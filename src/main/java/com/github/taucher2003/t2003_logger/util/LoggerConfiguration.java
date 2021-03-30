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

import org.slf4j.event.Level;
import org.slf4j.helpers.Util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class LoggerConfiguration {

    private static final String PREFIX = "logging.";
    private static final String LOG_LEVEL = PREFIX + "level";
    private static final String PRINT_STREAM = PREFIX + "printstream";
    private static final String SHOW_DATE = PREFIX + "show_date";
    private static final String SHOW_THREAD_NAME = PREFIX + "show_thread_name";
    private static final String THREAD_IN_BRACES = PREFIX + "thread_name_in_braces";

    private Level logLevel;
    final String name;
    final PrintStream printStream;
    final boolean showDate;
    final boolean showThread;
    final boolean threadInBraces;

    public LoggerConfiguration(String name) {
        this.name = name;
        try {
            this.logLevel = Level.valueOf(computeCustomSettings(LOG_LEVEL, "INFO"));
        }catch(IllegalArgumentException | EnumConstantNotPresentException exception) {
            Util.report("Log Level has been set to an invalid value", exception);
            this.logLevel = Level.INFO;
        }
        this.printStream = getPrintStream(computeCustomSettings(PRINT_STREAM, "System.err"));
        this.showDate = Boolean.parseBoolean(computeCustomSettings(SHOW_DATE, "true"));
        this.showThread = Boolean.parseBoolean(computeCustomSettings(SHOW_THREAD_NAME, "true"));
        this.threadInBraces = Boolean.parseBoolean(computeCustomSettings(THREAD_IN_BRACES, "true"));
    }

    private String computeCustomSettings(String key, String ifAbsent) {
        String computingName = key + "." + name;
        String value = null;
        int lastIndex = computingName.length();
        while (value == null && lastIndex > -1) {
            computingName = computingName.substring(0, lastIndex);
            value = PropertyLoader.getString(computingName, null);
            lastIndex = computingName.lastIndexOf('.');
        }
        return value == null ? ifAbsent : value;
    }

    private PrintStream getPrintStream(String file) {
        if(file.equalsIgnoreCase("System.out"))
            return System.out;
        if(file.equalsIgnoreCase("System.err"))
            return System.err;

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            return new PrintStream(outputStream);
        } catch(FileNotFoundException ignored) {
            System.err.println("Could not open print stream (" + file + ")");
            return System.err;
        }
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }
}
