/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao;

import com.food.model.enums.EnumRole;
import com.food.model.user.Role;

/**
 *
 * @author daniyar.artykov
 */
public interface RoleDAO extends BaseDAO<Role, Long> {

    public Role getRoleByName(EnumRole name);
}
