package com.facetuis.server.utils;

public class CommisionUser {

    private double userRate; // 购买人
    private double user1Rate;// 上级用户
    private double user2Rate;// 上上级用户
    private double user3Rate;// 上上上级用户

    public double getUserRate() {
        return userRate;
    }

    public void setUserRate(double userRate) {
        this.userRate = userRate;
    }

    public double getUser1Rate() {
        return user1Rate;
    }

    public void setUser1Rate(double user1Rate) {
        this.user1Rate = user1Rate;
    }

    public double getUser2Rate() {
        return user2Rate;
    }

    public void setUser2Rate(double user2Rate) {
        this.user2Rate = user2Rate;
    }

    public double getUser3Rate() {
        return user3Rate;
    }

    public void setUser3Rate(double user3Rate) {
        this.user3Rate = user3Rate;
    }


}
