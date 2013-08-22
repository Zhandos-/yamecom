/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.controllers;

import com.food.model.restaurant.Restaurant;
import com.food.webapp.services.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author zhandos
 */
@Controller
public class RestaurantController {

    @Autowired
    UserService userService;

 

    @RequestMapping(value = "/restaurants",method = RequestMethod.GET)
    private String restaurants(Map<String, Object> map) {
        List<Restaurant> r=new ArrayList<Restaurant>();
        for(int i=0;i<5;i++)
        {
            
        }
        return "/restaurants";
    }
    
 @RequestMapping(value = "/restaurant")
    private  @ResponseBody List<Restaurant> restaurant(Map<String, Object> map,
    @RequestParam(value = "id", required = false) Long id,HttpServletResponse response) {
        List<Restaurant> r=new ArrayList<Restaurant>();
        for(int i=0;i<5;i++)
        {
            Restaurant restaurant=new Restaurant();
            restaurant.setAbout("У нас покупай!"+i);
            restaurant.setName("REstaurant"+i);
            r.add(restaurant);
        }
        return r;
    }
   
}
