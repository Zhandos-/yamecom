/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services;

import com.food.model.data.Address;
import java.util.Set;

/**
 *
 * @author TWINS
 */
public interface AddressService {

    public long saveOrUpdate(Address address);

    public void delete(Address address);

    public void deleteAll(Set<Long> addressId);
}
