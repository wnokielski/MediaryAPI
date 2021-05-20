package com.mediary.Models.Enums;

public enum SortType {
    Today("Today"),
    ThisWeek("ThisWeek"),
    ThisMonth("ThisMonth"),
    ThisYear("ThisYear"),
    Past("Past");

    private String sortType;

    SortType(String sortType){
        this.sortType=sortType;
    }

    public String getSortType() {
        return sortType;
    }


    @Override
    public String toString() {
        return sortType;
    }
}
