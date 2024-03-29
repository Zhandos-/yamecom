/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.BaseDAO;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TWINS
 * @param <T>
 * @param <KeyType>
 */
@Transactional("postgresT")
@Repository
public abstract class BaseDAOImpl<T, KeyType extends Serializable>
        implements BaseDAO<T, KeyType> {

    @Autowired
    @Qualifier("sessionFactoryPostgres")
    protected SessionFactory sessionFactory;
    private Class<T> domainClass;

    public BaseDAOImpl() {
        domainClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    protected Session ht() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public T save(T entity) {
        ht().saveOrUpdate(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        ht().saveOrUpdate(entity);
        return entity;
    }

    @Override
    public void delete(T entity) {
        ht().delete(entity);
    }

    @Override
    public T getById(KeyType id) {
        return (T) ht().get(domainClass, id);
    }

    @Override
    public List<T> getAll() {
        return ht().createCriteria(domainClass).list();
    }

    @Override
    public void saveAll(Collection<T> entities) {
        for (T t : entities) {
            ht().save(t);
        }
    }

    @Override
    public void updateAll(Collection<T> entities) {
        for (T t : entities) {
            ht().update(t);
        }
    }

    @Override
    public void deleteById(KeyType id) {
        Object o = getById(id);
        ht().delete(o);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<T> getAll(int maxResult, int firstResult) {
        return ht().createCriteria(domainClass)
                .addOrder(Order.asc("id"))
                .setFirstResult(firstResult)
                .setMaxResults(maxResult).list();
    }

    @Override
    public void deletelAllById(Collection<KeyType> ids) {
        for (KeyType id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteAll(Collection<T> entities) {
        for (T t : entities) {
            delete(t);
        }
    }
}
