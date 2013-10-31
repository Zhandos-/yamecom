/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services;

import com.food.model.user.User;
import com.food.webapp.additional.UploadedFile;

/**
 *
 * @author daniyar.artykov
 */
public interface UploadService {

    public String upload(UploadedFile uploadFile, User user);
}
