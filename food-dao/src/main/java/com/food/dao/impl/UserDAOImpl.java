/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.UserDAO;
import com.food.model.user.Role;
import com.food.model.user.User;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author daniyar.artykov
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    @Qualifier("sessionFactoryPostgres")
    SessionFactory sessionFactory;

    @Override
    public void addOrUpdateUser(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    @Override
    public User getUser(Integer id) {
        return (User) sessionFactory.getCurrentSession().createQuery("from User where id = :id").setParameter("id", id).uniqueResult();
    }

    @Override
    public List<User> allUsers() {
        return sessionFactory.getCurrentSession().createQuery("from User").list();
    }

    @Override
    public List<User> allActiveUsers() {
        return sessionFactory.getCurrentSession().createQuery("from User where active = true").list();
    }

    @Override
    public Role getRoles(Integer id) {
        return (Role) sessionFactory.getCurrentSession().createQuery("from Role where id=:id").setInteger("id", id).uniqueResult();
    }

    @Override
    public User getUserByEmail(String email) {
        return (User) sessionFactory.getCurrentSession().createQuery("from User where email = :email").setString("email", email).uniqueResult();
    }
}