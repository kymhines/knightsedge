/*
 * BishopWare Software
 * 
 * File: FileUtilities.java
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class FileUtilities {

    private static String s_path = null;

    public static final String k_fileSeparator = System
            .getProperty("file.separator");

    public static Map<String, String> buildOptionsMap(String[] args) {
        HashMap<String, String> retval = new HashMap<String, String>();
        String pendingKey = null;
        for (String arg : args) {
            String trimmedArg = arg.trim();
            if (trimmedArg.startsWith("--")) {
                pendingKey = trimmedArg.replace("--", "");
            }
            else if (pendingKey != null) {
                retval.put(pendingKey, trimmedArg);
                pendingKey = null;
            }
        }
        return retval;
    }

    public static String contentsToString(InputStream is) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader d = new BufferedReader(new InputStreamReader(is));

        String line = d.readLine();
        while (null != line) {
            // System.out.println("Line: " + line);
            line += "\n";
            builder.append(line);
            line = d.readLine();
        }
        d.close();
        return builder.toString();
    }

    public static String contentsToString(String fileName) {
        try {
            return contentsToString(new FileInputStream(fileName));
        }
        catch (Exception e) {
            return "";
        }
    }

    /**
     * Brain dead routine for unbuffered file copying.
     */
    public static void copyFile(File sourceFile, File destinationFile)
            throws IOException {
        FileInputStream is = new FileInputStream(sourceFile);
        FileOutputStream os = new FileOutputStream(destinationFile);
        int oneChar, count = 0;
        while ((oneChar = is.read()) != -1) {
            os.write(oneChar);
            count++;
        }
        is.close();
        os.close();
    }

    public static String stripExtension(String name) {
        final int index = name.lastIndexOf('.');
        if (index < 0) {
            return name;
        }
        else {
            return name.substring(0, index);
        }
    }

    public static void copyResources(JarOutputStream jar,
            File resourceDirectory, String path, boolean hideDotfiles)
            throws FileNotFoundException, IOException {
        File[] files = resourceDirectory.listFiles();
        for (File file : files) {
            if (!hideDotfiles || !file.getName().startsWith(".")) {
                if (file.isDirectory()) {
                    copyResources(jar, file, ((path != null) ? path + "/" : "")
                            + file.getName(), hideDotfiles);
                }
                else {
                    FileInputStream fileStream = new FileInputStream(file);
                    JarEntry myEntry = new JarEntry(((path != null) ? path
                            + "/" : "")
                            + file.getName());
                    jar.putNextEntry(myEntry);
                    fastStreamCopy(fileStream, jar);
                    jar.closeEntry();
                }
            }
        }
    }

    public static void fastStreamCopy(InputStream srcStream,
            OutputStream destStream) throws IOException {

        final ReadableByteChannel src = Channels.newChannel(srcStream);
        final WritableByteChannel dest = Channels.newChannel(destStream);
        final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            buffer.flip();
            dest.write(buffer);
            buffer.compact();
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }

    public static String getExecutionPath() {
        if (null == s_path) {
            String sz = new File(".").getAbsolutePath();
            sz = sz.substring(0, sz.lastIndexOf(k_fileSeparator));
            s_path = sz;
        }

        return s_path;
    }

    public static void recursiveDelete(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                recursiveDelete(child);
            }
        }
        else {
            file.delete();
        }
    }

    public static void saveManifest(Manifest mf, File file) throws IOException {
        OutputStream stream = new FileOutputStream(file);
        mf.write(stream);
        stream.close();

    }

    public static void setContents(String path, String contents)
            throws IOException {
        FileOutputStream stream = new FileOutputStream(path);
        stream.write(contents.getBytes());
        stream.close();
    }

    public static String transformToNamespace(String string) {
        String tempValue = string.replace("/", ".");

        while (tempValue.contains("..")) {
            tempValue = tempValue.replace("..", ".");
        }
        return tempValue;
    }

    public static String transformToPath(String typeName) {
        return typeName.replace(".", "/");
    }
}
