/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.myclassloader.annotations;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author dima
 */
public final class Registry {
    
    private Registry() {
        
    }

    private static Set<Class<?>> protoClasses = new HashSet<>();
    
    public static void addClass(Class<?> clazz) {
        protoClasses.add(clazz);
    }
    
    public static Set<Class<?>> getProtoClasses() {
        return protoClasses;
    }    
    
}
