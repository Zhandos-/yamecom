/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.controllers;

import com.food.dao.TestDao;
import com.food.model.enums.EnumRestaurantType;
import com.food.model.restaurant.Restaurant;
import com.food.model.restaurant.RestaurantDetails;
import com.food.model.restaurant.RestaurantType;
import com.food.model.user.User;
import com.food.webapp.services.RestaurantService;
import com.food.webapp.services.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
    private RestaurantService restaurantService;
    @Autowired
    private TestDao testDao;

    @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
    private String restaurants(Map<String, Object> map) {
        List<RestaurantType> types = restaurantService.getRestaurantTypes();
        map.put("types", types);
        return "/restaurants";
    }

    @RequestMapping(value = {"/restaurant"})
    private String restaurant(Map<String, Object> map,
            @RequestParam(value = "page", required = false) Integer pageNumber,
            @RequestParam(value = "minPrice", required = false) Integer minPrice,
            @RequestParam(value = "types[]", required = false) Integer types[],
            HttpServletResponse response) {
        List<RestaurantDetails> r = null;
        if (pageNumber == null) {
            pageNumber = 0;
        }

        if (types != null) {
            List<Long> ids = new ArrayList<Long>();
            for (Integer id : types) {
                ids.add(id.longValue());
                System.out.println(id);
            }
            r = restaurantService.filter(ids);
            map.put("restaurants", r);

        } else if (types == null && minPrice == null) {
            r = restaurantService.getRestaurants(pageNumber, 20);
            map.put("restaurants", r);
        }

        return "/includes/restaurants_list";
    }

    @RequestMapping(value = {"/searchRestaurant"})
    private String search(Map<String, Object> map,
            @RequestParam(value = "name", required = false) String name,
            HttpServletResponse response) {
        List<RestaurantDetails> r = restaurantService.getByName(name);
        map.put("restaurants", r);
        return "/includes/restaurants_list";
    }

    @RequestMapping(value = "/fill", method = RequestMethod.GET)
    @ResponseBody
    private String fill(Map<String, Object> map) {
        try {
            testDao.clean(RestaurantDetails.class);
            testDao.clean(Restaurant.class, User.class, RestaurantType.class);
            Random r = new Random();
            boolean bool[] = {true, false};
            double minPrice[] = {1000, 1500, 2000, 2500, 3000};
            List<RestaurantType> types = new ArrayList<RestaurantType>();
            EnumRestaurantType resaurantType[] = {
                EnumRestaurantType.CHINESE_JAPANESE,
                EnumRestaurantType.FAST_FOOD,
                EnumRestaurantType.ITALIAN_EUROPE,
                EnumRestaurantType.PICCA,
                EnumRestaurantType.SUSHI
            };
            for (EnumRestaurantType type : resaurantType) {
                RestaurantType type1 = new RestaurantType();
                type1.setType(type);
                type1.setDescription(type.getDescription());
                testDao.save(type1);
                types.add(type1);
            }


            for (int i = 0; i < 40; i++) {
                User user = testDao.fillClient("ddd");
                Restaurant restaurant = new Restaurant();
                restaurant.setAbout("Мы давно работаем уже" + i);
                restaurant.setCreationDate(new Date());
                restaurant.setName("Ресторан" + i);
                restaurant.setUser(user);
                testDao.save(restaurant);
                {
                    RestaurantDetails details = new RestaurantDetails();
                    details.setRestaurant(restaurant);
                    details.setDeliveryFree(bool[r.nextInt(bool.length)]);
                    details.setStartTime(new Date());
                    details.setEndTime(new Date());
                    details.setMinOrderPrice(minPrice[r.nextInt(minPrice.length)]);
                    details.setPayByCard(bool[r.nextInt(bool.length)]);
                    details.getTypes().add(types.get(r.nextInt(types.size())));
                    testDao.save(details);
                }

            }
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return "OK";
    }
}
