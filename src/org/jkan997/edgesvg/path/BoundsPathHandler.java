/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg.path;

import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PathHandler;
import org.jkan997.edgesvg.Bounds;

/**
 *
 * @author jakaniew
 */
public class BoundsPathHandler implements PathHandler {
   
    private Bounds bounds;

    public BoundsPathHandler(Bounds bounds) {
        this.bounds = bounds;
    }
    
    
    
    /**
     * Invoked when the path starts.
     * @exception ParseException if an error occured while processing the path
     */
    public void startPath() throws ParseException{
        
    }

    /**
     * Invoked when the path ends.
     * @exception ParseException if an error occured while processing the path
     */
    public void endPath() throws ParseException{
        
    }

    /**
     * Invoked when a relative moveto command has been parsed.
     * <p>Command : <b>m</b>
     * @param x the relative x coordinate for the end point
     * @param y the relative y coordinate for the end point
     * @exception ParseException if an error occured while processing the path
     */
    public void movetoRel(float x, float y) throws ParseException{
    }

    /**
     * Invoked when an absolute moveto command has been parsed.
     * <p>Command : <b>M</b>
     * @param x the absolute x coordinate for the end point
     * @param y the absolute y coordinate for the end point
     * @exception ParseException if an error occured while processing the path
     */
    public void movetoAbs(float x, float y) throws ParseException{
                bounds.addPoint(x, y);
    }

    /**
     * Invoked when a closepath has been parsed.
     * <p>Command : <b>z</b> | <b>Z</b>
     * @exception ParseException if an error occured while processing the path
     */
    public void closePath() throws ParseException{
        
    }

    /**
     * Invoked when a relative line command has been parsed.
     * <p>Command : <b>l</b>
     * @param x the relative x coordinates for the end point
     * @param y the relative y coordinates for the end point
     * @exception ParseException if an error occured while processing the path
     */
    public void linetoRel(float x, float y) throws ParseException{

    }

    /**
     * Invoked when an absolute line command has been parsed.
     * <p>Command : <b>L</b>
     * @param x the absolute x coordinate for the end point
     * @param y the absolute y coordinate for the end point
     * @exception ParseException if an error occured while processing the path
     */
    public void linetoAbs(float x, float y) throws ParseException{
                bounds.addPoint(x, y);

    }

    /**
     * Invoked when an horizontal relative line command has been parsed.
     * <p>Command : <b>h</b>
     * @param x the relative X coordinate of the end point
     * @exception ParseException if an error occured while processing the path
     */
    public void linetoHorizontalRel(float x) throws ParseException{
        
    }

    /**
     * Invoked when an horizontal absolute line command has been parsed.
     * <p>Command : <b>H</b>
     * @param x the absolute X coordinate of the end point
     * @exception ParseException if an error occured while processing the path
     */
    public void linetoHorizontalAbs(float x) throws ParseException{
        
    }

    /**
     * Invoked when a vertical relative line command has been parsed.
     * <p>Command : <b>v</b>
     * @param y the relative Y coordinate of the end point
     * @exception ParseException if an error occured while processing the path
     */
    public void linetoVerticalRel(float y) throws ParseException{
        
    }

    /**
     * Invoked when a vertical absolute line command has been parsed.
     * <p>Command : <b>V</b>
     * @param y the absolute Y coordinate of the end point
     * @exception ParseException if an error occured while processing the path
     */
    public void linetoVerticalAbs(float y) throws ParseException{
        
    }

    /**
     * Invoked when a relative cubic bezier curve command has been parsed.
     * <p>Command : <b>c</b>
     * @param x1 the relative x coordinate for the first control point
     * @param y1 the relative y coordinate for the first control point
     * @param x2 the relative x coordinate for the second control point
     * @param y2 the relative y coordinate for the second control point
     * @param x the relative x coordinate for the end point
     * @param y the relative y coordinate for the end point
     * @exception ParseException if an error occured while processing the path
     */
    public void curvetoCubicRel(float x1, float y1, 
			 float x2, float y2, 
			 float x, float y) throws ParseException{
        
    }


    /**
     * Invoked when an absolute cubic bezier curve command has been parsed.
     * <p>Command : <b>C</b>
     * @param x1 the absolute x coordinate for the first control point
     * @param y1 the absolute y coordinate for the first control point
     * @param x2 the absolute x coordinate for the second control point
     * @param y2 the absolute y coordinate for the second control point
     * @param x the absolute x coordinate for the end point
     * @param y the absolute y coordinate for the end point
     * @exception ParseException if an error occured while processing the path
     */
    public void curvetoCubicAbs(float x1, float y1, 
			 float x2, float y2, 
			 float x, float y) throws ParseException{
        
    }

