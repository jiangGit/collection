package com.facetuis.server.model.user;

public enum UserLevel {
    LEVEL1(0),
    LEVEL2(1);
    private int level;

    private UserLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
