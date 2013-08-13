/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.controllers;

import com.food.model.user.User;
import com.food.webapp.services.UserService;
import java.util.Map;
import javax.faces.bean.ManagedProperty;
import org.springframework.beans.factory.annotation.Autowired;
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
    UserService userService;
//    @ManagedProperty(value="#{user}")
//    User user;
    @RequestMapping(value = "/")
    private String index(Map<String, Object> map) {
 
        User u=new User();
        u.setName("Zhandos");
        u.setSurname("Dauyl");
        u.setEmail("j@h.kz");
        map.put("user", u);
        return "index";
    }
     
       @RequestMapping(value = "/profile")
    private String logout(Map<String, Object> map) {
         User user=userService.getUser(userService.getCurrentUser().getId());
         
         map.put("user",user);
        return "client/profile";
    }
    
    
        @RequestMapping(value = "/login")
    private String login(  Map<String, Object> map ) {
        User user=new User();   
        map.put("user", user);
        return "login";
    }
        
        @RequestMapping(value = "/registration", method = RequestMethod.GET)
    private String registration(  Map<String, Object> map ) {
        User user=new User();   
        map.put("user", user);
        return "registration";
    }    
        
 
        
           @RequestMapping(value = "/registration", method = RequestMethod.POST)
    private String registration(User user) {
        userService.registration(user);
        return "redirect:/";
    }       
        
}
