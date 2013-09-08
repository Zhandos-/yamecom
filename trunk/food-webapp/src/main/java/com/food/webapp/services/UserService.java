/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services;

import com.food.model.data.Phone;
import com.food.model.enums.EnumRole;
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

    public Set<Role> getRolesByUserId(Long id);

    public Role getRoleByEnum(EnumRole role);

    public boolean login(String email, String password);

    public long saveClient(User user, String phone, EnumRole enumRole);

    public void registration(User user, String phone, EnumRole enumRole);

    public boolean checkEmail(String email);

    public boolean authenticate(User user);

    public boolean isPasswordRight(String password);

    public boolean changePassword(String password);

    public boolean updateProfile(User user);

    public List<Phone> getCurrentUserPhones();

    public boolean savePhonesForCurrentUser(List<Phone> phones);
}
