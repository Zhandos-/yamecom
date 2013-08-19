/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.OrderDAO;
import com.food.model.food.Order;
import java.io.Serializable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TWINS
 */
@Repository
public class OrderDAOImpl extends BaseDAOImpl<Order, Long> implements OrderDAO, Serializable {

    private static final long serialVersionUID = 1867927217014305824L;
}