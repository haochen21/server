package com.km086.server.model.store;

public enum ProductStatus {

    ONLINE("上架"), OFFLINE("下架"), DELETE("删除");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static ProductStatus getStatus(String status) {
        if (status.equals("上架")) {
            return ONLINE;
        } else if (status.equals("下架")) {
            return OFFLINE;
        } else if (status.equals("删除")) {
            return DELETE;
        } else {
            return null;
        }
    }
}
