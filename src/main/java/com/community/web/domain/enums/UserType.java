package com.community.web.domain.enums;

public enum UserType {
    admin("관리자"),
    commonuser("일반 유저");

    private String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
