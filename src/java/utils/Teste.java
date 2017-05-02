/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import facades.BandeiraFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author valderlei
 */
public class Teste implements Cloneable{
    
    private int x1;
    private int x2;
    private int x3;
    private int x4;
    private int x5;
   
    public Teste(){
        
    }

    public Teste(int x1, int x2, int x3, int x4, int x5) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
        this.x5 = x5;
    }
       
    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getX3() {
        return x3;
    }

    public void setX3(int x3) {
        this.x3 = x3;
    }

    public int getX4() {
        return x4;
    }

    public void setX4(int x4) {
        this.x4 = x4;
    }

    public int getX5() {
        return x5;
    }

    public void setX5(int x5) {
        this.x5 = x5;
    }
    
    @Override
    public Teste clone() throws CloneNotSupportedException{
        return (Teste)super.clone();
    }
    
    public static void main(String[] args) {
       
            System.out.println(ChaveMD5.criptografar("1234"));
        
    }
    
}
