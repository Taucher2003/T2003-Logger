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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnsiColorTest {

    static final String RESET = "\u001B[0m",
            BLACK = "\u001B[30m",
            RED = "\u001B[31m",
            GREEN = "\u001B[32m",
            YELLOW = "\u001B[33m",
            BLUE = "\u001B[34m",
            PURPLE = "\u001B[35m",
            CYAN = "\u001B[36m",
            WHITE = "\u001B[37m";

    @Test
    void black() {
        assertEquals(BLACK + "message" + RESET, AnsiColor.BLACK.colorize("message"));
    }

    @Test
    void red() {
        assertEquals(RED + "message" + RESET, AnsiColor.RED.colorize("message"));
    }

    @Test
    void green() {
        assertEquals(GREEN + "message" + RESET, AnsiColor.GREEN.colorize("message"));
    }

    @Test
    void yellow() {
        assertEquals(YELLOW + "message" + RESET, AnsiColor.YELLOW.colorize("message"));
    }

    @Test
    void blue() {
        assertEquals(BLUE + "message" + RESET, AnsiColor.BLUE.colorize("message"));
    }

    @Test
    void purple() {
        assertEquals(PURPLE + "message" + RESET, AnsiColor.PURPLE.colorize("message"));
    }

    @Test
    void cyan() {
        assertEquals(CYAN + "message" + RESET, AnsiColor.CYAN.colorize("message"));
    }

    @Test
    void white() {
        assertEquals(WHITE + "message" + RESET, AnsiColor.WHITE.colorize("message"));
    }

    @Test
    void stripColors() {
        for(AnsiColor color : AnsiColor.values()) {
            String coloredString = color.colorize("message");
            assertEquals(color.ansi + "message" + AnsiColor.RESET.ansi, coloredString);
            String stripped = AnsiColor.stripColors(coloredString);
            assertEquals("message", stripped);
        }
    }
}