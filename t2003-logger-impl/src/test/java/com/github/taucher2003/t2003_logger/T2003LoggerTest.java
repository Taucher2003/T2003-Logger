/*
 * Copyright 2021 Niklas van Schrick and the T2003-Logger contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   	http://www.apache.org/licenses/LICENSE-2.0
 *
 * 	Unless required by applicable law or agreed to in writing, software
 * 	distributed under the License is distributed on an "AS IS" BASIS,
 * 	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 	See the License for the specific language governing permissions and
 * 	limitations under the License.
 *
 */

package com.github.taucher2003.t2003_logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.event.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;

class T2003LoggerTest {

    T2003LoggerFactory factory;

    @BeforeEach
    void init() {
        factory = new T2003LoggerFactory();
    }

    @Test
    void level() {
        T2003Logger logger = (T2003Logger) factory.getLogger(T2003LoggerTest.class.getCanonicalName());
        logger.setLevel(Level.DEBUG);
        assertEquals(Level.DEBUG, ((T2003Logger) factory.getLogger(T2003LoggerTest.class.getCanonicalName())).getLevel());
        assertEquals(Level.INFO, ((T2003Logger) factory.getLogger(T2003Logger.class.getCanonicalName())).getLevel());
    }

    @Test
    void levelFile() {
        T2003Logger logger = (T2003Logger) factory.getLogger(T2003LoggerTest.class.getCanonicalName());
        assertEquals(Level.TRACE, logger.getLevel());
        logger = (T2003Logger) factory.getLogger(T2003Logger.class.getCanonicalName());
        assertEquals(Level.INFO, logger.getLevel());
    }
}