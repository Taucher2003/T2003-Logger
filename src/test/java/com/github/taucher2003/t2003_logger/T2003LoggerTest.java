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

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;

class T2003LoggerTest {

    @Test
    void level() {
        Logger logger = LoggerFactory.getLogger(T2003LoggerTest.class);
        ((T2003Logger)logger).setLevel(Level.DEBUG);
        assertEquals(Level.DEBUG, ((T2003Logger)LoggerFactory.getLogger(T2003LoggerTest.class)).getLevel());
        assertEquals(Level.INFO, ((T2003Logger)LoggerFactory.getLogger(T2003Logger.class)).getLevel());
    }

    @Test
    void levelFile() {
        Logger logger = LoggerFactory.getLogger(T2003LoggerTest.class);
        assertEquals(Level.TRACE, ((T2003Logger)logger).getLevel());
        logger = LoggerFactory.getLogger(T2003Logger.class);
        assertEquals(Level.INFO, ((T2003Logger)logger).getLevel());
    }
}