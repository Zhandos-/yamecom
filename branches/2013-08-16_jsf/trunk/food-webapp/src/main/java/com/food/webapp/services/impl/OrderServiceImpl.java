/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services.impl;

import org.springframework.stereotype.Service;
import com.food.webapp.services.OrderService;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author TWINS
 */
@Service("orderService")
@Transactional("postgresT")
public class OrderServiceImpl implements OrderService {
    
}
