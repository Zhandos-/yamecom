/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services;

import com.food.model.restaurant.Restaurant;
import com.food.model.restaurant.RestaurantDetails;
import com.food.model.restaurant.RestaurantType;
import java.util.List;

/**
 *
 * @author TWINS
 */
public interface RestaurantService {

    public long saveOrUpdate(Restaurant restaurant, String mPhone, String sPhone);

    public List<RestaurantDetails> getRestaurants(int pageSize, int pageNumber);

    public Restaurant getById(Long id);

    public List<RestaurantDetails> getByName(String name);

    public List<RestaurantDetails> filter(List<Long> restaurantTypesId);

    public List<RestaurantType> getRestaurantTypes();

    public RestaurantType findById(Long id);
}
