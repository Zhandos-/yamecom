/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao;

import com.food.model.data.Address;
import java.util.Set;

/**
 *
 * @author TWINS
 */
public interface AddressDAO extends BaseDAO<Address,Long>{
  public void deleteAll(Set<Long> addressId);
}
