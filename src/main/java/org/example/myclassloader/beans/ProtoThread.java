/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.myclassloader.beans;

/**
 *
 * @author dnikiforov
 */
public class ProtoThread extends Thread {

    @Override
    public void run() {
        BeanY beanY = new BeanY();
    }
    
}
