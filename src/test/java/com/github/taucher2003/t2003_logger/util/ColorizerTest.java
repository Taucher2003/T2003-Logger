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

import static com.github.taucher2003.t2003_logger.util.Colorizer.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ColorizerTest {

    @Test
    void black() {
        assertEquals(ANSI_BLACK + "message" + ANSI_RESET, Colorizer.black("message"));
    }

    @Test
    void red() {
        assertEquals(ANSI_RED + "message" + ANSI_RESET, Colorizer.red("message"));
    }

    @Test
    void green() {
        assertEquals(ANSI_GREEN + "message" + ANSI_RESET, Colorizer.green("message"));
    }

    @Test
    void yellow() {
        assertEquals(ANSI_YELLOW + "message" + ANSI_RESET, Colorizer.yellow("message"));
    }

    @Test
    void blue() {
        assertEquals(ANSI_BLUE + "message" + ANSI_RESET, Colorizer.blue("message"));
    }

    @Test
    void purple() {
        assertEquals(ANSI_PURPLE + "message" + ANSI_RESET, Colorizer.purple("message"));
    }

    @Test
    void cyan() {
        assertEquals(ANSI_CYAN + "message" + ANSI_RESET, Colorizer.cyan("message"));
    }

    @Test
    void white() {
        assertEquals(ANSI_WHITE + "message" + ANSI_RESET, Colorizer.white("message"));
    }
}