/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg.gui;

import java.awt.Dimension;
import java.awt.event.MouseListener;

/**
 *
 * @author jakaniew
 */
public class FileButton extends CustomButton {
    public FileButton(){
        this.btnImgName = "filebtn.png";
        this.label=null;
        initBtnImg();
        Dimension dim = new Dimension(25, 25);
        this.setSize(dim);
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);
        MouseListener ml = new MoveMouseListener();
        this.addMouseListener(ml);
    }
}
