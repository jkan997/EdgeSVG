/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg.gui;

import java.awt.event.MouseEvent;

/**
 *
 * @author jakaniew
 */
public class MoveMouseListener extends AbstractMouseListener {

    @Override
    public void mouseExited(MouseEvent e) {
        CustomButton btn = (CustomButton)e.getComponent();
        btn.setMouseOver(false);
    }
    
     @Override
    public void mouseEntered(MouseEvent e) {
         CustomButton btn = (CustomButton)e.getComponent();
        btn.setMouseOver(true);
    }
    
}
