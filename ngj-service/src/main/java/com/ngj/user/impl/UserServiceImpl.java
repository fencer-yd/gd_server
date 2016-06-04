package com.ngj.user.impl;

import com.ngj.datasource.RedisClient;
import com.ngj.datasource.RedisClusterClient;
import com.ngj.menu.mapper.MenuMapper;
import com.ngj.menu.model.Menu;
import com.ngj.reletionTable.mapper.GroupMenuMapper;
import com.ngj.reletionTable.mapper.GroupUserMapper;
import com.ngj.reletionTable.model.GroupMenu;
import com.ngj.reletionTable.model.UserGroup;
import com.ngj.user.UserPassportService;
import com.ngj.user.UserService;
import com.ngj.user.mapper.CompanyMapper;
import com.ngj.user.mapper.UserMapper;
import com.ngj.user.modle.Company;
import com.ngj.user.modle.User;
import com.ngj.user.modle.UserPassport;
import com.ngj.utils.CipherUtil;
import com.ngj.utils.PropertiesCopyUtils;
import com.ngj.utils.TicketUtil;
import com.ngj.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sun.security.rsa.RSASignature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by guanxinquan on 16/3/1.
 */
@Component("userService")
public class UserServiceImpl implements UserService,UserPassportService {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private GroupMenuMapper groupMenuMapper;
    @Autowired
    private GroupUserMapper groupUserMapper;
    @Autowired
    private MenuMapper  menuMapper;

    private static final String PASSPORT_KEY = "x:p";


    public UserPassport loadPassport(String token) {
        String userId = TicketUtil.decryptTicket(token);
        if(StringUtils.isEmpty(userId)){
            return null;
        }

        Long id = Long.valueOf(userId);
        return loadPassport(id);
    }

    public Long createUser(User user) {

        if(user.getRole() == null || !User.checkRoles(user.getRole())){
            return -2l;
        }
        User oldUser = userMapper.loadByNameAndTenantId(user.getUsername(), user.getTenant());
        if(oldUser != null){
            return  USER_ALREADY_EXIST;
        }else{
            return userMapper.insert(user);
        }
    }

    /**
     * 用户登录时,如果缓存中存在对应的passport记录,token不变,但是要更新缓存中的用户配置信息;
     * 如果缓存中没有对应的用户信息,需要生成新的token,可以用于踢出用户,当用户获得的token不相等的情况下,需要用户重新登录
     * @param userName
     * @param domain
     * @param password
     * @return
     */
    public UserPassport userLogin(String userName, String domain, String password) {
        /**
         * 验证参数
         */
        Long companyId = null;
        if(!VerifyUtils.allNotNullOrEmpty(userName,domain,password))
            return null;
        Company company = companyMapper.selectCompanyByDomain(domain);
        if(company==null)
        {
            return null;
        }
        else {
            companyId =company.getId();
        }
        User user = userMapper.loadByNameAndTenantId(userName, companyId);

        if(user == null)
            return null;

        if(CipherUtil.validatePassword(user.getPassword(),password))//用户密码正确，处理用户业务逻辑
        {
            return refreshPassport(user);
        }else{//用户密码不正确，直接返回
            return null;
        }
    }

    /**
     * 刷新passport
     * @param user
     * @return
     */
    private UserPassport refreshPassport(User user){

        UserPassport passport = loadPassport(user.getId());
        //如果用户登录过，并且redis里面有信息，直接取出token，返回给客户端
        //否者，创建token，并写入redis
        String token;
        if(passport == null){//当前用户不再cache中，需要重新登录
            token = TicketUtil.generateTicket(String.valueOf(user.getId()));
        }else{//用户登录的时候，用户信息都刷新（这样，即便是在数据库被修改的情况下，通过重新登录，也可以同步用户状态）
            token = passport.getToken();
        }

        passport = new UserPassport(user.getId(),token,user.getTenant(), user.getRole(),user.getUsername(),user.getName(),user.getRefresh());
        savePassport(passport);
        return passport;
    }

    public User loadUser(Long userId) {
        return userMapper.loadById(userId);
    }

    public UserPassport updateUser(User user) {
        User oldUser = userMapper.loadById(user.getId());
        oldUser.setUTime(user.getuTime());
        oldUser.setPassword(user.getPassword());
        oldUser.setMobile(user.getMobile());
        oldUser.setName(user.getName());
        oldUser.setUsername(user.getUsername());
        userMapper.update(oldUser);
        kickout(user.getId());//踢掉用户
        User newUser = userMapper.loadById(user.getId());
        return refreshPassport(newUser);
    }

    public List<User> loadUsers(Long tenant, Long start, Integer limit) {
        return userMapper.selectUserByTenant(tenant, start, limit);
    }

    public List<User> loadUsers(Long tenant) {
        return userMapper.selectUsers(tenant);
    }

    public List<User> loadUsersBygroupId(Long groupId) {
        List<User> users = new ArrayList<User>();
        List<UserGroup> list = groupUserMapper.selectByGroupId(groupId);
        for(UserGroup userGroup:list)
        {
            users.add(userMapper.loadById(userGroup.getUser_id()));
        }
        return users;
    }


    public Integer countUsers(Long tenant) {
        return userMapper.countUser(tenant);
    }

    public List<Menu> getMenusByUserId(Long userId) {
        List<UserGroup> userGroups  = groupUserMapper.selectByUserId(userId);
        List<GroupMenu> groupMenus = new ArrayList<GroupMenu>();
        Set<Long> menuIdSet = new HashSet<Long>();
        List<Menu> menus = new ArrayList<Menu>();
        for(UserGroup userGroup: userGroups)
        {
            groupMenus.addAll(groupMenuMapper.selectByGroupId(userGroup.getGroup_id()));
        }
        for(GroupMenu groupMenu:groupMenus)
        {
            menuIdSet.add(groupMenu.getMenu_id());
        }
        for(Long id:menuIdSet)
        {
            menus.add(menuMapper.findById(id));
        }
        return  formatMenu(menus);
    }

    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }

    private List<Menu> formatMenu(List<Menu> menus){
        List<Menu> formartMenus = new ArrayList<Menu>();
        List<Menu> parentMenus = new ArrayList<Menu>();
        List<Menu> childMenus = new ArrayList<Menu>();
        for(Menu menu:menus)
        {
            if(menu.getPid()==null)
            {
                parentMenus.add(menu);
            }else
            {
                childMenus.add(menu);
            }
        }
        for(Menu pMenu:parentMenus)
        {
            for(Menu cMenu:childMenus)
            {
                if(cMenu.getPid().longValue()==pMenu.getId().longValue())
                    pMenu.getChildren().add(cMenu);
            }
        }
        return parentMenus;
    }
    public void kickout(Long userId) {
        invalidatePassport(userId);
    }

    public void invalidatePassport(Long userId) {
        String key = redisClient.generateKey(PASSPORT_KEY,userId);
        redisClient.del(key);
    }

    private void savePassport(UserPassport passport){
        String key = redisClient.generateKey(PASSPORT_KEY,passport.getId());
        redisClient.set(key,passport);
    }

    private UserPassport loadPassport(Long userId){
        return  redisClient.get(redisClient.generateKey(PASSPORT_KEY, userId), UserPassport.class);
    }
}
