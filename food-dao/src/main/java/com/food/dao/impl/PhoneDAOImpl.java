/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.PhoneDAO;
import com.food.model.data.Phone;
import java.io.Serializable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TWINS
 */
@Repository
public class PhoneDAOImpl extends BaseDAOImpl<Phone, Long> implements PhoneDAO, Serializable {

    private static final long serialVersionUID = 5105691350032035974L;
}
