/*
 * BishopWare Software
 * 
 * File: StringUtilities.java
 * 
 * Copyright: 2009 Kym Hines and BishopWare
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bishopware.util;

import java.util.LinkedList;
import java.util.List;

public class StringUtilities {

    public static String getBaseName(String name) {
        int index = name.lastIndexOf(".");
        int slashIndex = name.lastIndexOf(FileUtilities.k_fileSeparator);
        if (slashIndex < 0) {
            slashIndex = 0;
        }
        if (index >= 0) {
            return name.substring(slashIndex, index);
        }
        else {
            return name.substring(slashIndex);
        }
    }

    public static int getCountOfChar(String value, char c) {
        if (value == null || value.equals("")) {
            return 0;
        }
        else {
            int count = 0;
            for (int i = 0; i < value.length(); ++i) {
                if (value.charAt(i) == c) {
                    count++;
                }
            }
            return count;
        }
    }

    /**
     * @param fileName
     * @return The file name extension (empty string if there is none)
     */
    public static String getExtension(String fileName) {
        int x = fileName.lastIndexOf(".");
        if (x == -1) {
            return "";
        }
        else {
            return fileName.substring(x + 1);
        }
    }

    public static String reassembleTokens(List<String> values, String separator) {
        return reassembleTokens(values.toArray(new String[0]), separator);
    }

    public static String reassembleTokens(String[] versionValues,
            String separator) {
        StringBuilder buffer = new StringBuilder();
        boolean first = true;
        for (String versionValue : versionValues) {
            if (!first) {
                buffer.append(separator);
            }
            buffer.append(versionValue);
            first = false;
        }
        return buffer.toString();
    }

    public static String removeFirstSegments(String path, int segmentCount) {
        String[] segments = path.split("/");
        StringBuilder retval = new StringBuilder();
        for (int i = 0; i < segmentCount && i < segments.length; ++i) {
            if (i > 0) {
                retval.append("/");
            }
            retval.append(segments[i]);
        }
        return retval.toString();
    }

    public static String removeLastPathSegment(String name) {
        int slashIndex = name.lastIndexOf(FileUtilities.k_fileSeparator);
        if (slashIndex < 0) {
            slashIndex = 0;
        }
        return name.substring(0, slashIndex);
    }

    public static int segmentCount(String path) {
        String[] segments = path.split("/");
        return segments.length;
    }

    public static String squashSlashes(String string) {
        while (string.contains("//")) {
            string = string.replace("//", "/");
        }
        return string;
    }

    public static List<String> tokenize(String values, char separator) {
        List<String> retval = new LinkedList<String>();
        if ("".equals(values.trim())) {
            return retval;
        }
        int startPoint = 0;

        while (true) {
            int indexEnd = values.indexOf(separator, startPoint);
            if (indexEnd < 0) {
                break;
            }
            String val = values.substring(startPoint, indexEnd).trim();
            startPoint = indexEnd + 1;
            retval.add(val);
        }
        // Get the last bit in.
        retval.add(values.substring(startPoint).trim());
        return retval;
    }
}
