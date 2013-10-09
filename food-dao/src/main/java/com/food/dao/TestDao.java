/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao;

import com.food.model.user.User;
import org.hibernate.Session;

/**
 *
 * @author TWINS
 */
public interface TestDao {

    public void clean(Class... entityClasses);

    public User fillClient(String password);

    public Session ht();

    public <T> T save(T entity);

    public <T> T update(T entity);

    public void startTransaction();

    public void endTransaction();
}
