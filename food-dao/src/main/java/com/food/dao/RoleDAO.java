/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao;

import com.food.model.auth.ERole;
import com.food.model.auth.Role;

/**
 *
 * @author daniyar.artykov
 */
public interface RoleDAO {

    public Role getRoleById(Long id);

    public Role getRoleByName(ERole name);
}
