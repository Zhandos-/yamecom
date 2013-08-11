/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services.impl;

import com.food.dao.RoleDAO;
import com.food.model.enums.EnumRole;
import com.food.model.user.Role;
import com.food.webapp.services.InsertService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TWINS
 */
@Service("insertService")
@Transactional("postgresT")
public class InsertServiceImpl implements InsertService {
@Autowired RoleDAO roleDAO;
//    @PostConstruct
    void insert()
    {
        insertRoles();
    }
   @Transactional("postgresT")
   @Override
    public void insertRoles() {
    for(EnumRole role:EnumRole.values())
    {
       Role r=roleDAO.getRoleByName(role);
       if(r==null)
       {
           r=new Role();
           r.setName(role);
           r.setDescription(role.getDescription());
       }
    }
    }
   
}
