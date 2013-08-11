/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.OrderDAO;
import com.food.model.food.Order;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TWINS
 */
@Repository
public class OrderDAOImpl extends BaseDAOImpl<Order,Long> implements OrderDAO{
}
