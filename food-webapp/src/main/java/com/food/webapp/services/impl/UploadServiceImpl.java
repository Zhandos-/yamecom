/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.services.impl;

import com.food.webapp.services.UploadService;
import com.food.model.user.User;
import com.food.webapp.additional.UploadedFile;
import java.io.Serializable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daniyar.artykov
 */
@Service("uploadService")
@Transactional("postgresT")
public class UploadServiceImpl implements UploadService, Serializable {

    private static final long serialVersionUID = 2427861515294753883L;

    @Override
    public String upload(UploadedFile uploadFile, User user) {
        return null;
    }
}
