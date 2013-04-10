/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg.gui;

/**
 *
 * @author jakaniew
 */
public class Test {
    public static void main(String[] args) {
        String country = "022IS-A_$";
        country = country.replaceAll("[^0-9a-zA-Z_-]","");
        System.out.println(country);
    }
}
