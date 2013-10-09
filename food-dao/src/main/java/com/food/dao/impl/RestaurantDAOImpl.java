/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.RestaurantDAO;
import com.food.model.restaurant.Restaurant;
import com.food.model.restaurant.RestaurantDetails;
import com.food.model.restaurant.RestaurantType;
import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TWINS
 */
@Repository
public class RestaurantDAOImpl extends BaseDAOImpl<Restaurant, Long> implements RestaurantDAO, Serializable {

    @Override
    public List<RestaurantDetails> getSearchRestaurants(String name) {
        return ht().createQuery("from RestaurantDetails where restaurant.name like :name").setParameter("name", "%" + name + "%").list();
    }

    @Override
    public List<RestaurantDetails> getRestaurantDetailse(int firstResult, int maxResult) {
        return ht().createCriteria(RestaurantDetails.class)
                .addOrder(Order.asc("id"))
                .setFirstResult(firstResult)
                .setMaxResults(maxResult).list();
    }

    @Override
    public List<RestaurantDetails> filter(List<Long> restaurantTypesId) {
        List<Long> ids = ht().createSQLQuery("SELECT distinct(f.id) "
                + "  FROM food.f_restaurant_details f "
                + "  INNER JOIN food.f_restaurant_types d "
                + "  on d.f_restaurant_details_id=f.id "
                + "  where d.types_id in (:ids)").setParameterList("ids", restaurantTypesId).list();

        return ht().createSQLQuery("SELECT * "
                + "  FROM food.f_restaurant_details f "
                + "  where f.id in (:ids)").addEntity(RestaurantDetails.class).setParameterList("ids", ids).list();
    }

    @Override
    public List<RestaurantType> getRestaurantTypes() {
        return ht().createQuery("from RestaurantType").list();
    }
}
