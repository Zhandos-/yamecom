/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services.impl;

import com.food.dao.FoodDAO;
import com.food.model.food.Food;
import com.food.webapp.services.FoodService;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TWINS
 */
@Service("foodService")
@Transactional("postgresT")
public class FoodServiceImpl implements FoodService, Serializable {

    private static final long serialVersionUID = 3285458768543050522L;
    @Autowired
    private FoodDAO foodDAO;

    @Override
    public long save(Food food) {
        return foodDAO.save(food).getId();
    }

    @Override
    public long update(Food food) {
        return foodDAO.update(food).getId();
    }

    @Override
    public void delete(long id) {
        foodDAO.deleteById(id);
    }

    @Override
    public void saveAll(List<Food> foods) {
        foodDAO.saveAll(foods);
    }

    @Override
    public void deleteAll(List<Long> foods) {
        foodDAO.deletelAllById(foods);
    }
}
