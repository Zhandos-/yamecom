/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.FoodDAO;
import com.food.model.food.Food;
import java.io.Serializable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TWINS
 */
@Repository
public class FoodDAOImpl extends BaseDAOImpl<Food, Long> implements FoodDAO, Serializable {

    private static final long serialVersionUID = 1273111943636733438L;
}
