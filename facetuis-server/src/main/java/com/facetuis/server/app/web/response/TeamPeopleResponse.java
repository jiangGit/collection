package com.facetuis.server.app.web.response;

import java.util.List;

public class TeamPeopleResponse {

    private List<TeamUsersResponse> list;

    private int total;

    public List<TeamUsersResponse> getList() {
        return list;
    }

    public void setList(List<TeamUsersResponse> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
