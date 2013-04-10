/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg.entity;

/**
 *
 * @author jakaniew
 */


public class EdgeImage extends EdgeComponent {
    private String id;
    private int x;
    private int y;
    private int width;
    private int height;
    private byte[] content;
    private EdgeImageFormat format;

    public EdgeImage() {
        this.format = EdgeImageFormat.png;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public EdgeImageFormat getFormat() {
        return format;
    }

    public void setFormat(EdgeImageFormat format) {
        this.format = format;
    }
    
    public String getFileName(){
        return id+"."+format;
    }
    
}
