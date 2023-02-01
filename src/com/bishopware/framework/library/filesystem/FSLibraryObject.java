/*
 * BishopWare Software
 * 
 * File: FSLibraryObject.java
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

package com.bishopware.framework.library.filesystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.bishopware.framework.library.*;

public class FSLibraryObject implements LibraryObject {

    private File m_wrappedFile;

    private String m_fullName;

    public String getShortName() {
        int lastDot = m_fullName.lastIndexOf('.');
        if (lastDot < 0) {
            return m_fullName;
        }
        else {
            return m_fullName.substring(lastDot + 1);
        }
    }

    public InputStream getInputStream() {
        if (m_wrappedFile != null) {
            try {
                return new FileInputStream(m_wrappedFile);
            }
            catch (FileNotFoundException e) {
                return null;
            }
        }
        else {
            return null;
        }
    }

}
