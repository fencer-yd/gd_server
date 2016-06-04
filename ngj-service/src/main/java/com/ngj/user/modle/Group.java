package com.ngj.user.modle;

import com.ngj.base.FormData;
import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/22.
 */
@Data
public class Group extends FormData{
    private Long id;
    private Long tenant;
    private String name;
}
