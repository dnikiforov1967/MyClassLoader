/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifactory.myclassloader.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author dnikiforov
 */
public class CustomClassLoader extends ClassLoader {

    private static final Map<String, Class<?>> map = new ConcurrentHashMap<>();

    public CustomClassLoader(ClassLoader cl) {
        super(cl);
    }

    public CustomClassLoader() {
        super();
    }

    private Class<?> lookupClass(String name) {
        return map.get(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> cl = null;
        if (!name.startsWith("com.ifactory")) {
            return cl;
        }
        final String way = name.replace(".", "/")+".class";
        try(final InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(way);) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            byte[] array = new byte[1024];
            int read = 0;
            while ((read = stream.read(array)) != -1) {
                baos.write(array, 0, read);
            }
            array = baos.toByteArray();
            
            cl = defineClass(name, array, 0, array.length);
            map.put(name, cl);
            
        } catch (FileNotFoundException ex) {
            cl = super.findClass(name);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
        return cl;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> lookupClass = lookupClass(name);
        if (lookupClass == null) {
            lookupClass = findClass(name);
            if (lookupClass!=null && resolve) {
                resolveClass(lookupClass);
            }
        }
        if (lookupClass == null) {
            lookupClass = super.loadClass(name, resolve);
        }
        return lookupClass;
    }
}
