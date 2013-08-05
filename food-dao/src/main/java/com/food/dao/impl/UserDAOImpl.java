/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.UserDAO;
import com.food.model.user.Role;
import com.food.model.user.User;
import java.util.List;
import org.hibernate.Session;
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
    private SessionFactory sessionFactory;
    private Session session;

    @Override
    public void addOrUpdateUser(User user) {
        session.saveOrUpdate(user);
    }

    @Override
    public User getUser(Integer id) {
        return (User) session.createQuery("from User where id = :id").setParameter("id", id).uniqueResult();
    }

    @Override
    public List<User> allUsers() {
        return session.createQuery("from User").list();
    }

    @Override
    public List<User> allActiveUsers() {
        return session.createQuery("from User where active = true").list();
    }

    @Override
    public Role getRoles(Integer id) {
        return (Role) session.createQuery("from Role where id=:id").setInteger("id", id).uniqueResult();
    }

    @Override
    public User getUserByEmail(String email) {
        return (User) session.createQuery("from User where email = :email").setString("email", email).uniqueResult();
    }

    @Override
    public boolean login(String mail, String password) {
        long count = ((Number) session
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
    public long save(User user) {
        session.saveOrUpdate(user);
        return user.getId();
    }

    public Session getSession() {
        if (session == null) {
            session = sessionFactory.getCurrentSession();
        }
        return session;
    }
}