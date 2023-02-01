/*
 * BishopWare Software
 * 
 * File: HashMultiMap.java
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class MultiHashMap<t_key, t_value> implements
        Iterable<Entry<t_key, Set<t_value>>> {

    private final HashMap<t_key, Set<t_value>> m_map = new LinkedHashMap<t_key, Set<t_value>>();

    public void clear() {
        m_map.clear();
    }

    public Set<Map.Entry<t_key, Set<t_value>>> entrySet() {
        return m_map.entrySet();
    }

    public Set<t_value> get(t_key key) {
        Set<t_value> set = m_map.get(key);
        return set;
    }

    public Iterator<Entry<t_key, Set<t_value>>> iterator() {
        return m_map.entrySet().iterator();
    }

    public Set<t_key> keySet() {
        return m_map.keySet();
    }

    public void put(t_key key, t_value value) {
        Set<t_value> set = get(key);
        if (set == null) {
            set = new LinkedHashSet<t_value>();
            m_map.put(key, set);
        }
        set.add(value);
    }

    public boolean remove(t_key key, t_value value) {
        Set<t_value> set = get(key);
        if (set != null) {
            return set.remove(value);
        }
        else {
            return true;
        }
    }

    public Set<t_value> values() {
        Set<t_value> retval = new LinkedHashSet<t_value>();
        for (Set<t_value> valueSegment : m_map.values()) {
            retval.addAll(valueSegment);
        }
        return retval;
    }
}
