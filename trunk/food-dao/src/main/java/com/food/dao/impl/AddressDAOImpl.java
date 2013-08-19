/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.AddressDAO;
import com.food.model.data.Address;
import java.io.Serializable;
import java.util.Set;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TWINS
 */
@Repository
public class AddressDAOImpl extends BaseDAOImpl<Address, Long> implements AddressDAO, Serializable {

    private static final long serialVersionUID = 2585518248603364526L;

    @Override
    public void deleteAll(Set<Long> addressId) {
        for (Long id : addressId) {
            super.deleteById(id);
        }
    }
}
