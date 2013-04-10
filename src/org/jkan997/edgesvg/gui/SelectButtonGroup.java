/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg.gui;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jakaniew
 */
public class SelectButtonGroup {
    private Set<SelectButton> buttons = new HashSet<SelectButton>();
    
    
   public void addSelectButton(SelectButton btn){
       buttons.add(btn);
       btn.setSelectButtonGroup(this);
   }

    void unselectExcept(SelectButton btn) {
        for (SelectButton b : buttons){
            if (b!=btn){
                b.setSelectedInternal(false);
            }
        }
    }
}
