/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.beans;

import com.food.dao.UserDAO;
import com.food.model.user.User;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author daniyar.artykov
 */
@Component("loginBean")
@Scope("session")
public class LoginBean implements Serializable {

    private static final long serialVersionUID = -8808619668024400140L;
    @Autowired(required = true)
    private UserDAO userDao;
    private String initUserName;
    private User initUser;
    private Set<String> roles = new HashSet<String>();
    private static final Logger logger = LoggerFactory.getLogger(LoginBean.class.getName());
    private Locale locale = null;

    public synchronized String login() throws IOException, ServletException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");
        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();

        return null;
    }

    public synchronized String logout() {

        return null;
    }

    public synchronized String getCurrentUserName() {
        String username = null;
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return username;
    }

    public synchronized User getCurrentUser() {
        init();
        return initUser;
    }

    public synchronized boolean isLoggedIn() {
        return getCurrentUserName() != null;
    }

    public synchronized Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public synchronized boolean hasRole(String role) {
        init();
        return roles.contains(role);
    }

    public String changeLocale() {
        return null;
    }

    public void setLocale(Locale sessionLocale, HttpServletRequest request, HttpServletResponse response) {
        logger.info("Change locale to: {}", sessionLocale.getLanguage());
        Cookie localeCookie = new Cookie("com.food.webapp.locale", sessionLocale.getLanguage());
        localeCookie.setPath(request.getContextPath());
        localeCookie.setMaxAge(31104000);
        response.addCookie(localeCookie);
        setLocale(sessionLocale);
    }

    public void setLocale(Locale sessionLocale) {
        this.locale = sessionLocale;
    }

    public Locale getLocale() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        //Считываем из параметра запроса.
        String languageCode = ((HttpServletRequest) ec.getRequest()).getParameter("languageCode");
        if (languageCode != null && languageCode.length() > 0) {
            setLocale(new Locale(languageCode), (HttpServletRequest) ec.getRequest(), (HttpServletResponse) ec.getResponse());
            return locale;
        }
        if (locale != null) {
            return locale;
        }
        //Считываем из параметра куки.
        Map<String, Object> cookies = ec.getRequestCookieMap();
        Object localeCookie = cookies.get("com.food.webapp.locale");
        if (localeCookie != null && ((Cookie) localeCookie).getValue().length() != 0) {
            setLocale(new Locale(((Cookie) localeCookie).getValue()));
            return locale;
        }
        //Считываем из параметра браузера.
        setLocale(fc.getApplication().getViewHandler().calculateLocale(fc), (HttpServletRequest) ec.getRequest(), (HttpServletResponse) ec.getResponse());
        return locale;
    }

    public synchronized boolean hasAnyRole(String roles[]) {
        for (String role : roles) {
            if (hasRole(role)) {
                return true;
            }
        }
        return false;
    }

    private synchronized void init() {
        if (initUserName == null || !initUserName.equals(getCurrentUserName())) {
            this.initUser = userDao.getUserByEmail(getCurrentUserName());
            if (!roles.isEmpty()) {
                roles.clear();
            }

            Authentication auth = getAuthentication();
            if (auth != null) {
                for (GrantedAuthority ga : auth.getAuthorities()) {
                    if (!roles.contains(ga.getAuthority())) {
                        roles.add(ga.getAuthority());
                    }
                }
            }

            this.initUserName = getCurrentUserName();
            logger.info("{} roles: {}", new Object[]{initUserName, Arrays.toString(roles.toArray())});
        }
    }
}
