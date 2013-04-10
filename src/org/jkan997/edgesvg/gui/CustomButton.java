/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 *
 * @author jakaniew
 */
public class CustomButton extends JComponent {

    protected boolean mouseOver;
    protected boolean selected = false;
    protected String label = "Settings";
    protected BufferedImage btnImg = null;
    protected BufferedImage btnSelectedImg = null;
    protected String btnImgName = "bigbtn.png";
    protected String btnSelectedImgName = null;
    protected boolean showHover = true;
    protected int margRight = -12;
    protected int margTop = 0;
    protected void initBtnImg() {
        try {
            InputStream is = getClass().getResourceAsStream("/org/jkan997/edgesvg/gui/assets/" + btnImgName);
            btnImg = ImageIO.read(is);

            if (btnSelectedImgName != null) {
                is = getClass().getResourceAsStream("/org/jkan997/edgesvg/gui/assets/" + btnSelectedImgName);
                btnSelectedImg = ImageIO.read(is);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public CustomButton() {
        initBtnImg();
        Dimension dim = new Dimension(200, 35);
        this.setSize(dim);
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);
        MouseListener ml = new MoveMouseListener();
        this.addMouseListener(ml);
    }

    @Override
    public void paint(Graphics ig2) {
        Font font = new Font("Lucida Grande", Font.BOLD, 10);
        ig2.setFont(font);
        ig2.setColor(new Color(0xd4d4d4));
        BufferedImage img = btnImg;
        if ((selected)&&(btnSelectedImg!=null)){
            img=btnSelectedImg;
        }
        ig2.drawImage(img, 0, margTop, null);
        if ((mouseOver) && (showHover)) {
            ig2.setColor(new Color(255, 255, 255, 100));
            ig2.fillRect(4, 4+margTop, btnImg.getWidth() + margRight, btnImg.getHeight() - 8 + margTop);
        }
        if ((label != null) && (label.length() > 0)) {
            String message = label;
            FontMetrics fontMetrics = ig2.getFontMetrics();
            int stringWidth = fontMetrics.stringWidth(message);
            int stringHeight = fontMetrics.getAscent();
            int px = (this.getWidth() / 2) - (stringWidth / 2);
            int py = (this.getHeight() / 2) + (stringHeight / 2);
            ig2.drawString(message, px, py);
        }
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {

        this.mouseOver = mouseOver;
        System.out.println("MOUSE " + (mouseOver ? "over" : "out"));
        this.repaint();
    }

    public boolean isSelected() {
        return selected;
    }
    
    public void setSelectedInternal(boolean selected) {
        this.selected = selected;
        repaint();
    }

    public void setSelected(boolean selected) {
        setSelectedInternal(selected);
    }
    
    public boolean switchSelected(){
        boolean selected = isSelected();
        selected = !selected;
        setSelected(selected);
        return isSelected();
    }
    
}
