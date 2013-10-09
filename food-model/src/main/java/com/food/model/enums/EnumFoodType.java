/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.model.enums;

/**
 *
 * @author TWINS
 */
public enum EnumFoodType {

    SUSHI("Суши"),
    PICCA("Пицца"),
    FAST_FOOD("Фаст Фуд"),
    CHINESE_JAPANESE("Китайская/Японская"),
    ITALIAN_EUROPE("Италянская/Европейская");
    private String descriptin;

    EnumFoodType(String descriptin) {
        this.descriptin = descriptin;
    }

    public String getDescription() {
        return this.descriptin;
    }
}
