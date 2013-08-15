package com.food.model.enums;

public enum EnumRole {

    ROLE_CLIENT("РОЛЬ КЛИЕНТА"),
    ROLE_ADMIN("РОЛЬ АДМИНСТРАТОРА"),
    ROLE_CONSUMER("РОЛЬ КОМПАНЬОНА"),
    ROLE_ANONYMOUS("НЕ АВТОРИЗОВАННЫЙ ПОЛЗОВАТЕЛЬ");
    private String descriptin;

    EnumRole(String descriptin) {
        this.descriptin = descriptin;
    }

    public String getDescription() {
        return this.descriptin;
    }
}