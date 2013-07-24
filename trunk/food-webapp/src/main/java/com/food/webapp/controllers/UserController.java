/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.controllers;

import com.food.model.auth.ERole;
import com.food.model.auth.User;
import com.food.webapp.services.UserService;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        User u = userService.getUserbyLogin(username);

        return u;
    }

    // GEt
    @RequestMapping("/spravka")
    private String spravka(Map<String, Object> map) {

        return "spravka";
    }

    @RequestMapping("/login/register")
    private String loadForm(Map<String, Object> map) {
        System.out.println("1");
        map.put("newuser", new User());
        return "login/register";
    }

    //POST SAVE NEW USER
    @RequestMapping(value = "/login/register/reg", method = RequestMethod.POST)
    private String register(@ModelAttribute(value = "newuser") @Valid User user, BindingResult result) {

        if (result.hasErrors()) {

            return "/login/register/reg";

        } else {
            // пароль  в MD5
            user.setLoginPassword(userService.createHash(user.getLoginPassword()));
//            // Задаем роль
//            if (user.getUserRole() == 1) {
//                user.setRole(userService.getRoles(1));
//            } else if (user.getUserRole() == 2) {
//                user.setRole(userService.getRoles(2));
//            } else if (user.getUserRole() == 3) {
//                user.setRole(userService.getRoles(3));
//            }
            // Сохраняем    
            userService.addOrUpdateUser(user);
        }

        return "redirect:/index";
    }
    // Admin all user

    @RequestMapping("/admin/usershow")
    private String listUsers(Map<String, Object> map) {
        map.put("listUsers", userService.listUser());
        return "/admin/usershow";
    }

    @RequestMapping("/admin/useredit/edit/{id}")
    public String getEditUser(Map<String, Object> map, @PathVariable("id") Integer id) {
        System.out.println("3");
        map.put("user", userService.getUser(id));
        return "admin/useredit";
    }

    @RequestMapping(value = "/admin/useredit/edit", method = RequestMethod.POST)
    private String editUser(@ModelAttribute(value = "user") User user, BindingResult result) {
        if (!result.hasErrors()) {
            userService.addOrUpdateUser(user);
            return "redirect:../usershow";
        } else {
        }
        return "redirect:../useredit";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    private String loginform(Map<String, Object> map, HttpSession session) {
//        session.removeAttribute("user");
//        SecurityContextHolder.clearContext();
        User user = getCurrentUser();
        if (user == null) {
            return "login";
        } else {
            if (user.getRoles().contains(ERole.ROLE_CLIENT)) {
                return "redirect:/clients/orderlist";
            } else {
//                if (user.getRole().getId() == 3) {
//                    return "redirect:/admin/orderlist";
//                } else {
//                    return "redirect:/employers/orderlist";
//                }
                return null;
            }
        }
    }

    @JsonIgnore
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private String loginform(Map<String, Object> map,
            HttpSession session,
            @RequestParam(value = "rememberme", required = false) boolean remember,
            @RequestParam("login") String login,
            @RequestParam("password") String password) {
        // session.removeAttribute("user");
        // SecurityContextHolder.clearContext();
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
