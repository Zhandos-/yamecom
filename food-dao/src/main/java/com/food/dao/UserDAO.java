/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao;

import com.food.model.restaurant.Restaurant;
import com.food.model.user.Role;
import com.food.model.user.User;
import java.util.List;
import java.util.Set;

/**
 *
 * @author daniyar.artykov
 */
public interface UserDAO extends BaseDAO<User, Long> {

    public User getUser(Long id);

    public List<User> allUsers();

    public List<User> allActiveUsers();

    public User getUserByEmail(String email);

    public Set<Role> getRolesByUserId(Long id);

    public boolean login(String mail, String password);

    public List<User> allUsers(int maxResults, int firstResult);

    public List<Restaurant> getRestaurantsByUser(User user);
}