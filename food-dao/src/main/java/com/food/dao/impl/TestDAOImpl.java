/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.TestDao;
import com.food.model.user.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TWINS
 */
@Transactional("postgresT")
@Repository
public class TestDAOImpl implements TestDao {

    @Autowired
    @Qualifier("sessionFactoryPostgres")
    private SessionFactory sessionFactory;
    private static Map<Class<?>, Set<Class<?>>> depends = new HashMap<Class<?>, Set<Class<?>>>();

    public Session ht() {
        return sessionFactory.getCurrentSession();
    }

    private <T> void delete(T entity) {
        ht().delete(entity);
    }

    private <T> void deleteAll(Collection<T> entities) {
        for (T t : entities) {
            delete(t);
        }
    }

    public <T> List<T> find(String sql) {
        return ht().createQuery(sql).list();
    }

    public void clean(Class... entityClasses) {
        cleanInternal(entityClasses, new HashSet<Class<?>>());
    }

    private void cleanInternal(Class[] entityClasses, Set<Class<?>> alreadyCleaned) {
        if (entityClasses.length == 0) {
            List list = find("from java.lang.Object");
            deleteAll(list);
        } else {
            for (Class aClass : entityClasses) {
                if (alreadyCleaned.contains(aClass)) {
                    continue;
                }
                alreadyCleaned.add(aClass);
                Set<Class<?>> set = depends.get(aClass);
                if (set != null) {
                    for (Class<?> class1 : set) {
                        cleanInternal(new Class<?>[]{class1}, alreadyCleaned);
                    }
                }
                deleteAll(find("from " + aClass.getName()));
            }
        }
    }

    @Override
    public User fillClient(String password) {
        User user = new User();
        user.setEmail("doni@gmail.comh");
        user.setPassword(password);
        user.setName("Doni");
        user.setSurname("Artykov");
        ht().save(user);
        return user;
    }
}
