/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifactory.myclassloader;

import com.ifactory.myclassloader.beans.BeanX;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author dnikiforov
 */
public class App {
    
    public static void main(String... args) throws ClassNotFoundException, InterruptedException, FileNotFoundException, IOException {
        final ClassLoader defaultClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("System class loader : "+defaultClassLoader);
        System.out.println("Parent class loader : "+defaultClassLoader.getParent());
        System.out.println("Thread class loader : "+Thread.currentThread().getContextClassLoader());
        System.out.println("App class loader : "+App.class.getClassLoader());

        BeanX beanX = new BeanX();
        //final Class<?> beanXClass = defaultClassLoader.loadClass("com.ifactory.myclassloader.beans.BeanX");
        System.out.println("BeanX Class class loader : "+beanX.getClass().getClassLoader());
        
    }
    
}
