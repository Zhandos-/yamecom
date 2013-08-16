/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.UserDAO;
import com.food.model.user.Role;
import com.food.model.user.User;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 *
 * @author daniyar.artykov
 */
@Repository
public class UserDAOImpl extends BaseDAOImpl<User, Long> implements UserDAO {

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
    public Role getRoles(Integer id) {
        return (Role) ht().createQuery("from Role where id=:id").setInteger("id", id).uniqueResult();
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
}