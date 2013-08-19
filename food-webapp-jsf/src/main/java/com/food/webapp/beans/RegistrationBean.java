/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.beans;

import com.food.model.user.User;
import com.food.webapp.services.UserService;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author daniyar.artykov
 */
@Component("registrationBean")
@Scope("session")
public class RegistrationBean implements Serializable {
    
    private static final long serialVersionUID = -2179883471466728189L;
    @Autowired(required = true)
    @Qualifier("userService")
    private UserService userService;
    private User user;
    private static final Logger log = LoggerFactory.getLogger(RegistrationBean.class);
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String addUser() {
        log.info("addUser()");
        user.setPassword(userService.createHash(user.getPassword()));
        userService.save(user);
        return null;
    }
}