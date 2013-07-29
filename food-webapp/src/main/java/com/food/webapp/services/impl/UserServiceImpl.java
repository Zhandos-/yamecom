/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services.impl;

import com.food.dao.UserDAO;
import com.food.model.enums.EnumRole;
import com.food.model.user.Role;
import com.food.model.user.User;
import com.food.webapp.services.UserService;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional("postgresT")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public void addOrUpdateUser(User user) {
        userDAO.addOrUpdateUser(user);
    }

    @Override
    public User getUser(Integer id) {
        return userDAO.getUser(id);
    }

    @Override
    public List<User> allUsers() {
        return userDAO.allUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {
        User innerUser = getUserByEmail(login);
        System.out.println(innerUser.getEmail() + "  ########################## login");
        if (innerUser == null) {
            System.out.println(" Нет пользователя  ########################## login");
            throw new UsernameNotFoundException("user not found in database");
        }
        org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(innerUser.getEmail(), innerUser.getPassword(), true, true, true, true, getAuthorities(innerUser.getRoles()));

        return springUser;
    }

    @Override
    public String generateUserPassword(Integer length) {
        Random rg = new Random();
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyz#$&@^*?";
        String password = "";
        for (int i = 0; i < length; i++) {
            password = password + alphabet.charAt(rg.nextInt(alphabet.length()));
        }
        System.out.println("*** Generated password:" + password);
        return password;
    }

    @Override
    public String createHash(String password) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("md5");
            messageDigest.update(password.getBytes(), 0, password.length());
            String hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
            if (hashedPass.length() < 32) {
                hashedPass = "0" + hashedPass;
            }
            return hashedPass;
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    @Override
    public GrantedAuthority[] defaultAuthority() {
        GrantedAuthority[] authorities = new GrantedAuthority[1];
        authorities[0] = new SimpleGrantedAuthority(EnumRole.ROLE_ANONYMOUS.toString());
        return authorities;
    }

    @Override
    public GrantedAuthority[] consumerAuthority() {
        GrantedAuthority[] authorities = new GrantedAuthority[1];
        authorities[0] = new SimpleGrantedAuthority(EnumRole.ROLE_CONSUMER.toString());
        return authorities;
    }

    @Override
    public GrantedAuthority[] adminAuthority() {
        GrantedAuthority[] authorities = new GrantedAuthority[1];
        authorities[0] = new SimpleGrantedAuthority(EnumRole.ROLE_ADMIN.toString());
        return authorities;
    }

    @Override
    public GrantedAuthority[] clientAuthority() {
        GrantedAuthority[] authorities = new GrantedAuthority[1];
        authorities[0] = new SimpleGrantedAuthority(EnumRole.ROLE_CLIENT.toString());
        return authorities;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public List<String> getRoles(Set<Role> rolesList) {
        List<String> roles = new ArrayList<String>();
        for (Role role : rolesList) {
            roles.add(role.getName().toString());
        }
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(Set<Role> rolesList) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(rolesList));
        return authList;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public Role getRoles(Integer id) {
        return userDAO.getRoles(id);
    }

    @Override
    public List<User> allActiveUsers() {
        return userDAO.allActiveUsers();
    }
}
