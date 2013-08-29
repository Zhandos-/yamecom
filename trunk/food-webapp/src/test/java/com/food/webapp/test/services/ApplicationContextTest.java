/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.test.services;

import com.food.model.user.User;
import com.food.webapp.services.UserService;
import java.util.Date;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daniyar.artykov
 */
@ContextConfiguration({"classpath:/configs/applicationContext-business.xml"})
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
public class ApplicationContextTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private UserService userService;

    @Test
    public void testUserService() {
        User user = new User();
        user.setEmail("doni@gmail.com");
        user.setPassword("s3cret");
        user.setName("Doni");
        user.setCreationDate(new Date());
        userService.registration(user, "+7 (702) 820-52-25");
        User u = userService.getUserByEmail("doni@gmail.com");
        System.out.println(u);
    }
}