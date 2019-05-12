/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.myclassloader.classloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dnikiforov
 */
public class CustomClassLoader extends ClassLoader {

    private Class<? extends Annotation> protoAnnotation;
    private Class<?> registry;

    public CustomClassLoader(ClassLoader cl) {
        super(cl);
    }

    public CustomClassLoader() {
        super();
        protoAnnotation = null;
    }

    public void postConstruct() {
        try {
            protoAnnotation = (Class<? extends Annotation>) super.loadClass("org.example.myclassloader.annotations.Proto", true);
            registry = super.loadClass("org.example.myclassloader.annotations.Registry", true);
            System.out.println("Class loader of portoClass is " + protoAnnotation.getClassLoader().getClass());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private byte[] getClassData(String name) throws IOException {
        final String way = name.replace(".", "/") + ".class";
        try (final InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(way);) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            byte[] array = new byte[1024];
            int read = 0;
            while ((read = stream.read(array)) != -1) {
                baos.write(array, 0, read);
            }
            array = baos.toByteArray();
            return array;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> cl = null;
        final String way = name.replace(".", "/") + ".class";
        try (final InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(way);) {
            byte[] array = getClassData(name);
            cl = defineClass(name, array, 0, array.length);
        } catch (Throwable ex) {
            throw new ClassNotFoundException(name, ex);
        }
        return cl;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> cl = super.loadClass(name, resolve);
        if (protoAnnotation != null && cl.isAnnotationPresent(protoAnnotation)) {
            try {
                final Method method = registry.getMethod("addClass", Class.class);
                method.invoke(null, cl);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return cl;
    }

}
