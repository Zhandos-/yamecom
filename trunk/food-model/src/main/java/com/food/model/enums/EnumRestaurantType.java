/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.model.enums;

/**
 *
 * @author TWINS
 */
public enum EnumRestaurantType {

    PIZZA("Пицца"),
    SUSHI("Суши"),
    HALAL("Халял"),
    SHASHLIK("Шашлык"),
    FAST_FOOD("Фаст Фуд/Бургер"),
    CONFECTION("Кондитерское изделие"),
    ITALIAN_EUROPE("Итальянская/Европейская"),
    RUSSIAN_UKRAINIAN("Русская/Украинская"),
    ASIAN("Азиатская"),
    KAZAKH_UZBEK("Казахская/Узбекская"),
    CHINESE_JAPANESE("Китайская/Японская");

    private final String descriptin;

    EnumRestaurantType(String descriptin) {
        this.descriptin = descriptin;
    }

    public String getDescription() {
        return this.descriptin;
    }
}
