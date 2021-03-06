package com.company.cbess.util;

import java.io.*;

/**
 * Represents the package util.
 */
public class Util {
    public static String getResourceContents(String path) {
        // add root slash
        if (path.charAt(0) != '/') {
            path = "/" + path;
        }

        // get the bundle contents
        InputStream inputStream = Util.class.getResourceAsStream(path);
        if (inputStream == null) {
            return null;
        }

        return getContents(inputStream);
    }


    public static String getContents(InputStream inputStream) {

        // open stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder("");

        // read until end of stream
        try {
            String line = reader.readLine();
            do {
                stringBuilder.append(line).append("\n");
            } while ((line = reader.readLine()) != null);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();

    }

    public static String getContents(String path) {

        FileInputStream fileInputStream;

        try {
            fileInputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return getContents(fileInputStream);
    }
}
