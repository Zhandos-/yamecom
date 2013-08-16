/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services;

import com.food.model.user.Role;
import com.food.model.user.User;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author Andrey
 */
public interface UserService {

    public User getCurrentUser();
    
    public User getUser(Long id);

    public List<User> allUsers();

    public List<User> allActiveUsers();

    public String generateUserPassword(Integer length);

    public User getUserByEmail(String email);

    public String createHash(String password);

    public Collection<? extends GrantedAuthority> getAuthorities(Set<Role> rolesList);

    public Role getRoles(Integer id);
    
    public boolean login(User user);
    
    public long save(User user);
    
    public void registration(User user);
}
