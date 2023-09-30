package com.bhreneer.springdatacrudexample.enums;

public enum ShowType {
    MOVIE("Movie"),
    TV_SHOW("TV Show");

    private String description;

    ShowType(String description) {
        this.description = description;
    }
}
