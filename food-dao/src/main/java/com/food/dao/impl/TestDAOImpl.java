/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.TestDao;
import com.food.model.data.Phone;
import com.food.model.enums.EnumPhoneType;
import com.food.model.enums.EnumRole;
import com.food.model.restaurant.Restaurant;
import com.food.model.restaurant.RestaurantDetails;
import com.food.model.restaurant.RestaurantType;
import com.food.model.user.Role;
import com.food.model.user.User;
import java.util.ArrayList;
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

    private static void addDepends(Class<?> from, Class<?> to) {
        Set<Class<?>> set = depends.get(to);
        if (set == null) {
            depends.put(to, set = new HashSet<Class<?>>());
        }
        set.add(from);
    }

    static {
        //        (FKTABLE_NAME, PKTABLE_NAME)
//       очищать таблицы и таблицы которые от низ зависят
//        от (зависящий) к до (основной)
        addDepends(Restaurant.class, User.class);
        addDepends(Restaurant.class, RestaurantDetails.class);
        addDepends(RestaurantType.class, RestaurantDetails.class);
    }

    @Override
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
        Set<Role> roles = new HashSet<Role>();
        List<Phone> phones = new ArrayList<Phone>();
        EnumRole enumRole = EnumRole.ROLE_CLIENT;
        Role role = (Role) ht().createQuery("from Role where name=:name").setString("name", "ROLE_CLIENT").uniqueResult();
        if (role == null) {
            role = new Role();
            role.setDescription(enumRole.getDescription());
            role.setName(enumRole);
            ht().save(role);
        }
        roles.add(role);
        User user = new User();
        user.setRoles(roles);
        user.setEmail("doni@gmail.comh");
        user.setPassword(password);
        user.setName("Doni");
        user.setSurname("Artykov");
        {
            Phone phone = new Phone();
            phone.setPhoneType(EnumPhoneType.MOBILE);
            phone.setNumber("+7 (702) 820-52-25");
            ht().save(phone);
            phones.add(phone);
        }
        {
            Phone phone = new Phone();
            phone.setPhoneType(EnumPhoneType.MOBILE);
            phone.setNumber("+7 (702) 828-52-25");
            ht().save(phone);
            phones.add(phone);
        }
        user.setPhones(phones);
        ht().save(user);
        return user;
    }

    @Override
    public <T> T save(T entity) {
        ht().saveOrUpdate(entity);
        return entity;
    }

    @Override
    public <T> T update(T entity) {
        ht().saveOrUpdate(entity);
        return entity;
    }

    @Override
    public void startTransaction() {
        ht().beginTransaction();
    }

    @Override
    public void endTransaction() {
        ht().getTransaction().commit();
    }
}
