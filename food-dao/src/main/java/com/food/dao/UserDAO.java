/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao;

import com.food.model.auth.Role;
import com.food.model.auth.User;
import java.util.List;

/**
 *
 * @author daniyar.artykov
 */
public interface UserDAO {

    public void addOrUpdateUser(User user);

    public User getUser(Integer id);

    public List<User> listUser();

    public User getUserbyLogin(String login);

    public User getUserByUUID(String uuid);

    public List<User> getUserByUserCode(String userCode);

    public Role getRoles(Integer id);
}