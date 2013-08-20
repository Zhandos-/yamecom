/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.listeners;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author daniyar.artykov
 */
public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {

    private static final Logger log = LoggerFactory.getLogger(ContextLoaderListener.class);

    @Override
    public void contextDestroyed(javax.servlet.ServletContextEvent event) {
        if (getContextLoader() != null) {
            getContextLoader().closeWebApplicationContext(event.getServletContext());
        }
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                log.info(String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                log.error(String.format("Error deregistering driver %s", driver), e);
            }
        }
    }
}