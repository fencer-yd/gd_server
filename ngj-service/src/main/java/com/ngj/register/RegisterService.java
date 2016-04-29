package com.ngj.register;

import com.ngj.register.model.Register;

import java.io.IOException;
import java.util.Map;

/**
 * Created by pangyueqiang on 16/4/20.
 */
public interface RegisterService {
    void register(Register register)throws IOException;
}
