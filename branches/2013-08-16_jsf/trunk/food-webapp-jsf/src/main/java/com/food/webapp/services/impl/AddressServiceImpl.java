/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services.impl;

import com.food.dao.AddressDAO;
import com.food.model.data.Address;
import com.food.model.user.User;
import com.food.webapp.services.AddressService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.food.webapp.services.UserService;
import java.io.Serializable;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TWINS
 */
@Service("addressService")
@Transactional("postgresT")
public class AddressServiceImpl implements AddressService, Serializable {

    private static final long serialVersionUID = 5223813995345850774L;
    @Autowired
    private AddressDAO addressDAO;
    @Autowired
    UserService userService;

    @Override
    public long saveOrUpdate(Address address) {
        User user = userService.getCurrentUser();
        address.setUser(user);
        return addressDAO.save(address).getId();
    }

    @Override
    public void delete(Address address) {
        addressDAO.delete(address);
    }

    @Override
    public void deleteAll(Set<Long> addressId) {
        addressDAO.deleteAll(addressId);
    }
}
