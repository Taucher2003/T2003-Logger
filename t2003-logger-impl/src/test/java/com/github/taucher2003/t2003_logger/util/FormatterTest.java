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

package com.github.taucher2003.t2003_logger.util;

import com.github.taucher2003.t2003_logger.T2003Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FormatterTest {

    @Test
    void format() {
        String message = "Object1 {} Object2 {} {}";
        assertEquals("Object1 hello Object2 world {}", Formatter.format(message, "hello", "world"));
        assertEquals("Object1 hello Object2 world !", Formatter.format(message, "hello", "world", "!"));
        assertNotEquals("Object1 hello Object2 world ", Formatter.format(message, "hello", "world"));
        assertNotEquals("Object1 hello Object2 world {}", Formatter.format(message, "hello", "world", "!"));
    }

    @Test
    void shortenThreadName() {
        T2003Logger logger = new T2003Logger(FormatterTest.class.getCanonicalName());
        String longThreadName = "SomeReallyLongThreadNameThatDoesntMakeAnySense";
        String threadName = "Thats17Characters";
        assertEquals("SomeRea...ySense", logger.getFormatter().shortenThreadName(longThreadName));
        assertEquals("Thats17...acters", logger.getFormatter().shortenThreadName(threadName));

        logger = new T2003Logger(AnsiColorTest.class.getCanonicalName());
        assertEquals("SomeReal...ySense", logger.getFormatter().shortenThreadName(longThreadName));
        assertEquals("Thats17Characters", logger.getFormatter().shortenThreadName(threadName));
    }
}