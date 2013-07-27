/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.RoleDAO;
import com.food.model.auth.EnumRole;
import com.food.model.auth.Role;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    @Qualifier("sessionFactoryPostgres")
    SessionFactory sessionFactory;

    @Override
    public Role getRoleById(Long id) {
        return (Role) sessionFactory.getCurrentSession().createQuery("from Role where id = :id").setParameter("id", id).uniqueResult();
    }

    @Override
    public Role getRoleByName(EnumRole name) {
        return (Role) sessionFactory.getCurrentSession().createQuery("from Role where name = :name").setParameter("name", name).uniqueResult();
    }
}