/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg;

import org.jkan997.edgesvg.entity.EdgeImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.jkan997.edgesvg.entity.EdgeComponent;
import org.jkan997.edgesvg.entity.EdgeContainer;
import org.jkan997.edgesvg.entity.TimelineEvent;
import org.jkan997.edgesvg.entity.TimelinePosition;
import org.jkan997.edgesvg.helper.IOHelper;

/**
 *
 * @author jakaniew
 */
public class EdgeTemplateGenerator {

    private EdgeContainer rootContainer;
    private List<TimelinePosition> timeline = new LinkedList<TimelinePosition>();
    List<EdgeImage> edgeImages = new LinkedList<EdgeImage>();
    private Map<String, Boolean> components = new HashMap<String, Boolean>();
    private String srcDir = "/Volumes/MacData/jakaniew/svn/Adobe/EdgeSVG/edge_template";
    private String destFile = null;
    private String st = "symbol_template";
    private String symbolName = "us_states";
    private ZipOutputStream zos = null;
    private int globalWidth = 600;
    private int globalHeight = 600;

    public void addAllFiles(File[] files) throws IOException {
        for (File f : files) {
            addFile(f);
        }
    }

    private String processLine(String line) {
        line = line.replaceAll(st, symbolName);
        return line;
    }

    public EdgeContainer getRootContainer() {
        return rootContainer;
    }

    public void setRootContainer(EdgeContainer rootContainer) {
        this.rootContainer = rootContainer;
    }

