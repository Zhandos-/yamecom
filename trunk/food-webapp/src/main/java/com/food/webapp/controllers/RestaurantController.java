/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.controllers;

import com.food.dao.TestDao;
import com.food.model.data.Address;
import com.food.model.data.City;
import com.food.model.data.District;
import com.food.model.enums.EnumRestaurantType;
import com.food.model.restaurant.Restaurant;
import com.food.model.restaurant.RestaurantDetails;
import com.food.model.restaurant.RestaurantType;
import com.food.model.user.User;
import com.food.webapp.services.RestaurantService;
import com.food.webapp.services.UserService;
import com.food.webapp.utils.RestaurantTypePropertyEditor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author zhandos
 */
@Controller
public class RestaurantController {

    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private TestDao testDao;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        System.out.println(">>>>>> initBinder <<<<<<");
        binder.registerCustomEditor(RestaurantType.class, new RestaurantTypePropertyEditor(restaurantService));
    }

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

    @RequestMapping(value = "/companion/restaurateur", method = RequestMethod.GET)
    private void restaurateurs(Map<String, Object> map) {
        List<Restaurant> restaurants = userService.getRestaurantsByUser();
        map.put("restaurants", restaurants);
    }

    @RequestMapping(value = "/companion/restaurateur/add", method = RequestMethod.GET)
    private String newRestaurateur(Map<String, Object> map) {
        List<RestaurantType> types = restaurantService.getRestaurantTypes();
        map.put("types", types);
        if (userService.getCurrentUser() == null) {
            return "redirect:/";
        } else {
            RestaurantDetails details = new RestaurantDetails();
            Restaurant r = new Restaurant();
            Address a = new Address();
            r.setAddress(a);
            r.setDetails(details);
            map.put("r", r);
        }
        return "/companion/restaurateur/add";
    }

    @RequestMapping(value = "/companion/restaurateur/add", method = RequestMethod.POST)
    private synchronized String newRestaurateur(Restaurant r,
            @RequestParam("city") String city,
            @RequestParam("mPhone") String mPhone,
            @RequestParam("sPhone") String sPhone,
            @RequestParam("restaurantLogo") Object obj) {
        CommonsMultipartFile file = (CommonsMultipartFile) obj;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date()) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4);
        String absolutePath = null;
        try {
            absolutePath = new File("").getAbsolutePath() + "/food/images/"
                    + userService.getCurrentUser().getEmail() + "/restaurant/logo/";
            File fTest = new File(absolutePath);
            if (!fTest.exists()) {
                boolean mkdirs = fTest.mkdirs();
                System.out.println("create new dir => " + mkdirs + " => " + absolutePath);
            }
            File f = new File(absolutePath + fileName);
            InputStream inputStream = file.getInputStream();
            OutputStream outputStream = new FileOutputStream(f);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        City c = new City();
        c.setName(city);
        District d = new District();
        d.setCity(c);
        r.getAddress().setDistrict(d);
        r.getDetails().setLogo(absolutePath + fileName);

        restaurantService.saveOrUpdate(r, mPhone, sPhone);
        return "redirect:/companion/restaurateur";
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
                EnumRestaurantType.PIZZA,
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
