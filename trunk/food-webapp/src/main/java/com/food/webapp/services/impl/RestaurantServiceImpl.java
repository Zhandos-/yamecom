/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services.impl;

import com.food.dao.RestaurantDAO;
import com.food.model.restaurant.Restaurant;
import com.food.model.user.User;
import org.springframework.stereotype.Service;
import com.food.webapp.services.RestaurantService;
import com.food.webapp.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author TWINS
 */
@Service("restaurantService")
@Transactional("postgresT")
public class RestaurantServiceImpl implements RestaurantService {
@Autowired private RestaurantDAO restaurantDAO;
@Autowired private UserService userService;
    @Override
    public long saveOrUpdate(Restaurant restaurant) {
        User user=userService.getCurrentUser();
        if(restaurant.getId()!=null)
        {
        restaurant.setUser(user);
        return restaurantDAO.save(restaurant).getId();
        }
        else
        {
        restaurant.setUser(user);
        return restaurantDAO.update(restaurant).getId();
        }
    }

    @Override
    public List<Restaurant> getRestaurants(int maxResult, int firtsResult) {
       return restaurantDAO.getAll(maxResult, firtsResult);
    }

    @Override
    public Restaurant getById(Long id) {
     return restaurantDAO.getById(id);
    }
    
    
}