    /**
     * Invoked when a relative smooth cubic bezier curve command has
     * been parsed. The first control point is assumed to be the
     * reflection of the second control point on the previous command
     * relative to the current point.
     * <p>Command : <b>s</b>
     * @param x2 the relative x coordinate for the second control point
     * @param y2 the relative y coordinate for the second control point
     * @param x the relative x coordinate for the end point
     * @param y the relative y coordinate for the end point
     * @exception ParseException if an error occured while processing the path
     */
    public void curvetoCubicSmoothRel(float x2, float y2, 
			       float x, float y) throws ParseException{
        
    }

    /**
     * Invoked when an absolute smooth cubic bezier curve command has
     * been parsed. The first control point is assumed to be the
     * reflection of the second control point on the previous command
     * relative to the current point.
     * <p>Command : <b>S</b>
     * @param x2 the absolute x coordinate for the second control point
     * @param y2 the absolute y coordinate for the second control point
     * @param x the absolute x coordinate for the end point
     * @param y the absolute y coordinate for the end point
     * @exception ParseException if an error occured while processing the path
     */
    public void curvetoCubicSmoothAbs(float x2, float y2, 
			       float x, float y) throws ParseException{
        
    }

    /**
     * Invoked when a relative quadratic bezier curve command has been parsed.
     * <p>Command : <b>q</b>
     * @param x1 the relative x coordinate for the control point
     * @param y1 the relative y coordinate for the control point
     * @param x the relative x coordinate for the end point
     * @param y the relative x coordinate for the end point
     * @exception ParseException if an error occured while processing the path
     */
    public void curvetoQuadraticRel(float x1, float y1, 
			     float x, float y) throws ParseException{
        
    }

    /**
     * Invoked when an absolute quadratic bezier curve command has been parsed.
     * <p>Command : <b>Q</b>
     * @param x1 the absolute x coordinate for the control point
     * @param y1 the absolute y coordinate for the control point
     * @param x the absolute x coordinate for the end point
     * @param y the absolute x coordinate for the end point
     * @exception ParseException if an error occured while processing the path
     */
    public void curvetoQuadraticAbs(float x1, float y1, 
			     float x, float y) throws ParseException{
        
    }

    /**
     * Invoked when a relative smooth quadratic bezier curve command
     * has been parsed. The control point is assumed to be the
     * reflection of the control point on the previous command
     * relative to the current point.
     * <p>Command : <b>t</b>
     * @param x the relative x coordinate for the end point 
     * @param y the relative y coordinate for the end point 
     * @exception ParseException if an error occured while processing the path
     */
    public void curvetoQuadraticSmoothRel(float x, float y) throws ParseException{
        
    }

    /**
     * Invoked when an absolute smooth quadratic bezier curve command
     * has been parsed. The control point is assumed to be the
     * reflection of the control point on the previous command
     * relative to the current point.
     * <p>Command : <b>T</b>
     * @param x the absolute x coordinate for the end point 
     * @param y the absolute y coordinate for the end point 
     * @exception ParseException if an error occured while processing the path
     */
    public void curvetoQuadraticSmoothAbs(float x, float y) throws ParseException{
        
    }

    /**
     * Invoked when a relative elliptical arc command has been parsed. 
     * <p>Command : <b>a</b>
     * @param rx the X axis radius for the ellipse
     * @param ry the Y axis radius for the ellipse 
     * @param xAxisRotation the rotation angle in degrees for the ellipse's
     *                      X-axis relative to the X-axis
     * @param largeArcFlag the value of the large-arc-flag 
     * @param sweepFlag the value of the sweep-flag 
     * @param x the relative x coordinate for the end point 
     * @param y the relative y coordinate for the end point 
     * @exception ParseException if an error occured while processing the path
     */
    public void arcRel(float rx, float ry, 
		float xAxisRotation, 
		boolean largeArcFlag, boolean sweepFlag, 
		float x, float y) throws ParseException{
        
    }


    /**
     * Invoked when an absolute elliptical arc command has been parsed.
     * <p>Command : <b>A</b>
     * @param rx the X axis radius for the ellipse
     * @param ry the Y axis radius for the ellipse 
     * @param xAxisRotation the rotation angle in degrees for the ellipse's
     *                      X-axis relative to the X-axis
     * @param largeArcFlag the value of the large-arc-flag 
     * @param sweepFlag the value of the sweep-flag 
     * @param x the absolute x coordinate for the end point 
     * @param y the absolute y coordinate for the end point 
     * @exception ParseException if an error occured while processing the path
     */
    public void arcAbs(float rx, float ry, 
		float xAxisRotation, 
		boolean largeArcFlag, boolean sweepFlag, 
		float x, float y) throws ParseException{
        
    }
}
