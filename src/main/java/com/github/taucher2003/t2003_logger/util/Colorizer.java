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

public class Colorizer {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static String black(String string) {
        return ANSI_BLACK + string + ANSI_RESET;
    }

    public static String red(String string) {
        return ANSI_RED + string + ANSI_RESET;
    }

    public static String green(String string) {
        return ANSI_GREEN + string + ANSI_RESET;
    }

    public static String yellow(String string) {
        return ANSI_YELLOW + string + ANSI_RESET;
    }

    public static String blue(String string) {
        return ANSI_BLUE + string + ANSI_RESET;
    }

    public static String purple(String string) {
        return ANSI_PURPLE + string + ANSI_RESET;
    }

    public static String cyan(String string) {
        return ANSI_CYAN + string + ANSI_RESET;
    }

    public static String white(String string) {
        return ANSI_WHITE + string + ANSI_RESET;
    }
}
