/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.test.services;

import com.food.dao.TestDao;
import com.food.model.data.Phone;
import com.food.model.enums.EnumRole;
import com.food.model.user.User;
import com.food.webapp.services.UserService;
import com.food.webapp.utils.UserUtil;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daniyar.artykov
 */
@ContextConfiguration({"classpath:/configs/applicationContext-business.xml"})
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
public class UserServiceImplTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private UserService userService;
    @Autowired
    private TestDao testDao;

    @Test
    public void registration() {
        testDao.clean(User.class, Phone.class);
        {
            User user = new User();
            user.setEmail("doni@gmail.com");
            user.setPassword("s3cret");
            user.setName("Doni");
            userService.registration(user, "+7 (702) 820-52-25", EnumRole.ROLE_ADMIN);
            User u = userService.getUserByEmail("doni@gmail.com");
            assertNotNull(u);
            assertEquals(user.getEmail(), u.getEmail());
            assertEquals(user.getPassword(), u.getPassword());
            assertEquals(user.getName(), u.getName());
            assertNotNull(u.getCreationDate());
            assertNotNull(userService.getCurrentUser());
//            assertEquals("+7 (702) 820-52-25", u.getPhones().get(0).getPhoneType());
//            assertEquals(EnumPhoneType.MOBILE, u.getPhones().get(0).getPhoneType());
        }
        {
            User user = new User();
            user.setEmail("doni@gmail.comh");
            user.setPassword("s3cret");
            user.setName("Doni");
            user.setCreationDate(new Date());
            UserUtil.createHash("");
            userService.registration(user, "+7 (702) 820-52-25", EnumRole.ROLE_CLIENT);
            User u = userService.getUserByEmail("doni@gmail.comh");
            assertNotNull(u);
        }
    }

    @Test
    public void login() {
        testDao.clean(User.class);
        String password = "csasacd";
        User u = testDao.fillClient(UserUtil.createHash(password));
        assertNotNull(u);

        {
            assertTrue(userService.login(u.getEmail(), password));
            assertNotNull(userService.getCurrentUser());
        }
        {
            assertFalse(userService.login(u.getEmail(), "s3cret5"));

        }
    }

    @Test
    public void login2() {
        testDao.clean(User.class);
        String password = "csasacd";
        User u = testDao.fillClient(UserUtil.createHash(password));
        User s = new User();
        s.setEmail("ssq");
        s.setPassword("dq");
        userService.authenticate(s);
    }

    @Test
    public void isPasswordRight() {
        testDao.clean(User.class, Phone.class);
        String password = "csasacd";
        User u = testDao.fillClient(UserUtil.createHash(password));
        userService.login(u.getEmail(), password);
        assertTrue(userService.isPasswordRight(password));
    }

    @Test
    public void changePassword() {
        String newPassword = "45454545";
        testDao.clean(User.class);
        String password = "csasacd";
        User u = testDao.fillClient(UserUtil.createHash(password));
        userService.login(u.getEmail(), password);
        userService.changePassword(newPassword);
        assertEquals(UserUtil.createHash(newPassword), userService.getCurrentUser().getPassword());
    }

    @Test
    public void updateProfile() {
        testDao.clean(User.class, Phone.class);
        String password = "csasacd";
        User u = testDao.fillClient(UserUtil.createHash(password));
        userService.login(u.getEmail(), password);
        User user2 = new User();
        user2.setName("name");
        user2.setSurname("surname");
        user2.setEmail("dqkjkjk");
        userService.updateProfile(user2);
        User realUser = userService.getCurrentUser();
        assertEquals(u.getId(), realUser.getId());
        assertEquals(user2.getName(), realUser.getName());
        assertEquals(user2.getSurname(), realUser.getSurname());
        assertEquals(user2.getEmail(), realUser.getEmail());
    }

    @Test
    public void savePhonesForCurrentUser() {
        testDao.clean(User.class, Phone.class);
        String password = "csasacd";
        User u = testDao.fillClient(UserUtil.createHash(password));
        userService.login(u.getEmail(), password);
        List<Phone> phones = u.getPhones();
        phones.remove(0);
        userService.savePhonesForCurrentUser(phones);
//        assertNull(userService.getCurrentUser().getPhones());
    }
}
