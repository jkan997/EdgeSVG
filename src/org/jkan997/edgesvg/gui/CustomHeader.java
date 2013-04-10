package org.jkan997.edgesvg.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 *
 * @author jakaniew
 */
public class CustomHeader extends JComponent{
      private BufferedImage headerImg = null;
    
      private String label = "Settings";
      
    public CustomHeader(){
        try{
            InputStream is = getClass().getResourceAsStream("/org/jkan997/edgesvg/gui/assets/header.png");
            headerImg = ImageIO.read(is);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics ig2){
      Font font = new Font("Lucida Grande", Font.BOLD, 10);
      ig2.setFont(font);
      ig2.setColor(new Color(0xd4d4d4));
      String message = label;
      FontMetrics fontMetrics = ig2.getFontMetrics();
      int stringWidth = fontMetrics.stringWidth(message);
      int stringHeight = fontMetrics.getAscent();
      ig2.drawImage(headerImg, 0, 0, null);
      ig2.drawString(message, 23,stringHeight+2);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    
}
