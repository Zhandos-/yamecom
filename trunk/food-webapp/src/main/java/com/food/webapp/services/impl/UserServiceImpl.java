/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services.impl;

import com.food.dao.PhoneDAO;
import com.food.dao.RoleDAO;
import com.food.dao.UserDAO;
import com.food.model.data.Phone;
import com.food.model.enums.EnumPhoneType;
import com.food.model.enums.EnumRole;
import com.food.model.user.Role;
import com.food.model.user.User;
import com.food.webapp.services.UserService;
import static com.food.webapp.services.impl.UserServiceImpl.getGrantedAuthorities;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserServiceImpl implements UserDetailsService, UserService, Serializable {

    private static final long serialVersionUID = 1369253307786229411L;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private PhoneDAO phoneDAO;
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public boolean authenticate(User user) {

        SecurityContextHolder.clearContext();
        User realUser = getUserByEmail(user.getEmail());
        String password = createHash(user.getPassword());

        if (realUser != null) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(realUser.getEmail(), user.getPassword(), getAuthorities(realUser.getRoles()));
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
        try {
            User innerUser = getUserByEmail(login);
            log.info("########################## login = {}", innerUser.getEmail());
            org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(innerUser.getEmail(), innerUser.getPassword(), true, true, true, true, getAuthorities(innerUser.getRoles()));
            return springUser;
        } catch (Exception ex) {
            log.warn(">>>> Пользователь не найден в базе : {} {}", login, ex.getMessage());
            return null;
        }
    }

    @Override
    public String generateUserPassword(Integer length) {
        Random rg = new Random();
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyz#$&@^*?";
        String password = "";
        for (int i = 0; i < length; i++) {
            password = password + alphabet.charAt(rg.nextInt(alphabet.length()));
        }
        log.info("*** Generated password: {}", password);
        return password;
    }

    @Override
    public String createHash(String password) {
        MessageDigest messageDigest;
        try {
            log.info(">>> create hash for password: {} ", password);
            messageDigest = MessageDigest.getInstance("md5");
            messageDigest.update(password.getBytes(), 0, password.length());
            String hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
            if (hashedPass.length() < 32) {
                hashedPass = "0" + hashedPass;
            }
            return hashedPass;
        } catch (NoSuchAlgorithmException e) {
            log.error("Error when create hashpassword: {}", e.getMessage());
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
    public Set<Role> getRolesByUserId(Long id) {
        return userDAO.getRolesByUserId(id);
    }

    @Override
    public List<User> allActiveUsers() {
        return userDAO.allActiveUsers();
    }

    @Override
    public boolean login(String email, String password) {
        boolean t = userDAO.login(email, createHash(password));
        if (t) {
            authenticate(getUserByEmail(email));
        }
        return t;
    }

    @Override
    public long saveClient(User user, String phone) {
        Long id;
        user.setPassword(createHash(user.getPassword()));
        Set<Role> roles = new HashSet<Role>();
        Role role = getRoleByEnum(EnumRole.ROLE_CLIENT);
        Phone phone1 = new Phone();
        phone1.setNumber(phone);
        phone1.setPhoneType(EnumPhoneType.MOBILE);
        if (user.getId() == null) {
            roles.add(role);
            user.setRoles(roles);
            user.setCreationDate(new Date());
            phone1.setUser(user);
            id = userDAO.save(user).getId();
            phoneDAO.save(phone1);
            return id;
        } else {
            roles.add(role);
            user.setRoles(roles);
            id = userDAO.update(user).getId();
            return id;
        }
    }

    @Override
    public Role getRoleByEnum(EnumRole role) {
        Role r = roleDAO.getRoleByName(role);
        if (r == null) {
            r = new Role();
            r.setDescription(role.getDescription());
            r.setName(role);
            roleDAO.save(r);
        }
        return r;
    }

    @Override
    public void registration(User user, String phone) {
        saveClient(user, phone);
        authenticate(user);
    }

    @Override
    public boolean checkEmail(String email) {
        User user = userDAO.getUserByEmail(email);
        if (user != null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isPasswordRight(String password) {
        password = createHash(password);
        User user = getCurrentUser();
        if (getCurrentUser() == null) {
            return false;
        } else {
            boolean t = password.equals(user.getPassword());
            return t;
        }
    }

    @Override
    public boolean changePassword(String password) {
        boolean response = false;
        try {
            User user = getUserByEmail(getCurrentUser().getEmail());
            user.setPassword(createHash(password));
            userDAO.update(user);
            response = true;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getLocalizedMessage());
            }
        }
        return response;
    }

    @Override
    public boolean updateProfile(User user) {
        boolean response = false;
        try {

            User u = getCurrentUser();
            u.setEmail(user.getEmail());
            u.setName(user.getName());
            u.setSurname(user.getSurname());
            userDAO.update(u);
            Phone phone = user.getPhones().get(0);
            phone.setUser(u);
            phoneDAO.update(phone);
            authenticate(u);
            response = true;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Ошибка при обновлении профиля", e.getLocalizedMessage());
            }
        }
        return response;
    }

    @Override
    public List<Phone> getCurrentUserPhones() {
        return getCurrentUser().getPhones();
    }

    @Override
    public boolean savePhonesForCurrentUser(List<Phone> phones) {
        if (log.isInfoEnabled()) {
            log.info("Вызов savePhonesForCurrentUser");
        }
        boolean response = false;
        try {
            User user = getCurrentUser();
            user.setPhones(phones);
            response = true;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Ошибка при обновлении телефонов профиля", e.getLocalizedMessage());
            }
        }
        if (log.isInfoEnabled()) {
            log.info("Конец вызова savePhonesForCurrentUser");
        }
        return response;
    }
}
