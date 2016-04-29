package com.ngj.reletionTable.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/21.
 */
@Data
public class GroupMenu {
    private Long id;

    private Long group_id;

    private Long tenant;

    @JsonIgnore
    private Long menu_id;
}
