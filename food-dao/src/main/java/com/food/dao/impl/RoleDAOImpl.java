/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.RoleDAO;
import com.food.model.enums.EnumRole;
import com.food.model.user.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    @Qualifier("sessionFactoryPostgres")
    private SessionFactory sessionFactory;
    private Session session;

    @Override
    public Role getRoleById(Long id) {
        return (Role) session.createQuery("from Role where id = :id").setParameter("id", id).uniqueResult();
    }

    @Override
    public Role getRoleByName(EnumRole name) {
        return (Role) session.createQuery("from Role where name = :name").setParameter("name", name).uniqueResult();
    }

    @Override
    public long save(Role role) {
        session.saveOrUpdate(role);
        return role.getId();
    }

    public Session getSession() {
        if (session == null) {
            session = sessionFactory.getCurrentSession();
        }
        return session;
    }
}