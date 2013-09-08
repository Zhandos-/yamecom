/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.controllers;

import com.food.model.enums.EnumRole;
import com.food.model.user.User;
import com.food.webapp.services.UserService;
import java.util.Map;
import javax.servlet.http.HttpSession;
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    private String login() {
        if (userService.getCurrentUser() != null) {
            return "redirect:/";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private @ResponseBody
    boolean login(@RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "password", required = true) String password, HttpSession httpSession) {
        return userService.login(email, password);
    }

    @RequestMapping(value = "/client/registration", method = RequestMethod.GET)
    private String registration(Map<String, Object> map) {
        if (userService.getCurrentUser() != null) {
            return "redirect:/";
        } else {
            User user = new User();
            map.put("user", user);
        }
        return "client/registration";
    }

    @RequestMapping(value = "/client/registration", method = RequestMethod.POST)
    private String registration(User user, @RequestParam("phone") String phone) {
        userService.registration(user, phone, EnumRole.ROLE_CLIENT);
        return "redirect:/";
    }

    @RequestMapping(value = "/companion/registration", method = RequestMethod.GET)
    private String companionRegistration(Map<String, Object> map) {
        if (userService.getCurrentUser() != null) {
            return "redirect:/";
        } else {
            User user = new User();
            map.put("user", user);
        }
        return "companion/registration";
    }

    @RequestMapping(value = "/companion/registration", method = RequestMethod.POST)
    private String companionRegistration(User user, @RequestParam("phone") String phone) {
        userService.registration(user, phone, EnumRole.ROLE_COMPANION);
        return "redirect:/";
    }

    @RequestMapping(value = {"/companion/checkemail", "/client/checkemail"}, method = RequestMethod.POST)
    private @ResponseBody
    boolean checkEmail(@RequestParam(value = "email", required = true) String email) {
        email = email.toLowerCase();
        User user = userService.getCurrentUser();
        if (user != null) {
            if (user.getEmail().equals(email)) {
                return true;
            } else {
                return userService.checkEmail(email);
            }
        } else {
            return userService.checkEmail(email);
        }
    }

//    @RequestMapping(value = {"/companion/checkemail", "client/checkemail"}, method = RequestMethod.POST)
//    private @ResponseBody
//    boolean companionCheckEmail(@RequestParam(value = "email", required = true) String email) {
//        email = email.toLowerCase();
//        User user = userService.getCurrentUser();
//        if (user != null) {
//            if (user.getEmail().equals(email)) {
//                return true;
//            } else {
//                return userService.checkEmail(email);
//            }
//        } else {
//            return userService.checkEmail(email);
//        }
//    }
    @RequestMapping(value = "client/changeProfile", method = RequestMethod.POST)
    private @ResponseBody
    boolean editProfile(User user) {
        return userService.updateProfile(user);

    }

    @RequestMapping(value = {"/isPasswordRight", "client/isPasswordRight"}, method = RequestMethod.POST)
    private @ResponseBody
    boolean isPasswordRight(@RequestParam(value = "oldpassword", required = true) String password) {
        User user = userService.getCurrentUser();
        if (user != null) {
            return userService.isPasswordRight(password);
        } else {
            return false;
        }
    }

    @RequestMapping(value = "client/changePasssword", method = RequestMethod.POST)
    private @ResponseBody
    boolean editPasssword(@RequestParam(value = "password", required = true) String password) {
        return userService.changePassword(password);

    }
}
