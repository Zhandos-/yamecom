/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.test.services;

import java.io.File;
import org.junit.Ignore;

/**
 *
 * @author daniyar.artykov
 */
public class Test {

    @org.junit.Test
    @Ignore
    public void test() {
        String absolutePath = new File("").getAbsolutePath();
        System.out.println("absolutePath = " + absolutePath);
        File f = new File(absolutePath + "/food/images/");
        boolean flag = f.mkdirs();
        System.out.println("flag = " + flag);
    }

}
