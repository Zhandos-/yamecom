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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Andrey
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    private String index() {
        return "index";
    }

    @RequestMapping(value = "/profile")
    private String logout(Map<String, Object> map) {
        User user = userService.getUser(userService.getCurrentUser().getId());

        map.put("user", user);
        return "client/profile";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    private String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private @ResponseBody
    boolean login(@RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "password", required = true) String password) {
        return userService.login(email, password);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    private String registration(Map<String, Object> map) {
        User user = new User();
        map.put("user", user);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    private String registration(User user, @RequestParam("phone") String phone) {
        userService.registration(user, phone);
        return "redirect:/";
    }

    @RequestMapping(value = "/checkemail", method = RequestMethod.POST)
    private @ResponseBody
    boolean checkEmail(@RequestParam(value = "email", required = true) String email) {
        return userService.checkEmail(email);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    private String editProfile(User user, @RequestParam("phone") String phone) {
        userService.registration(user, phone);
        return "redirect:/";
    }
}
