/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services;

import com.food.model.food.Food;
import java.util.List;

/**
 *
 * @author TWINS
 */
public interface FoodService {
    public long save(Food food);
    
    public long update(Food food);
    
    public void delete(long id);
    
    public void saveAll(List<Food> foods);
    
    public void deleteAll(List<Long> foods);
}
