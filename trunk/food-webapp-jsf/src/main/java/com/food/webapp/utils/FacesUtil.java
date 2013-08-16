/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author daniyar.artykov
 */
public class FacesUtil {

    private static Logger log = LoggerFactory.getLogger(FacesUtil.class);

    public static synchronized void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
    }

    public static synchronized void addErrorMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
    }

    public static synchronized void addInfoMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
    }

    public static synchronized void addInfoMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
    }

    public static synchronized void addWarningMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
    }

    public static synchronized void addWarningMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
    }

    public static synchronized String getMessageFromBundle(String key, Object... arguments) {
        try {
            if (key != null && !key.isEmpty()) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                String messageBundleName = facesContext.getApplication().getMessageBundle();
                Locale locale = facesContext.getViewRoot().getLocale();
                ResourceBundle bundle = ResourceBundle.getBundle(messageBundleName, locale);
                String message = bundle.getString(key);
                return MessageFormat.format(message, arguments);
            }
        } catch (Exception e) {
            log.warn("Bundle message with key {} not found! Error: {}", key, e);
        }

        return key;
    }
}
