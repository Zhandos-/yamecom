/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services.impl;

import com.food.dao.RestaurantDAO;
import com.food.model.data.Phone;
import com.food.model.enums.EnumPhoneType;
import com.food.model.restaurant.Restaurant;
import com.food.model.restaurant.RestaurantDetails;
import com.food.model.restaurant.RestaurantType;
import com.food.model.user.User;
import org.springframework.stereotype.Service;
import com.food.webapp.services.RestaurantService;
import com.food.webapp.services.UserService;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private RestaurantDAO restaurantDAO;
    @Autowired
    private UserService userService;

    @Override
    public long saveOrUpdate(Restaurant restaurant, String mPhone, String sPhone) {
        User user = userService.getCurrentUser();
        if (restaurant.getId() != null) {
            restaurant.setUser(user);
            restaurant.setCreationDate(new Date());
            setPhoneList(restaurant, mPhone, sPhone);
            return restaurantDAO.save(restaurant).getId();
        } else {
            restaurant.setUser(user);
            restaurant.setCreationDate(new Date());
            setPhoneList(restaurant, mPhone, sPhone);
            return restaurantDAO.update(restaurant).getId();
        }
    }

    private void setPhoneList(Restaurant restaurant, String mPhone, String sPhone) {
        List<Phone> phones = new ArrayList<Phone>();
        Phone mP = new Phone();
        mP.setNumber(mPhone);
        mP.setPhoneType(EnumPhoneType.MOBILE);
        mP.setRestaurant(restaurant);
        phones.add(mP);
        if (sPhone != null && !sPhone.equals("")) {
            Phone sP = new Phone();
            sP.setNumber(sPhone);
            sP.setPhoneType(EnumPhoneType.HOME);
            sP.setRestaurant(restaurant);
            phones.add(sP);
        }
        restaurant.setPhones(phones);
    }

    @Override
    public List<RestaurantDetails> getRestaurants(int maxResult, int firtsResult) {
        return restaurantDAO.getRestaurantDetailse(maxResult, firtsResult);
    }

    @Override
    public Restaurant getById(Long id) {
        return restaurantDAO.getById(id);
    }

    @Override
    public List<RestaurantDetails> getByName(String name) {
        return restaurantDAO.getSearchRestaurants(name);
    }

    @Override
    public List<RestaurantDetails> filter(List<Long> restaurantTypesId) {
        return restaurantDAO.filter(restaurantTypesId);
    }

    @Override
    public List<RestaurantType> getRestaurantTypes() {
        return restaurantDAO.getRestaurantTypes();
    }

    @Override
    public RestaurantType findById(Long id) {
        return restaurantDAO.findById(id);
    }
}
