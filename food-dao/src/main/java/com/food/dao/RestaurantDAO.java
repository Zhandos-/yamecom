/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao;

import com.food.model.restaurant.Restaurant;
import com.food.model.restaurant.RestaurantDetails;
import com.food.model.restaurant.RestaurantType;
import java.util.List;

/**
 *
 * @author TWINS
 */
public interface RestaurantDAO extends BaseDAO<Restaurant, Long> {

    public List<RestaurantDetails> getSearchRestaurants(String name);

    public List<RestaurantDetails> getRestaurantDetailse(int firstResult, int maxResult);

    public List<RestaurantDetails> filter(List<Long> restaurantTypesId);

    public List<RestaurantType> getRestaurantTypes();

    public RestaurantType findById(Long id);
}
