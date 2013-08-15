/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author daniyar.artykov
 */
public class TestApp {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Test
    @Ignore
    public void test() {
        MessageDigest messageDigest;
        String password = "s3cret";
        try {
            messageDigest = MessageDigest.getInstance("md5");
            messageDigest.update(password.getBytes(), 0, password.length());
            String hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
            if (hashedPass.length() < 32) {
                hashedPass = "0" + hashedPass;
            }
            System.out.println(hashedPass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Before
    @Ignore
    public void init() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("foodPU");
            em = emf.createEntityManager();
        }
    }

    @Test
    @Ignore
    public void addUsers() {
        try {
            em.getTransaction().begin();

//            Query q = em.createQuery("from Role r where r.name = :name");
//            q.setParameter("name", EnumRole.ROLE_ADMIN);
//            List<Role> role = q.getResultList();

//            Query q = em.createQuery("from User");
//            List<User> users = q.getResultList();

//            User user1 = new User();
//            user1.setName("Daniyar");
//            user1.setActive(true);
//            user1.setCreationDate(new Date());
//            user1.setEmail("redscauser@gmail.com");
//            user1.setPassword("s3cret");
//            user1.setSurname("Artykov");
//            user1.setRoles(null);
//            em.persist(user1);
//
//            User user2 = new User();
//            user2.setName("Zhandos");
//            user2.setActive(true);
//            user2.setCreationDate(new Date());
//            user2.setEmail("zhandos.22.03.90@gmail.com");
//            user2.setPassword("s3cret");
//            user2.setSurname("Daulbaev");
//            user2.setRoles(null);
//            em.persist(user2);

            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
                if (em.isOpen()) {
                    em.close();
                }
                if (emf.isOpen()) {
                    emf.close();
                }
            }
        }
    }
}
