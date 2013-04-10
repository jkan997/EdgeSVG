/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg.path;

import org.apache.batik.parser.PathParser;
import org.jkan997.edgesvg.Bounds;

/**
 *
 * @author jakaniew
 */
public class PathBounds {
    public static Bounds getBounds(String pathD){
        Bounds res = new Bounds();
        PathParser pp = new PathParser();
        BoundsPathHandler ph = new BoundsPathHandler(res);
        pp.setPathHandler(ph);
        pp.parse(pathD);
        return res;
    }
}
