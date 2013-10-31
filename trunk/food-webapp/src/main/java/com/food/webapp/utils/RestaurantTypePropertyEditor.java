/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.utils;

import com.food.model.restaurant.RestaurantType;
import com.food.webapp.services.RestaurantService;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author daniyar.artykov
 */
public class RestaurantTypePropertyEditor extends PropertyEditorSupport {

    public RestaurantTypePropertyEditor(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    private final RestaurantService restaurantService;

    @Override
    public void setAsText(String incomming) {
        RestaurantType b = restaurantService.findById(Long.parseLong(incomming));
        setValue(b);
    }

    @Override
    public String getAsText() {
        return ((RestaurantType) getValue()).getId().toString();
    }
}
