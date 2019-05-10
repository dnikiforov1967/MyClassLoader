/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.myclassloader.classloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author dnikiforov
 */
public class CustomClassLoader extends ClassLoader {

    public CustomClassLoader(ClassLoader cl) {
        super(cl);
    }

    public CustomClassLoader() {
        super();
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
        if (!name.startsWith("org.example")) {
            throw new ClassNotFoundException(name);
        }
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
        return cl;
    }

}
