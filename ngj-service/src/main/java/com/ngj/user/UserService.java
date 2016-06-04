package com.ngj.user;

import com.ngj.menu.model.Menu;
import com.ngj.user.modle.User;
import com.ngj.user.modle.UserPassport;

import java.util.List;

/**
 * Created by guanxinquan on 16/3/1.
 */
public interface UserService {

    static final Long USER_ALREADY_EXIST = -1L;

    Long createUser(User user);
    UserPassport userLogin(String userName,String domain,String password);
    User loadUser(Long userId);
    UserPassport updateUser(User user);
    List<User> loadUsers(Long tenant,Long start,Integer limit);
    List<User> loadUsers(Long tenant);
    List<User> loadUsersBygroupId(Long groupId);
    Integer countUsers(Long tenant);
    List<Menu> getMenusByUserId(Long userId);
    void deleteById(Long id);
}
