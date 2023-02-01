/*
 * BishopWare Software
 * 
 * File: Triple.java
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

public class Triple<T1, T2, T3> extends Tuple {
    private final T1 m_first;

    private final T2 m_second;

    private final T3 m_third;

    public Triple(T1 first, T2 second, T3 third) {
        m_first = first;
        m_second = second;
        m_third = third;
    }

    public T1 getFirst() {
        return m_first;
    }

    public T2 getSecond() {
        return m_second;
    }

    public T3 getThird() {
        return m_third;
    }
}
