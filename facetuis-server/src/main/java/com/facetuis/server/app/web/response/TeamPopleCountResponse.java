package com.facetuis.server.app.web.response;

public class TeamPopleCountResponse {

    private int people_total; //总人数
    private int people_directly_super; // 直属人数(高级)
    private int people_today;// 今日人数
    private int people_yesterday;// 昨日人数
    private int people_directly;// 直属会员人数（所有直属一级人数）
    private int people_directly_next;// 直属会员下级人数（下级的下级人数）

    public int getPeople_total() {
        return people_total;
    }

    public void setPeople_total(int people_total) {
        this.people_total = people_total;
    }

    public int getPeople_directly_super() {
        return people_directly_super;
    }

    public void setPeople_directly_super(int people_directly_super) {
        this.people_directly_super = people_directly_super;
    }

    public int getPeople_today() {
        return people_today;
    }

    public void setPeople_today(int people_today) {
        this.people_today = people_today;
    }

    public int getPeople_yesterday() {
        return people_yesterday;
    }

    public void setPeople_yesterday(int people_yesterday) {
        this.people_yesterday = people_yesterday;
    }

    public int getPeople_directly() {
        return people_directly;
    }

    public void setPeople_directly(int people_directly) {
        this.people_directly = people_directly;
    }

    public int getPeople_directly_next() {
        return people_directly_next;
    }

    public void setPeople_directly_next(int people_directly_next) {
        this.people_directly_next = people_directly_next;
    }
}
