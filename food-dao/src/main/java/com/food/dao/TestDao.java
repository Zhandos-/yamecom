/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao;

import com.food.model.user.User;

/**
 *
 * @author TWINS
 */
public interface TestDao {

    public void clean(Class... entityClasses);

    public User fillClient(String password);
}
