/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg.gui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author jakaniew
 */
public class SelectButton extends CustomButton {

    private SelectButtonGroup selectButtonGroup = null;

    public SelectButton() {
        label = null;
        margRight = -8;
        margTop = 4;
        btnImgName = "select.png";
        btnSelectedImgName = "select_selected.png";
        initBtnImg();
        Dimension dim = new Dimension(20, 20);
        this.setSize(dim);
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);
        MouseListener ml = new MoveMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SelectButton.this.switchSelected();
            }
        };
        this.addMouseListener(ml);
    }

    @Override
    public void setSelected(boolean selected) {
        if (this.selected != selected) {
            if (selectButtonGroup == null) {
                super.setSelected(selected);
            } else {
                if (selected == true) {
                    System.out.println("SELECTED!");
                    selectButtonGroup.unselectExcept(this);
                    super.setSelected(true);
                }
            }
        }
    }

    public SelectButtonGroup getSelectButtonGroup() {
        return selectButtonGroup;
    }

    public void setSelectButtonGroup(SelectButtonGroup selectButtonGroup) {
        this.selectButtonGroup = selectButtonGroup;
    }
}
