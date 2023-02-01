/*
 * BishopWare Software
 * 
 * File: LRUMap.java
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

package com.bishopware.util.collections;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUMap<t_key, t_value> extends LinkedHashMap<t_key, t_value> {

    /**
     * Silence the SVUID warning.
     */
    private static final long serialVersionUID = 1L;

    private final int m_maxSize;

    public LRUMap(int maxSize) {
        m_maxSize = maxSize;
    }

    @SuppressWarnings("unchecked")
    @Override
    public t_value get(Object key) {
        t_value retval = super.get(key);
        if (retval != null) {
            // Reinsert this so that it is now the newest item in the map
            t_key k = (t_key) key;
            super.remove(k);
            put(k, retval);
            return retval;
        }
        else {
            return null;
        }
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<t_key, t_value> eldest) {
        if (size() > m_maxSize) {
            return true;
        }
        else {
            return false;
        }
    }

}
