package com.ngj.contract.model;

import com.ngj.base.FormData;
import lombok.Data;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/22.
 */
public class Contract extends FormData{
    private static final Integer NO_CHECKED = 1; //未审核
    private static final Integer PASS = 2;     //已通过
    private static final Integer  REJECT = 3;  //驳回
    private Long id;
    private String name;
    private Long party_a;
    private Long party_b;
    private Long effectiveTime;
    private Long expirationTime;
    private String content;
    private Long templetId;
    private String  keyWords;
    private Integer situate ;
    private List<Object> keyWordList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParty_a() {
        return party_a;
    }

    public void setParty_a(Long party_a) {
        this.party_a = party_a;
    }

    public Long getParty_b() {
        return party_b;
    }

    public void setParty_b(Long party_b) {
        this.party_b = party_b;
    }

    public Long getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Long effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTempletId() {
        return templetId;
    }

    public void setTempletId(Long templetId) {
        this.templetId = templetId;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public Integer getSituate() {
        return situate;
    }

    public void setSituate(Integer situate) {
        this.situate = situate;
    }

    public List<Object> getKeyWordList() {
        return keyWordList;
    }

    public void setKeyWordList(List<Object> keyWordList) {
        this.keyWordList = keyWordList;
    }
}
