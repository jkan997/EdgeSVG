/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg.gui;

import org.jkan997.edgesvg.EdgeSvgConverter;

/**
 *
 * @author jakaniew
 */
public class GenerationThread extends Thread {

    public ProgressBar progressBar;
    private EdgeSvgConverter esc;

    public GenerationThread() {
        esc = new EdgeSvgConverter();
    }

    public EdgeSvgConverter getEsc() {
        return esc;
    }

    public void run() {
        String msg = "Error occured";
        try {
            esc.loadSVG();
            esc.process();
            esc.printStruct();
            esc.saveSymbol();
            msg = "Generation successful";
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            finished(msg);
        }
    }

    public void finished(String msg) {
    }
}
