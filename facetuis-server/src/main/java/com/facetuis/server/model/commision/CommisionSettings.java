package com.facetuis.server.model.commision;

import com.facetuis.server.model.basic.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_commision")
public class CommisionSettings extends BaseEntity {

    //private Double

    // 总分出
    @JsonIgnore
    private double totalRatio = 0.5;
    // 直属团长下无SVIP
    private double teamNoSvip = 0.2;
    // 直属团长下有SVIP
    private double teamHaveSvip = 0.06;

    // 总监下无总监
    private double directorNoSvip = 0.4;
    // 总监下有总监
    private double directorHaveSvip = 0.06;
    // 总监自买分出
    private double directorSelf = 0.6;
    // 月版奖励
    private Long monthAdd = 3900l;
    // 年版奖励
    private Long yearAdd = 35200l;

    public Long getMonthAdd() {
        return monthAdd;
    }

    public void setMonthAdd(Long monthAdd) {
        this.monthAdd = monthAdd;
    }

    public Long getYearAdd() {
        return yearAdd;
    }

    public void setYearAdd(Long yearAdd) {
        this.yearAdd = yearAdd;
    }

    public double getDirectorSelf() {
        return directorSelf;
    }

    public void setDirectorSelf(double directorSelf) {
        this.directorSelf = directorSelf;
    }

    public double getTotalRatio() {
        return totalRatio;
    }

    public void setTotalRatio(double totalRatio) {
        this.totalRatio = totalRatio;
    }

    public double getTeamNoSvip() {
        return teamNoSvip;
    }

    public void setTeamNoSvip(double teamNoSvip) {
        this.teamNoSvip = teamNoSvip;
    }

    public double getTeamHaveSvip() {
        return teamHaveSvip;
    }

    public void setTeamHaveSvip(double teamHaveSvip) {
        this.teamHaveSvip = teamHaveSvip;
    }

    public double getDirectorNoSvip() {
        return directorNoSvip;
    }

    public void setDirectorNoSvip(double directorNoSvip) {
        this.directorNoSvip = directorNoSvip;
    }

    public double getDirectorHaveSvip() {
        return directorHaveSvip;
    }

    public void setDirectorHaveSvip(double directorHaveSvip) {
        this.directorHaveSvip = directorHaveSvip;
    }
}
