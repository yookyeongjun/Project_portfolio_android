package com.example.android_project;

import java.util.HashMap;
import java.util.Map;

public class Contents {

    private Long id;
    private String name;
    private String category;
    private String type;
    private String expiration;
    private Long count;
    private Long dday;
    private Long inUseList;
    private Long inBuyList;
    private int backgroundColor;
    // 품목소비 버튼 카운트값
    private int CuseCnt = 1;
    //품목소비 에디트텍스트 값 저장
    private String editTextValue;
    // 품목소비 위치값
    private Long pos;





    public Long getPos() {
        return pos;
    }

    public void setPos(Long pos) {
        this.pos = pos;
    }

    public String getEditTextValue() {
        return editTextValue;
    }

    public String getEditTextValue(int pos) {
        return editTextValue;
    }

    public void setEditTextValue(String editTextValue) {
        this.editTextValue = editTextValue;
    }

    public int getCuseCnt() {
        return CuseCnt;
    }
    public void setCuseCnt(int cuseCnt) {
        CuseCnt = cuseCnt;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    private boolean isSelected;

    public Contents(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Contents() {

    }

    public Contents(Long id, String name, Long count, String category, String type, String expiration, Long dday, Long inUseList, Long inBuyList) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.type = type;
        this.expiration = expiration;
        this.count = count;
        this.dday = dday;
        this.inUseList = inUseList;
        this.inBuyList = inBuyList;
    }

    public Contents(Long id, String name, String category, String type, String expiration, Long count, Long dday, Long inUseList, Long inBuyList) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.type = type;
        this.expiration = expiration;
        this.count = count;
        this.dday = dday;
        this.inUseList = inUseList;
        this.inBuyList = inBuyList;
    }

    public Long getId() {
        return id;
    }

    public Long getId(int position) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public Long getCount() {
        return count;
    }

    public Long getCount(int position) {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getDday() {
        return dday;
    }

    public void setDday(Long dday) {
        this.dday = dday;
    }

    public Long getInUseList() {
        return inUseList;
    }

    public void setInUseList(Long inUseList) {
        this.inUseList = inUseList;
    }

    public Long getInBuyList() {
        return inBuyList;
    }

    public void setInBuyList(Long inBuyList) {
        this.inBuyList = inBuyList;
    }

}
