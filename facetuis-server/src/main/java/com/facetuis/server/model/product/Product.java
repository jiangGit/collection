package com.facetuis.server.model.product;

import com.facetuis.server.model.basic.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_product")
public class Product extends BaseEntity {

    private String title;
    private String description;
    private String amount;
    private String timeLimit; // 时长 单位 天 月 季 年
    private Integer timeLimitValue; // 时长 值 365
    private String timeLimitTxt;// 时长 文字描述
    private Integer valuesDay;// 续费天数

    public Integer getValues() {
        return valuesDay;
    }

    public void setValues(Integer values) {
        this.valuesDay = values;
    }

    public void setTimeLimitValue(Integer timeLimitValue) {
        this.timeLimitValue = timeLimitValue;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getTimeLimitValue() {
        return timeLimitValue;
    }

    public void setTimeLimitValue(int timeLimitValue) {
        this.timeLimitValue = timeLimitValue;
    }

    public String getTimeLimitTxt() {
        return timeLimitTxt;
    }

    public void setTimeLimitTxt(String timeLimitTxt) {
        this.timeLimitTxt = timeLimitTxt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


}
