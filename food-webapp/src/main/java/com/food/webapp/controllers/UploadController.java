/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.controllers;

import com.food.model.user.User;
import com.food.webapp.additional.UploadFile;
import com.food.webapp.services.UploadService;
import com.food.webapp.services.UserService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author daniyar.artykov
 */
@Controller
public class UploadController {

    private static final Logger logger =
            LoggerFactory.getLogger(UploadController.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private UploadService uploadService;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            // throw new NotFndException("");
        }
        Object obj = auth.getPrincipal();
        String username;
        if (obj instanceof UserDetails) {
            username = ((UserDetails) obj).getUsername();
        } else {
            username = obj.toString();
        }
        User u = userService.getUserByEmail(username);

        return u;
    }

    @RequestMapping(value = "/client/upload", method = RequestMethod.GET)
    public String getUploadForm(Model model) {
        model.addAttribute(new UploadFile());
        return "client/upload";
    }

    @RequestMapping(value = "/client/upload", method = RequestMethod.POST)
    public ModelAndView create(UploadFile uploadFile, BindingResult result,
            HttpServletRequest request) {
        logger.info(">>> Upoladed file name = {}; type = {}",
                uploadFile.getFile().getName(), uploadFile.getFile().getContentType());
        String url = request.getRealPath("xml/" + getCurrentUser().getEmail());
        logger.info(">>> realpath = {}", url);
        BufferedReader reader;
        BufferedWriter writer;
        try {
            File dir = new File(url);
            if (dir.exists() == false) {
                dir.mkdirs();
            }
            File file = new File(url + "/" + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()) + "." + uploadFile.getFile().getContentType());
            if (file.exists() == false) {
                file.createNewFile();
            }
            reader = new BufferedReader(new InputStreamReader(uploadFile.getFile().getInputStream(), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            String thisLine;

            while ((thisLine = reader.readLine()) != null) {
                writer.write(thisLine);
                writer.newLine();
            }

            writer.close();
            reader.close();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return new ModelAndView("redirect:/client/upload");
    }
}
