package com.ngj.user;

import com.ngj.user.modle.UserPassport;

/**
 * Created by guanxinquan on 16/3/1.
 */
public interface UserPassportService {


    public UserPassport loadPassport(String token);


    public void invalidatePassport(Long userId);

}
