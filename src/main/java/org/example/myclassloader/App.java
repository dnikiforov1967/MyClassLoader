/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.myclassloader;

import org.example.myclassloader.beans.BeanX;
import org.example.myclassloader.beans.BeanY;
import org.example.myclassloader.beans.ProtoThread;

/**
 *
 * @author dnikiforov
 */
public class App {
    
    public static void main(String... args) throws InterruptedException {
        final ClassLoader defaultClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("System classloader : "+defaultClassLoader.getClass());
        System.out.println("Parent classloader : "+defaultClassLoader.getParent().getClass());
        System.out.println("Thread classloader : "+Thread.currentThread().getContextClassLoader().getClass());
        System.out.println("This class classloader : "+App.class.getClassLoader().getClass());

        BeanX beanX = new BeanX();
        System.out.println("BeanX Class class loader : "+beanX.getClass().getClassLoader().getClass());
        ProtoThread pt = new ProtoThread();
        pt.start();
        pt.join();
        
    }
    
}
