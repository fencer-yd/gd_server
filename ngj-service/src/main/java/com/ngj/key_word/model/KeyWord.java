package com.ngj.key_word.model;

import com.ngj.base.FormData;
import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/25.
 */
@Data
public class KeyWord extends FormData{
    private Long id;
    private String name;
    private Long tenant;

}
