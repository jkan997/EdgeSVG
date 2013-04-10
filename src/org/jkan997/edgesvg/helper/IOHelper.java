package org.jkan997.edgesvg.helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IOHelper {

    public static final int BUFFER_SIZE = 1024;

    public static String readReaderToString(Reader reader)
            throws IOException {
        StringBuilder res = new StringBuilder();
        char[] buf = new char[1024];
        int count = 0;
        while ((count = reader.read(buf)) != -1) {
            res.append(buf, 0, count);
        }
        return res.toString();
    }

    public static String readInputStreamToString(InputStream is) throws IOException {
        return readReaderToString(new InputStreamReader(is));
    }

    public static void readInputStreamToOutputStream(InputStream is, OutputStream os) throws IOException {
        byte[] buf = new byte[1024];
        int count;
        while ((count = is.read(buf)) > 0) {
            os.write(buf, 0, count);
        }
    }

    public static void readFileToOutputStream(File file, OutputStream os) throws IOException {
        FileInputStream is = new FileInputStream(file);
        byte[] buf = new byte[1024];
        int count;
        while ((count = is.read(buf)) > 0) {
            os.write(buf, 0, count);
        }
        is.close();
    }

    public static byte[] readFileToBytes(File file) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        FileInputStream is = new FileInputStream(file);
        byte[] buf = new byte[1024];
        int count;
        while ((count = is.read(buf)) > 0) {
            bos.write(buf, 0, count);
        }
        is.close();
        bos.close();
        return bos.toByteArray();
    }

    public static void readReaderToWriter(Reader reader, Writer writer)
            throws IOException {
        char[] buf = new char[1024];
        int count = 0;
        while ((count = reader.read(buf)) != -1) {
            writer.write(buf, 0, count);
        }
    }

    public static void readReaderToOutputStream(Reader reader, Writer writer) throws IOException {
        char[] buf = new char[1024];
        int count = 0;
        while ((count = reader.read(buf)) != -1) {
            writer.write(buf, 0, count);
        }
    }

    public static void saveStr(String s, String filePath) {
        try {
            if (!filePath.startsWith("/")) {
                filePath = "/tmp/" + filePath;
            }
            FileWriter fw = new FileWriter(filePath);
            fw.append(s);
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
