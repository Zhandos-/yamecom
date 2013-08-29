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

/**
 *
 * @author TWINS
 */
@ContextConfiguration(locations = {"classpath:/configs/applicationContext-business.xml"})
public class UserServiceImplTest {

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
//        userService.registration(u);
        User saved = userService.getUserByEmail(null);
//        Assert.assertNotNull(saved);
    }
}
