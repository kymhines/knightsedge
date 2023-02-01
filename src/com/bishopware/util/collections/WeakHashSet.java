/*
 * BishopWare Software
 * 
 * File: WeakHashSet.java
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

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class WeakHashSet<t_value> implements Set<t_value> {

    private final WeakHashMap<t_value, WeakReference<t_value>> m_contents = new WeakHashMap<t_value, WeakReference<t_value>>();

    public boolean add(t_value value) {
        if (m_contents.containsKey(value)) {
            return false;
        }
        else {
            m_contents.put(value, new WeakReference<t_value>(value));
            return true;
        }
    }

    public boolean addAll(Collection<? extends t_value> values) {
        boolean changed = false;
        for (t_value value : values) {
            changed |= add(value);
        }
        return changed;
    }

    public void clear() {
        m_contents.clear();
    }

    public boolean contains(Object value) {
        return m_contents.containsKey(value);
    }

    public boolean containsAll(Collection<?> values) {
        boolean changed = false;
        for (Object value : values) {
            changed |= contains(value);
        }
        return changed;
    }

    public boolean isEmpty() {
        return m_contents.isEmpty();
    }

    public Iterator<t_value> iterator() {
        return m_contents.keySet().iterator();
    }

    public boolean remove(Object value) {
        if (m_contents.containsKey(value)) {
            m_contents.remove(value);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean removeAll(Collection<?> values) {
        boolean changed = false;
        for (Object value : values) {
            changed |= remove(value);
        }
        return changed;
    }

    public boolean retainAll(Collection<?> c) {
        HashSet<t_value> valueCopies = new HashSet<t_value>(this);
        boolean changed = false;
        for (t_value value : valueCopies) {
            if (!c.contains(value)) {
                changed |= remove(value);
            }
        }
        return changed;
    }

    public int size() {
        return m_contents.size();
    }

    public Object[] toArray() {
        return m_contents.keySet().toArray();
    }

    public <T> T[] toArray(T[] startArray) {
        return m_contents.keySet().toArray(startArray);
    }

}
