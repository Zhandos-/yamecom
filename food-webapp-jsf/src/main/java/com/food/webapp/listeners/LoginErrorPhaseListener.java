/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.listeners;

import com.food.webapp.utils.FacesUtil;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

/**
 *
 * @author daniyar.artykov
 */
public class LoginErrorPhaseListener implements PhaseListener {

    private static final long serialVersionUID = -3660450949731003503L;

    @Override
    public void beforePhase(final PhaseEvent arg0) {
        Exception e = (Exception) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);

        if (e instanceof BadCredentialsException) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
                    WebAttributes.AUTHENTICATION_EXCEPTION, null);
            FacesUtil.addErrorMessage("Ошибка: Не правильный логин или пароль!");
        }
    }

    @Override
    public void afterPhase(final PhaseEvent arg0) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
