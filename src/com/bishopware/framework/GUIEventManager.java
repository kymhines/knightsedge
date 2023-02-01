/*
 * BishopWare Software
 * 
 * File: GUIEventManager.java
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

package com.bishopware.framework;

import java.util.Hashtable;

/**
 * Event managers oversee a set of GUI events. It provides somewhat similar
 * functionality to the awt event model, except that it isn't necessary for
 * "listeners" to be explicitly added to the affected component. Instead,
 * generic listeners can add themselves to a named events as follows:<br>
 * manager.getEventMedium("ArchitectureChanged").addListener(this);
 * <p>
 * 
 * @author Kym Hines
 */

public class GUIEventManager {

    private final Hashtable<String, GUIEventMedium> m_events;

    private static GUIEventManager k_defaultmanager;

    public GUIEventManager() {

        m_events = new Hashtable<String, GUIEventMedium>();
    }

    public synchronized void addEventMedium(String name, GUIEventMedium ev) {
        m_events.put(name, ev);
    }

    public synchronized GUIEventMedium getEventMedium(String name) {
        return m_events.get(name);
    }

    public synchronized static GUIEventManager getDefaultManager() {
        if (k_defaultmanager == null) {
            k_defaultmanager = new GUIEventManager();
        }
        return k_defaultmanager;
    }

    public synchronized static void mainRegisterEvent(String eventName,
            GUIEventListener list) {
        getDefaultManager().registerEvent(eventName, list);
    }

    public synchronized void registerEvent(String eventName,
            GUIEventListener list) {
        checkEventMedium(eventName).addListener(list);
    }

    public synchronized GUIEventMedium checkEventMedium(String name) {
        if (m_events.containsKey(name)) {
            return m_events.get(name);
        }
        else {
            GUIEventMedium eventMedium = new GUIEventMedium(name);
            m_events.put(name, eventMedium);
            return eventMedium;
        }
    }
}
