package com.ngj.rest.resp;

import lombok.Data;

import java.util.List;

/**
 * Created by guanxinquan on 16/3/8.
 */
@Data
public class CommonsRespList<T> {

    private List<T> values = null;

    private Integer total = null;


    public CommonsRespList(List<T> values,Integer total){
        this.values = values;
        this.total = total;
    }


    public CommonsRespList(List<T> values){
        this.values = values;
    }

    public CommonsRespList(){}
}
