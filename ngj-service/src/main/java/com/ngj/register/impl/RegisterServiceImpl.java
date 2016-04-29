package com.ngj.register.impl;

import com.ngj.customer.mapper.CustomerMapper;
import com.ngj.customer.model.Customer;
import com.ngj.register.RegisterService;
import com.ngj.register.model.Register;
import com.ngj.reletionTable.mapper.GroupMenuMapper;
import com.ngj.reletionTable.mapper.GroupUserMapper;
import com.ngj.reletionTable.model.GroupMenu;
import com.ngj.reletionTable.model.UserGroup;
import com.ngj.resource.ResourceLoadUtils;
import com.ngj.user.mapper.CompanyMapper;
import com.ngj.user.mapper.GroupMapper;
import com.ngj.user.mapper.UserMapper;
import com.ngj.user.modle.Company;
import com.ngj.user.modle.Group;
import com.ngj.user.modle.User;
import com.ngj.utils.CipherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * Created by pangyueqiang on 16/4/20.
 */
@Component("registerService")
public class RegisterServiceImpl implements RegisterService{
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GroupUserMapper groupUserMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupMenuMapper groupMenuMapper;
    @Autowired
    private CustomerMapper customerMapper;


    public void register(Register register) throws IOException{
        //创建公司
        Properties properties = new Properties();
        InputStream inputStream = ResourceLoadUtils.class.getResourceAsStream("/company.properties");
        properties.load(inputStream);
        Company company = new Company();
        User user = new User();
        company.setName(register.getCompanyName());
        company.setMaxUser(Integer.parseInt(properties.get(register.getRelease() + ".maxUser").toString()));
        company.setDomain(register.getDomain());
        Date curTime = new Date();
        int day = Integer.parseInt(properties.get(register.getRelease() + ".deadLine").toString());
        company.setDeadLine(curTime.getTime() + day * 24 * 60 * 60 * 1000);
        company.setCtime(curTime.getTime());
        company.setUtime(curTime.getTime());
        companyMapper.insertCompany(company);
        Long tenantId = company.getId();

        //创建管理员
        user.setCtime(curTime.getTime());
        user.setUtime(curTime.getTime());
        user.setName(register.getName());
        user.setTenant(tenantId);
        user.setUsername(register.getUsername());
        user.setPassword(CipherUtil.generatePassword(register.getPassword()));
        user.setMobile(register.getMobile());
        user.setRole("ROLE_ADMIN");
        user.setRefresh(curTime.getTime());
        userMapper.insert(user);
        Long userId = user.getId();

        //加入企业客户信息库
        Customer customer = new Customer();
        customer.setTenant(tenantId);
        customer.setName(register.getCompanyName());
        customer.setCTime(curTime.getTime());
        customer.setUTime(curTime.getTime());
        customer.setStatus(1);
        customerMapper.insert(customer);

        //初始化管理员分组
        Group group = new Group();
        group.setTenant(tenantId);
        group.setName("管理组");
        group.setCTime(curTime.getTime());
        group.setUTime(curTime.getTime());
        group.setStatus(1);
        groupMapper.insert(group);
        Long groupId = group.getId();

        //加入用户分组管理表
        UserGroup userGroup  = new UserGroup();
        userGroup.setGroup_id(groupId);
        userGroup.setUser_id(userId);
        groupUserMapper.insert(userGroup);

        //初始化该版本的的menu,从配置文件中获取选择的版本对应菜单内容
        String menuIds = properties.get(register.getRelease() + ".menu").toString().trim();
        String[] menuIdArray = menuIds.split(",");
        GroupMenu groupMenu = new GroupMenu();
        groupMenu.setTenant(tenantId);
        groupMenu.setGroup_id(groupId);
        Long menuId = null;
        for(int i =0;i<menuIdArray.length;i++)
        {
            menuId = Long.parseLong(menuIdArray[i].trim());
            groupMenu.setMenu_id(menuId);
            groupMenuMapper.insert(groupMenu);
        }
    }
}
