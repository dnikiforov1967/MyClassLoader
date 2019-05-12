/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.myclassloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.example.myclassloader.classloader.CustomClassLoader;

/**
 *
 * @author dnikiforov
 */
public class App {
    
    public static void main(String... args) throws InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        ClassLoader extentionClassLoader = ClassLoader.getSystemClassLoader();
        while(extentionClassLoader.getParent()!=null) {
            extentionClassLoader = extentionClassLoader.getParent();
        }

        CustomClassLoader cloader = new CustomClassLoader(extentionClassLoader);
        cloader.postConstruct();
        final Class<?> serverClass = cloader.loadClass("org.example.myclassloader.beans.Server");
        System.out.println("Server Class class loader : "+serverClass.getClassLoader().getClass());
        final Object serverObject = serverClass.newInstance();
        final Method runMethod = serverClass.getMethod("run", String[].class);
        runMethod.invoke(serverObject, (Object)args);
        
    }
    
}
