/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.RestaurantTypeDAO;
import com.food.model.restaurant.RestaurantType;
import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daniyar.artykov
 */
@Transactional("postgresT")
@Repository
public class RestaurantTypeDAOImpl extends BaseDAOImpl<RestaurantType, Long>
        implements RestaurantTypeDAO, Serializable {

    @Autowired
    @Qualifier("sessionFactoryPostgres")
    private SessionFactory sessionFactory;

    @Override
    public Session ht() {
        return sessionFactory.getCurrentSession();
    }
}
