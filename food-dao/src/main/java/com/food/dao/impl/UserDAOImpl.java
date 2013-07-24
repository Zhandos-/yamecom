/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.UserDAO;
import com.food.model.auth.Role;
import com.food.model.auth.User;
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
    public List<User> listUser() {
        return sessionFactory.getCurrentSession().createQuery("from User").list();
    }

    @Override
    public User getUserbyLogin(String login) {
        return (User) sessionFactory.getCurrentSession().createQuery("from User where userLogin = :login").setString("login", login).uniqueResult();
    }

    @Override
    public User getUserByUUID(String uuid) {
        return (User) sessionFactory.getCurrentSession().createQuery("from User where userGuid = :uuid").setString("uuid", uuid).uniqueResult();
    }

    @Override
    public List<User> getUserByUserCode(String userCode) {
        return sessionFactory.getCurrentSession().createQuery("from User where userCode like :userCode").setString("userCode", "%" + userCode + "%").list();
    }

    @Override
    public Role getRoles(Integer id) {
        return (Role) sessionFactory.getCurrentSession().createQuery("from Role where id=:id").setInteger("id", id).uniqueResult();
    }
}