package com.km086.server.model.security;

public enum DiscountType {

    PERCNET("百分比"), AMOUNT("减价");

    private final String description;

    DiscountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
