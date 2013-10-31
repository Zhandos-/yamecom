/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.test.services;

import com.food.dao.RestaurantTypeDAO;
import com.food.dao.TestDao;
import com.food.model.enums.EnumRestaurantType;
import com.food.model.restaurant.Restaurant;
import com.food.model.restaurant.RestaurantDetails;
import com.food.model.restaurant.RestaurantType;
import com.food.model.user.User;
import com.food.webapp.services.RestaurantService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daniyar.artykov
 */
@ContextConfiguration({"classpath:/configs/applicationContext-business.xml"})
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
public class RestaurantServiceImplTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private TestDao testDao;
    @Autowired
    private RestaurantTypeDAO restaurantTypeDAO;

    @Test
    public void checkRestaurantsType() {
        List<EnumRestaurantType> restaurantTypes = new ArrayList<EnumRestaurantType>(Arrays.asList(EnumRestaurantType.values()));
        if (restaurantTypeDAO.getAll() == null || restaurantTypeDAO.getAll().isEmpty()) {
            System.out.println(">>>");
            for (EnumRestaurantType e : restaurantTypes) {
                System.out.println("e = " + e.name());
                RestaurantType rt = new RestaurantType();
                rt.setType(e);
                rt.setDescription(e.getDescription());
                restaurantTypeDAO.save(rt);
            }
        }
    }

    @Test
    public void getRestaurants() {
        testDao.clean(RestaurantDetails.class);
        boolean bool[] = {true, false};
        double minPrice[] = {1000, 1500, 2000, 2500, 3000};
        Random r = new Random();
        testDao.clean(Restaurant.class, User.class);
        for (int i = 0; i < 40; i++) {
            User user = testDao.fillClient("ddd");
            Restaurant restaurant = new Restaurant();
            restaurant.setAbout("Мы давно работаем уже" + i);
            restaurant.setCreationDate(new Date());
            restaurant.setName("Ресторан" + i);
            restaurant.setUser(user);
            {
                RestaurantDetails details = new RestaurantDetails();
                details.setRestaurant(restaurant);
                details.setDeliveryFree(bool[r.nextInt(1)]);
                details.setStartTime(new Date());
                details.setEndTime(new Date());
                details.setMinOrderPrice(minPrice[r.nextInt(5)]);
                details.setPayByCard(bool[r.nextInt(2)]);
                testDao.save(details);
            }
            testDao.save(restaurant);
        }
        List<RestaurantDetails> list;
        list = restaurantService.getRestaurants(20, 20);
        Assert.assertEquals(20, list.size());
    }

    @Test
    public void search() {
        testDao.clean(RestaurantDetails.class);
        boolean bool[] = {true, false};
        double minPrice[] = {1000, 1500, 2000, 2500, 3000};
        Random r = new Random();
        testDao.clean(Restaurant.class, User.class);
        for (int i = 0; i < 40; i++) {
            User user = testDao.fillClient("ddd");
            Restaurant restaurant = new Restaurant();
            restaurant.setAbout("Мы давно работаем уже" + i);
            restaurant.setCreationDate(new Date());
            restaurant.setName("Ресторан" + i);
            restaurant.setUser(user);
            {
                RestaurantDetails details = new RestaurantDetails();
                details.setRestaurant(restaurant);
                details.setDeliveryFree(bool[r.nextInt(1)]);
                details.setStartTime(new Date());
                details.setEndTime(new Date());
                details.setMinOrderPrice(minPrice[r.nextInt(5)]);
                details.setPayByCard(bool[r.nextInt(2)]);
                testDao.save(details);
            }
            testDao.save(restaurant);
        }
        List<RestaurantDetails> list;
        list = restaurantService.getByName("Ресторан");
        Assert.assertEquals(40, list.size());
    }

    @Test
    public void filter() {
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
        List<Long> ids = new ArrayList<Long>();
        ids.add(types.get(0).getId());
        List<RestaurantDetails> list;
        list = restaurantService.filter(ids);
        Assert.assertEquals(40, list.size());
    }
}
