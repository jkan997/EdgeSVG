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
public class LogoButton extends CustomButton {
        public LogoButton(){
        this.btnImgName = "svg_edge.png";
        this.label=null;
        initBtnImg();
        Dimension dim = new Dimension(50, 400);
        this.setSize(dim);
        this.showHover=false;
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);
        MouseListener ml = new MoveMouseListener();
        this.addMouseListener(ml);
    }
}
