/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services;

import com.food.model.restaurant.Restaurant;
import java.util.List;

/**
 *
 * @author TWINS
 */
public interface RestaurantService {
  
public long saveOrUpdate(Restaurant restaurant);    
    
public List<Restaurant> getRestaurants(int pageSize,int pageNumber);

public Restaurant getById(Long id);
}
