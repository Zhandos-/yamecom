/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.dao.impl;

import com.food.dao.RoleDAO;
import com.food.model.enums.EnumRole;
import com.food.model.user.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAOImpl extends BaseDAOImpl<Role, Long> implements RoleDAO {

    @Override
    public Role getRoleByName(EnumRole name) {

        return (Role) ht().createQuery("from Role where name = :name")
                .setParameter("name", name)
                .uniqueResult();
    }
}
