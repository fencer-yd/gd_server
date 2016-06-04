package com.ngj.signForm.model;

import com.ngj.base.FormData;
import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/25.
 */
@Data
public class SignForm extends FormData{
    private Long id;
    private Long contractId;
    private Long party_a;
    private Long party_b;
    private String party_a_opinion;
    private String party_b_opinion;
}
