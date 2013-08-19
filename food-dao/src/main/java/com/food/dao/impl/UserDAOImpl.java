/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.UserDAO;
import com.food.model.user.Role;
import com.food.model.user.User;
import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daniyar.artykov
 */
@Repository
public class UserDAOImpl extends BaseDAOImpl<User, Long> implements UserDAO, Serializable {

    private static final long serialVersionUID = 5105691350032035974L;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public User getUser(Long id) {
        return (User) ht().createQuery("from User where id = :id").setParameter("id", id).uniqueResult();

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<User> allUsers() {
        return ht().createQuery("from User").list();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<User> allActiveUsers() {
        return ht().createQuery("from User where active = true").list();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Role getRoles(Integer id) {
        return (Role) ht().createQuery("from Role where id=:id").setInteger("id", id).uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public User getUserByEmail(String email) {
        return (User) ht().createQuery("from User where email = :email").setString("email", email).uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
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