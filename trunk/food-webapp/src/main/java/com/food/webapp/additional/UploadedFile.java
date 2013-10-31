/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.additional;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author daniyar.artykov
 */
public class UploadedFile {

    private CommonsMultipartFile file;

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }
}
