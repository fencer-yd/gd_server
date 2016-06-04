package com.ngj.item.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ngj.base.FormData;
import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/25.
 */
@Data
public class Item extends FormData{
    private Long id;
    private String name;
    private Long tenant;
    private String content;


}
