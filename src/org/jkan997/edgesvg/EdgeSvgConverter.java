/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg;

import java.io.ByteArrayInputStream;
import org.jkan997.edgesvg.entity.EdgeImageFormat;
import org.jkan997.edgesvg.entity.EdgeImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.jkan997.edgesvg.entity.EdgeComponent;
import org.jkan997.edgesvg.entity.EdgeContainer;
import org.jkan997.edgesvg.entity.TimelinePosition;
import org.jkan997.edgesvg.helper.IOHelper;
import org.jkan997.edgesvg.path.PathBounds;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jakaniew
 */
public class EdgeSvgConverter {

    private int width;
    private int height;
    private EdgeContainer mainContainer = new EdgeContainer();
    private EdgeContainer currentContainer = mainContainer;
    private List<TimelinePosition> timeline = new LinkedList<TimelinePosition>();
    private Map<String, Boolean> components = new HashMap<String, Boolean>();
    private int noidCounter = 0;
    Document doc = null;
    private String destFile = null;
    private String inputFile = null;

    public void loadSVG() {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
            System.out.println("Input SVG file " + inputFile);
            System.out.println(inputFile);
            doc = builder.parse(new FileInputStream(inputFile));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void process() throws Exception {
        inRootContainer = false;
        Element rootElement = doc.getDocumentElement();
        String wStr = rootElement.getAttribute("width");
        String hStr = rootElement.getAttribute("height");
        wStr = wStr.replaceAll("px", "").trim();
        hStr = hStr.replaceAll("px", "").trim();
        this.width = Integer.parseInt(wStr);
        this.height = Integer.parseInt(hStr);
        processElement(doc.getDocumentElement());
    }

    public void rasterizePNG(Reader reader, OutputStream outputStream) throws Exception {
        PNGTranscoder t = new PNGTranscoder();
        TranscoderInput input = new TranscoderInput(reader);
        TranscoderOutput output = new TranscoderOutput(outputStream);
        t.transcode(input, output);
        outputStream.flush();
        outputStream.close();
    }

    public void save(Element el, String filePath) throws Exception {
        String d = el.getAttribute("d");
        String id = el.getAttribute("id");

        if (el.hasAttribute("display")) {
            el.removeAttribute("display");
        }

        Bounds bounds = PathBounds.getBounds(d);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(el);
        //File f = new File(filePath);
        //FileOutputStream os = new FileOutputStream(f);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        StringBuilder svgHeader = new StringBuilder();
        int margin = 5;
        int w = bounds.getWidth() + 10;
        int h = bounds.getHeight() + 10;
        int tx = bounds.getMinX() - 5;
        int ty = bounds.getMinY() - 5;
        svgHeader.append("<svg:svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:dc=\"http://purl.org/dc/elements/1.1\" ");
        svgHeader.append("width=\"" + w + "px\" ");
        svgHeader.append("height=\"" + h + "\" ");
        svgHeader.append("viewBox=\"0 0 " + w + " " + h + "\" >\n");
        svgHeader.append("<g transform=\"translate(" + (0 - tx) + " " + (0 - ty) + ")\">\n");
        os.write(svgHeader.toString().getBytes());
        StreamResult streamResult = new StreamResult(os);
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(source, streamResult);
        os.write("\n</g></svg:svg>".getBytes());
        os.close();
        byte[] svgBytes = os.toByteArray();
        os = null;
        EdgeImage ei = new EdgeImage();
        ei.setFormat(EdgeImageFormat.png);

        if (ei.getFormat() == EdgeImageFormat.png) {
            ByteArrayInputStream bais = new ByteArrayInputStream(svgBytes);
            Reader r = new InputStreamReader(bais);
            ByteArrayOutputStream os2 = new ByteArrayOutputStream();
            rasterizePNG(r, os2);
            r.close();
            os2.close();
            ei.setContent(os2.toByteArray());
        }

        if (ei.getFormat() == EdgeImageFormat.svg) {
            ei.setContent(svgBytes);
        }
        if (id.length() == 0) {
            id = "noid_" + (++noidCounter);
        }
        ei.setId(id);
        ei.setWidth(w);
        ei.setHeight(h);
        ei.setX(tx);
        ei.setY(ty);
        currentContainer.addComponent(ei);

    }
    int x = 0;
    private StringBuilder compSb;
    private StringBuilder timelineSb;

    public boolean processScriptText(String sText) {
        boolean res = false;
        compSb = new StringBuilder();
        timelineSb = new StringBuilder();
        String[] lines = sText.split("\n");
        boolean compMode = false;
        boolean timelineMode = false;
        int i = 0;
        String line;
        while (i < lines.length) {
            line = lines[i];
            i++;
            if (line.startsWith("timelinePositions =")) {
                timelineMode = true;
                compMode = false;
                res = true;
                continue;
            }
            if (line.startsWith("this.components =")) {
                timelineMode = false;
                compMode = true;
                res = true;
                continue;
            }
            if (compMode) {
                if (line.contains("};")) {
                    compMode = false;
                    compSb.append("}\n");
                    continue;
                }
                compSb.append(line);
                compSb.append("\n");
            }
            if (timelineMode) {
                if (line.contains("} ]")) {
                    timelineMode = false;
                    timelineSb.append("}]\n");
                    continue;
                }
                timelineSb.append(line);
                timelineSb.append("\n");

            }

        }
        return res;
    }

    public void processScript(Element el) {
        NodeList nl = el.getChildNodes();
        boolean finish = false;
        for (int i = 0; i < nl.getLength(); i++) {
            Node sNode = nl.item(i);
            if (sNode instanceof CharacterData) {
                CharacterData cd = (CharacterData) sNode;
                finish = processScriptText(cd.getData());
                if (finish) {
                    break;
                }
            }
        }
        if ((timelineSb != null) && (timelineSb.length() > 0) && (compSb != null) && (compSb.length() > 0)) {
            try {
                FileWriter fw1 = new FileWriter("/tmp/timeline.json");
                FileWriter fw2 = new FileWriter("/tmp/comp.json");
                fw1.append(timelineSb.toString());
                fw2.append(compSb.toString());
                fw1.close();
                fw2.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            JSONTokener timelineTok = new JSONTokener(timelineSb.toString());
            JSONTokener compTok = new JSONTokener(compSb.toString());
            JSONObject compJson = (JSONObject) compTok.nextValue();
            JSONArray timelineJson = (JSONArray) timelineTok.nextValue();
            JSONObject timelinePosJson;
            for (int i = 0; i < timelineJson.length(); i++) {
                timelinePosJson = timelineJson.getJSONObject(i);
                TimelinePosition tp = new TimelinePosition(timelinePosJson);
                timeline.add(tp);
            }

            Iterator<String> it = compJson.keys();

            while (it.hasNext()) {
                String key = it.next();
                components.put(key, compJson.getBoolean(key));
            }

            return;
        }
    }
    private boolean inRootContainer = false;

    public void processElement(Element el) throws Exception {
        NodeList nl = el.getChildNodes();
        String name = el.getNodeName();
        if ((inRootContainer) && ("path".equals(name))) {
            try {
                save(el, "/tmp/out_" + (++x) + ".xml");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        EdgeContainer oldContainer = null;
        if ("g".equals(name)) {
            String containerId = el.getAttribute("id");

            if (inRootContainer) {
                oldContainer = currentContainer;
                currentContainer = new EdgeContainer();
                currentContainer.setId(containerId);
                oldContainer.addComponent(currentContainer);
            } else {
                if (containerId.equals("root")) {
                    inRootContainer = true;
                }
            }
        }

        if ("script".equals(name)) {
            processScript(el);
        }
        int nlLen = nl.getLength();
        Node n = null;
        Element el2 = null;
        for (int i = 0; i < nlLen; i++) {
            n = nl.item(i);
            if (n instanceof Element) {
                el2 = (Element) n;
                processElement(el2);
            }
        }
        if ((oldContainer != null) && ("g".equals(name))) {
            currentContainer = oldContainer;
        }
    }

    public void saveSymbol() throws IOException {
        EdgeTemplateGenerator etg = new EdgeTemplateGenerator();
        System.out.println("D2 FILE "+destFile);
        if (destFile != null) {
            etg.setDestFile(destFile);
        }
        etg.setGlobalWidth(width);
        etg.setGlobalHeight(height);
        etg.setRootContainer(mainContainer);
        etg.setComponents(components);
        etg.setTimeline(timeline);

        etg.generateEdgeSymbol();
    }

    private void append(StringBuilder sb, String pattern, Object... params) {
        sb.append(String.format(pattern, params));
        sb.append("\n");
    }

    private void printComp(StringBuilder sb, EdgeComponent comp) {
        if (comp instanceof EdgeImage) {
            append(sb, "<component id=\"%s\"/>", comp.getId());
        }
        if (comp instanceof EdgeContainer) {
            EdgeContainer ec = (EdgeContainer) comp;
            append(sb, "<container id=\"%s\">", ec.getId());
            for (EdgeComponent c2 : ec.getComponents()) {
                printComp(sb, c2);
            }
            append(sb, "</container>");

        }
    }

    public void printStruct() throws Exception {
        StringBuilder sb = new StringBuilder();
        EdgeContainer mainContainer = this.mainContainer;
        printComp(sb, mainContainer);
        IOHelper.saveStr(sb.toString(), "edge_struct.xml");


    }

    public String getDestFile() {
        return destFile;
    }

    public void setDestFile(String destFile) {
        this.destFile = destFile;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public static void main(String[] args) throws Exception {
        String input = "/Volumes/MacData/jakaniew/svn/maps/us_states.svg";
        String output = "/Volumes/MacData/jakaniew/svn/maps/us_states.ansym";
        
        EdgeSvgConverter esc = new EdgeSvgConverter();
        esc.setInputFile(input);
        esc.setDestFile(output);
        esc.loadSVG();
        esc.process();
        esc.printStruct();
        esc.saveSymbol();
        System.out.println("END");
    }
}
