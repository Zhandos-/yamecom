/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.RestaurantDAO;
import com.food.model.restaurant.Restaurant;
import java.io.Serializable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TWINS
 */
@Repository
public class RestaurantDAOImpl extends BaseDAOImpl<Restaurant, Long> implements RestaurantDAO, Serializable {
}
