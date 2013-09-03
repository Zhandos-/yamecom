/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.controllers;

import com.food.model.user.User;
import com.food.webapp.services.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author TWINS
 */
@Controller
public class ClientController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "client/profile")
    private String logout(Map<String, Object> map) {
        User user = userService.getUser(userService.getCurrentUser().getId());

        map.put("user", user);
        return "client/profile";
    }
}
