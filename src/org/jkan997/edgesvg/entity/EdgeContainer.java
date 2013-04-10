/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg.entity;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jakaniew
 */
public class EdgeContainer extends EdgeComponent{
    private String id;
    public List<EdgeComponent> components = new LinkedList<EdgeComponent>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<EdgeComponent> getComponents() {
        return components;
    }
    
    public void addComponent(EdgeComponent comp){
        components.add(comp);
    } 
}
