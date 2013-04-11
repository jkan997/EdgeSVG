/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JComponent;

/**
 *
 * @author jakaniew
 */
public class ProgressBar extends JComponent {

    private Timer timer = null;
    private int percent = 0;
    private String label = null;

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        if (percent != this.percent) {
            this.percent = percent;
            this.label = "" + percent + "%";
            this.repaint();
        }
    }

    public void setLabel(String label) {
        this.label = label;
        this.repaint();
    }

    public int incPercent() {
        int p = this.getPercent() + 1;
        this.setPercent(p);
        return this.percent;
    }

    public void startBar(int sec) {
        timer = new Timer();
        int length = sec * 1000;
        int delay = length / 100;
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                int percent = ProgressBar.this.incPercent();
                if (percent >= 100) {
                    ProgressBar.this.setPercent(0);
                }
            }
        };
        timer.scheduleAtFixedRate(tt, 0, delay);
    }

    public void stopBar() {
        timer.cancel();
    }

    @Override
    public void paint(Graphics g) {
        int w = this.getWidth();
        int h = this.getHeight();
        Color c = new Color(0x5e, 0x5e, 0x5e);
        Color c2 = new Color(0xE7, 0xA8, 0x45);
        g.setColor(c2);
        int nw = (w - 6) * percent / 100;
        g.fillRoundRect(4, 4, nw, h - 8, 3, 3);
        g.setColor(c);
        g.drawRoundRect(2, 2, w - 4, h - 4, 3, 3);
        if ((label != null) && (label.length() > 0)) {
            Font font = new Font("Lucida Grande", Font.BOLD, 11);
            g.setFont(font);
            g.setColor(new Color(0xd4d4d4));
            String message = label;
            FontMetrics fontMetrics = g.getFontMetrics();
            int stringWidth = fontMetrics.stringWidth(message);
            int stringHeight = fontMetrics.getAscent();
            int px = (this.getWidth() / 2) - (stringWidth / 2);
            int py = (this.getHeight() / 2) + (stringHeight / 2);
            g.drawString(message, px, py);
        }
    }
}
