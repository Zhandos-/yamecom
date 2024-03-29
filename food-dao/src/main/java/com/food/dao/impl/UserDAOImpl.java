/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.UserDAO;
import com.food.model.restaurant.Restaurant;
import com.food.model.user.Role;
import com.food.model.user.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

/**
 *
 * @author daniyar.artykov
 */
@Repository
public class UserDAOImpl extends BaseDAOImpl<User, Long> implements UserDAO, Serializable {

    private static final long serialVersionUID = 5105691350032035974L;
    private static final String QUERY_GETALL_BETWEEN_ROWS = "select * from food.f_user limit :maxResults offset :firstResult";

    @Override
    public User getUser(Long id) {
        return (User) ht().createQuery("from User where id = :id").setParameter("id", id).uniqueResult();

    }

    @Override
    public List<User> allUsers() {
        return ht().createQuery("from User").list();
    }

    @Override
    public List<User> allActiveUsers() {
        return ht().createQuery("from User where active = true").list();
    }

    @Override
    public Set<Role> getRolesByUserId(Long id) {
        return new HashSet<Role>(ht().createQuery("select u.roles from User u where u.id=:id").setParameter("id", id).list());
    }

    @Override
    public User getUserByEmail(String email) {
        return (User) ht().createQuery("from User where email = :email").setString("email", email).uniqueResult();
    }

    @Override
    public boolean login(String mail, String password) {
        long count = ((Number) ht()
                .createQuery("select COUNT(*) from User where email = :email and password=:password")
                .setString("email", mail)
                .setString("password", password)
                .uniqueResult()).longValue();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<User> allUsers(int maxResults, int firstResult) {
        return ht().createSQLQuery(QUERY_GETALL_BETWEEN_ROWS)
                .addEntity(User.class)
                .setInteger("firstResult", firstResult)
                .setInteger("maxResults", maxResults)
                .list();
    }

    @Override
    public List<Restaurant> getRestaurantsByUser(User user) {
        return new ArrayList<Restaurant>(ht()
                .createQuery("select u.restaurants from User u where u=:u")
                .setParameter("u", user)
                .list());
    }
}
