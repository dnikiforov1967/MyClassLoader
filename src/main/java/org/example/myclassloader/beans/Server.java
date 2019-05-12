/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.myclassloader.beans;

import java.util.HashSet;
import java.util.Set;
import org.example.myclassloader.annotations.Proto;
import org.example.myclassloader.annotations.Registry;
import org.example.myclassloader.classloader.CustomClassLoader;

/**
 *
 * @author dima
 */
public class Server {
    
    private static final Set<Class<?>> protoSet = new HashSet<>();
    
    public void run(String... args) {
        System.out.println("I start the server run");
        A a = new A();
        B b = new B();
        C c = new C();
        Registry.getProtoClasses().forEach(System.out::println);
    }
    
}
