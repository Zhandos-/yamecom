/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.controllers;

import com.food.model.auth.User;
import com.food.webapp.services.UserService;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Andrey
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    private String loginform(Map<String, Object> map, HttpSession session) {
        return "index";
    }

    @JsonIgnore
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private String loginform(Map<String, Object> map,
            HttpSession session,
            @RequestParam(value = "rememberme", required = false) boolean remember,
            @RequestParam("login") String login,
            @RequestParam("password") String password) {
        // session.removeAttribute("user");
        SecurityContextHolder.clearContext();
//        User user = userService.getUserbyLogin(login);
//
//        if (user != null && user.getPassword().equals(userService.createHash(password))) {
//
//            Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), userService.getAuthorities(user.getRole()));
//            session.setAttribute("user", ((User) user));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            if (remember) {
//                session.setMaxInactiveInterval(2880);
//            }
//            if (user.getUserRole() == 1) {
//                return "redirect:/clients/orderlist";
//            } else {
//                if (user.getUserRole() == 3) {
//                    return "redirect:/admin/orderlist";
//                } else {
//                    return "redirect:/employers/orderlist";
//                }
//            }
//
//
//        } else {
//            map.put("login_error", "Not user");
//        }
        User user = getCurrentUser();
        if (user == null) {
            return "login";
        } else {
//            if (user.getUserRole() == 1) {
//                return "redirect:/clients/orderlist";
//            } else {
//                if (user.getRole().getId() == 3) {
//                    return "redirect:/admin/orderlist";
//                } else {
//                    return "redirect:/employers/orderlist";
//                }
//            }
            return null;
        }
    }
}
