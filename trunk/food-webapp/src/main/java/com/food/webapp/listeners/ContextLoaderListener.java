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
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

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
        deregisterDrivers();
        clearThreads();
    }

    public void deregisterDrivers() {
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

    public void clearThreads() {
        int count = 0;
        try {
            final Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
            threadLocalsField.setAccessible(true);
            final Field inheritableThreadLocalsField = Thread.class.getDeclaredField("inheritableThreadLocals");
            inheritableThreadLocalsField.setAccessible(true);
            for (final Thread thread : Thread.getAllStackTraces().keySet()) {
                count += clear(threadLocalsField.get(thread));
                count += clear(inheritableThreadLocalsField.get(thread));
            }
            log.info("Stopped threads count = {}", count);
        } catch (Exception e) {
            log.error("When try to stop threads get follow error: {}", e.getMessage());
        }
    }

    private int clear(final Object threadLocalMap) throws Exception {
        if (threadLocalMap == null) {
            return 0;
        }
        int count = 0;
        final Field tableField = threadLocalMap.getClass().getDeclaredField("table");
        tableField.setAccessible(true);
        final Object table = tableField.get(threadLocalMap);
        for (int i = 0, length = Array.getLength(table); i < length; ++i) {
            final Object entry = Array.get(table, i);
            if (entry != null) {
                final Object threadLocal = ((WeakReference) entry).get();
                if (threadLocal != null) {
                    log(i, threadLocal);
                    Array.set(table, i, null);
                    ++count;
                }
            }
        }
        return count;
    }

    private void log(int i, final Object threadLocal) {
        if (threadLocal.getClass() != null
                && threadLocal.getClass().getEnclosingClass() != null
                && threadLocal.getClass().getEnclosingClass().getName() != null) {
            log.info("threadLocalMap({}): {}", i, threadLocal.getClass().getEnclosingClass().getName());
        } else if (threadLocal.getClass() != null
                && threadLocal.getClass().getName() != null) {
            log.info("threadLocalMap({}): {}", i, threadLocal.getClass().getName());
        } else {
            log.info("threadLocalMap({}): cannot identify threadlocal class name", i);
        }
    }
}