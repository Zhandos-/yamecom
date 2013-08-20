/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.beans;

import com.food.model.user.User;
import com.food.webapp.services.UserService;
import com.food.webapp.utils.FacesUtil;
import java.io.Serializable;
import java.util.Date;
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
@Scope("singleton")
public class RegistrationBean implements Serializable {

    private static final long serialVersionUID = -2179883471466728189L;
    @Autowired(required = true)
    @Qualifier("userService")
    private UserService userService;
    private String email;
    private String name;
    private String surname;
    private String password;
    private static final Logger log = LoggerFactory.getLogger(RegistrationBean.class);

    public synchronized void addUser() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setActive(true);
        user.setCreationDate(new Date());
        user.setPassword(password);
        log.info("Registration new user => {}", user.getEmail());
        userService.save(user);
        FacesUtil.addInfoMessage("User added successfull!");
    }

    //<editor-fold defaultstate="collapsed" desc="get/set methods">
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //</editor-fold>
}