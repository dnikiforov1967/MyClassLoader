/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifactory.myclassloader.beans;

import com.ifactory.myclassloader.annotations.Proto;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author dnikiforov
 */
public class BeanRegister {
    private static final Set<Class<?>> protoSet = new HashSet<>();
    public static void addClass(Class<?> cl) {
        if (cl.isAnnotationPresent(Proto.class))
        protoSet.add(cl);
    }
    public static Set<Class<?>> getProtoSet() {
        return protoSet;
    }
}
