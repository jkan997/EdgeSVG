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
    
    
    public void run() {
        try{
               EdgeSvgConverter esc = new EdgeSvgConverter();
        esc.loadSVG();
        esc.process();
        esc.printStruct();
        esc.saveSymbol();
        finished();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void finished(){
        
    }
}
