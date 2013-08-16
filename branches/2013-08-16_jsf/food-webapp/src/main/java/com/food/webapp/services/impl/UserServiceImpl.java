/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services.impl;

import com.food.dao.RoleDAO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TWINS
 */
@Service("userService")
@Transactional("postgresT")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
   private UserDAO userDAO;
   @Autowired
   private RoleDAO roleDAO;
    public boolean  authenticate(User user)
    {
         SecurityContextHolder.clearContext();
        User user2 = getUserByEmail(user.getEmail());
         String password=createHash(user.getPassword());
         
        if (user2!= null) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(user2.getEmail(), user.getPassword(),getAuthorities(user2.getRoles()));
//            authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
         return false;
    }
    
    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null == auth) {
            // throw new NotFndException("");
        }
        Object obj = auth.getPrincipal();
        String username = "";
        if (obj instanceof UserDetails) {
            username = ((UserDetails) obj).getUsername();
        } else {
            username = obj.toString();
        }
        User u = getUserByEmail(username);

        return u;
    }
    
    @Override
    public User getUser(Long id) {
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

    @Override
    public boolean login(User user) {
        String mail=user.getEmail();
        String password=createHash(user.getPassword());
        return userDAO.login(mail, password);
    }

    @Override
    public long save(User user) {
        user.setPassword(createHash(user.getPassword()));
        Set<Role> roles=new HashSet<Role>();
        Role role=roleDAO.getRoleByName(EnumRole.ROLE_CLIENT);
        if(role==null)
        {
            role=new Role();
            role.setDescription("для клиентов");
            role.setName(EnumRole.ROLE_CLIENT);
            roleDAO.save(role);
        }
        if(user.getId()==null)
        {
        roles.add(role);
        user.setRoles(roles);
        return userDAO.save(user).getId();
        }
        else
        {
        roles.add(role);
        user.setRoles(roles);    
        return userDAO.update(user).getId();
        }
        
    }

    @Override
    public void registration(User user) {
        save(user);
        authenticate(user);
    }
}
