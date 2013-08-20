/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.test.services;

import com.food.dao.BaseDAO;
import com.food.model.user.User;
import com.food.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import org.testng.Assert;

/**
 *
 * @author TWINS
 */
@ContextConfiguration(locations = {"classpath:/configs/applicationTest.xml"})
public class UserServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BaseDAO baseDAO;
    @Autowired
    private UserService userService;

//    @Test
    public void authenticate() {
        User u = new User();
        u.setName("Zhandos");
        u.setSurname("Dauyl");
        u.setEmail("zh456");
        u.setPassword("z");
        userService.registration(u);
        User saved = userService.getUserByEmail(null);
//        Assert.assertNotNull(saved);
    }
}
