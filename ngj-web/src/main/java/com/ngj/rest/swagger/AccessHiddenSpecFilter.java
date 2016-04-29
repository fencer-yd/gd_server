package com.ngj.rest.swagger;

import com.wordnik.swagger.annotations.ApiModelProperty;
import com.wordnik.swagger.core.filter.SwaggerSpecFilter;
import com.wordnik.swagger.model.ApiDescription;
import com.wordnik.swagger.model.Operation;
import com.wordnik.swagger.model.Parameter;

import java.util.List;
import java.util.Map;

/**
 * Created by 葫芦娃 on 2016/3/5.
 *  解析swagger {@link ApiModelProperty}中的hidden属性
 */
public class AccessHiddenSpecFilter implements SwaggerSpecFilter {

    @Override
    public boolean isOperationAllowed(Operation arg0, ApiDescription arg1, Map<String, List<String>> arg2, Map<String, String> arg3, Map<String, List<String>> arg4) {
        return true;
    }

    @Override
    public boolean isParamAllowed(Parameter param, Operation operation, ApiDescription desc, Map<String, List<String>> arg3, Map<String, String> arg4, Map<String, List<String>> arg5) {
        final String paramAccess = param.paramAccess().toString();

        return !paramAccess.equalsIgnoreCase("Some(hidden)");
    }
}
