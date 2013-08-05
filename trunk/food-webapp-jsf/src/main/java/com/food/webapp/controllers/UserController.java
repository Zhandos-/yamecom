/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.controllers;

import com.food.model.user.User;
import com.food.webapp.services.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Andrey
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null == auth) {
            // throw new NotFndException("");
        }
        Object obj = auth.getPrincipal();
        String username = "";
        if (obj instanceof UserDetails) {
            username = ((UserDetails) obj).getUsername();
        } else {
            username = obj.toString();
        }
        User u = userService.getUserByEmail(username);

        return u;
    }

    @RequestMapping(value = "/")
    private String index() {
        return "index";
    }

    @RequestMapping(value = "/login")
    private String login(Map<String, Object> map) {
        User user = new User();
        map.put("user", user);
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    private String registration(Map<String, Object> map) {
        User user = new User();
        map.put("user", user);
        return "registration";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    private String save(User user) {
        userService.save(user);
        return "redirect:/";
    }
}