    public List<TimelinePosition> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<TimelinePosition> timeline) {
        this.timeline = timeline;
    }

    public Map<String, Boolean> getComponents() {
        return components;
    }

    public void setComponents(Map<String, Boolean> components) {
        this.components = components;
    }

    private void append(StringBuilder sb, String pattern, Object... params) {
        sb.append(String.format(pattern, params));
        sb.append("\n");
    }

    private void addImage(StringBuilder sb, EdgeImage ei) {
        append(sb, "{");
        append(sb, "id: '%s',", ei.getId());
        append(sb, "type: 'image',");
        append(sb, "rect: ['%dpx','%dpx','%dpx','%dpx','auto','auto'],", ei.getX(), ei.getY(), ei.getWidth(), ei.getHeight());
        append(sb, "fill: ['rgba(0,0,0,0)','images/%s','0px','0px']", ei.getFileName());
        append(sb, "}");
        edgeImages.add(ei);


    }

    private void addContainer(StringBuilder sb, EdgeContainer container) {
        boolean first = true;
        append(sb, "{");
        append(sb, "id:'%s',", container.getId());
        append(sb, "type:'group',");
        append(sb, "rect:['0','0','%d','%d','auto','auto'],", globalWidth, globalHeight);
        append(sb, "c:[");
        for (EdgeComponent ec : container.getComponents()) {
            if (first) {
                first = false;
            } else {
                sb.append(",\n");
            }
            addComponent(sb, ec);
        }
        append(sb, "]}");
    }

    private void addComponent(StringBuilder sb, EdgeComponent ec) {
        if (ec instanceof EdgeContainer) {
            addContainer(sb, (EdgeContainer) ec);
            return;
        }
        if (ec instanceof EdgeImage) {
            addImage(sb, (EdgeImage) ec);
            return;
        }
    }

    private static String formatTime(String time) {
        String res = null;
        if (time.length() == 8) {
            res = time.substring(0, 4);
            res += "-";
            res += time.substring(4, 6);
            res += "-";
            res += time.substring(6, 8);
            return res;
        }

        if (time.length() == 6) {
            res = time.substring(0, 4);
            res += "-";
            res += time.substring(4, 6);
            return res;
        }
        return res;
    }

    private List<TimelineEvent> getTimelineEvents() {
        List<TimelineEvent> res = new LinkedList<TimelineEvent>();
        Object[] timelineArr = timeline.toArray();
        for (int i = 0; i<timelineArr.length-1;i++){
       
            TimelinePosition tp = (TimelinePosition)timelineArr[i];
            for (TimelineEvent te : tp.getEvents()) {
                System.out.println(te);
                res.add(te);
            }
        }
        return res;
    }
    
    private final String OPACITY_VISIBLE = "1";
    private final String OPACITY_HIDDEN =  "0.01";


    private String processEdgeMainFile(BufferedReader br) throws IOException {
        int eidCounter = 100;
        int stepTime = 500;
        List<TimelineEvent> events = getTimelineEvents();
        String line = null;
        StringBuilder sb = new StringBuilder();
        sb.append("/* EDGE SVG Generator, generated " + new Date() + "*/\n\n");
        boolean skipLines = false;
        boolean symbolSection = false;
        while ((line = br.readLine()) != null) {
            if (!symbolSection) {
                if (line.contains("minimumCompatibleVersion")) {
                    symbolSection = true;
                    append(sb, line);
                } else {
                    line = line.replaceAll("161", "" + globalHeight);
                    line = line.replaceAll("94", "" + globalWidth);
                    append(sb, line);
                    continue;
                }
            }
            line = processLine(line);
            if (line.contains("\"${symbolSelector}\": [")) {
                for (Map.Entry<String, Boolean> me : components.entrySet()) {
                    boolean visible = me.getValue();
                    //append(sb, "\"${_%s}\": [[\"style\", \"display\", '%s']],\n", me.getKey(), visible ? "block" : "none");
                    append(sb, "\"${_%s}\": [[\"style\", \"opacity\", '%s']],\n", me.getKey(), visible ? OPACITY_VISIBLE : OPACITY_HIDDEN);
                }
                sb.append(line);
                continue;
            }
            if (line.contains("c: [")) {
                skipLines = true;
                sb.append("c: [\n");
                boolean first = true;
                for (EdgeComponent ec : rootContainer.getComponents()) {
                    if (first) {
                        first = false;
                    } else {
                        sb.append(",\n");
                    }
                    addComponent(sb, ec);
                }
                sb.append("]\n");
                continue;
            }
            if (line.contains("duration:")) {
                int duration = events.size() * stepTime;
                append(sb, "duration: %d,", duration);
                continue;

            }
            StringBuilder tSb = new StringBuilder();
            StringBuilder lSb = new StringBuilder();
            lSb.append("labels:{");

            if (line.contains("timeline:")) {

                append(tSb, "timeline: [");
                boolean first = true;
                String time = "";
                String lastTime = "";
                int eventCounter = 1;
                for (TimelineEvent te : events) {
                    if (first) {
                        first = false;
                    } else {
                        tSb.append(",");
                        lSb.append(",");
                    }
                    String compId = te.getComponentId();
                    boolean display = te.getDisplay();
                    String oFrom = display?OPACITY_HIDDEN:OPACITY_VISIBLE;
                    String oTo = display?OPACITY_VISIBLE:OPACITY_HIDDEN;
                    //String displayInv = te.getDisplay(true);
                    lastTime = time;
                    time = te.getTime();
                    int edgeTime = eventCounter * stepTime;
                    append(tSb, "{ id: \"eid%d\",", eidCounter++);
                    append(tSb, "tween: [ ");
                    //append(tSb, "\"style\", \"${_%s}\", \"display\", '%s', { fromValue: '%s'}], position: %d, duration: 0 }", compId, display, displayInv, edgeTime);
                    append(tSb, "\"style\", \"${_%s}\", \"opacity\", '%s', { fromValue: '%s'}], position: %d, duration: 200 }", compId, oTo, oFrom, edgeTime);
                    if (!lastTime.equals(time)) {
                        eventCounter++;
                    }
                    String timeStr = time;
                    if (timeStr.length()>4){
                        timeStr= timeStr.substring(0,4);
                    }
                    String labelText = timeStr + " " + compId.replaceAll("_", " ").trim();

                    append(lSb, "\"%s\":%d", labelText, edgeTime);

                }
                lSb.append("},");
                sb.append(lSb);
                sb.append(tSb);

                continue;
            }
            if (!skipLines) {
                if (line.contains("rect: ")) {
                    append(sb, "rect: ['0px','0px','%d','%d','auto','auto'],", globalWidth, globalHeight);
                    append(sb, "clip:['rect(0px %dpx %dpx 0px)'],", globalWidth, globalHeight);
                } else {
                    line = line.replaceAll("161", "" + globalHeight);
                    line = line.replaceAll("94", "" + globalWidth);
                    append(sb, line);
                }
            } else {
                if (line.contains("}]")) {
                    skipLines = false;
                }
            }
        }
        IOHelper.saveStr(sb.toString(), "edge.js");
        return sb.toString();
    }

    private String processOtherFile(BufferedReader br) throws IOException {
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            line = processLine(line);
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }

    private void processTextFile(File f, OutputStream os) throws IOException {
        String line = null;
        BufferedReader br = new BufferedReader(new FileReader(f));
        String output = null;
        if (f.getName().endsWith("_edge.js")) {
            output = processEdgeMainFile(br);
        } else {
            output = processOtherFile(br);
        }
        br.close();
        os.write(output.getBytes());
    }

    public void addFile(File f) throws IOException {
        if (f.isDirectory()) {
            addAllFiles(f.listFiles());
        } else {
            String fileName = f.getPath().substring(srcDir.length() + 1);
            fileName = fileName.replaceAll(st, symbolName);
            ZipEntry ze = new ZipEntry(fileName);
            zos.putNextEntry(ze);
            if ((fileName.endsWith(".an")) || ((fileName.endsWith(".js"))) || (fileName.endsWith(".html"))) {
                processTextFile(f, zos);
            } else {
                IOHelper.readFileToOutputStream(f, zos);
            }
            zos.closeEntry();
        }
    }

    public void addZipEntry(String fileName, byte[] content) throws IOException {
        ZipEntry ze = new ZipEntry(fileName);
        zos.putNextEntry(ze);
        zos.write(content);
        zos.closeEntry();
    }

    public void generateEdgeSymbol() throws IOException {
        System.out.println("DEST FILE " + destFile);
        zos = new ZipOutputStream(new FileOutputStream(destFile));
        zos.setLevel(9);
        File f = new File(srcDir);
        addAllFiles(f.listFiles());
        if (edgeImages != null) {
            for (EdgeImage ei : edgeImages) {
                addZipEntry("images/" + ei.getFileName(), ei.getContent());
            }
        }
        zos.close();
    }

    public int getGlobalWidth() {
        return globalWidth;
    }

    public void setGlobalWidth(int globalWidth) {
        this.globalWidth = globalWidth;
    }

    public int getGlobalHeight() {
        return globalHeight;
    }

    public void setGlobalHeight(int globalHeight) {
        this.globalHeight = globalHeight;
    }

    public String getDestFile() {
        return destFile;
    }

    public void setDestFile(String destFile) {
        this.destFile = destFile;
    }

    
    
    public static void main(String[] args) throws IOException {
        EdgeTemplateGenerator etg = new EdgeTemplateGenerator();
        etg.generateEdgeSymbol();
    }
}
