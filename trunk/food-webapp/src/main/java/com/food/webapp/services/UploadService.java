/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services;

import com.food.model.user.User;
import com.food.webapp.additional.UploadFile;

/**
 *
 * @author daniyar.artykov
 */
public interface UploadService {

    public String upload(UploadFile uploadFile, User user);
}
