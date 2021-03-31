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

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoggerConfiguration {

    private static final String PREFIX = "logging.";
    private static final String LOG_LEVEL = PREFIX + "level";
    private static final String PRINT_STREAM = PREFIX + "printstream";
    private static final String FILE = PREFIX + "file";
    private static final String STRIP_COLORS_FOR_FILE = PREFIX + "file_strip_colors";
    private static final String SHOW_DATE = PREFIX + "show_date";
    private static final String DATE_FORMAT = PREFIX + "date_format";
    private static final String SHOW_THREAD_NAME = PREFIX + "show_thread_name";
    private static final String THREAD_IN_BRACES = PREFIX + "thread_name_in_braces";
    private static final String THREAD_NAME_MAX_LENGTH = PREFIX + "thread_name_max_length";
    private static final String USE_COLORS = PREFIX + "use_colors";
    static final String COLOR = PREFIX + "color.";

    private static final Map<String, Path> PATHS = new ConcurrentHashMap<>();

    private Level logLevel;
    final String name;
    final PrintStream printStream;
    final Path path;
    final boolean stripColorsForFile;
    final boolean showDate;
    final SimpleDateFormat dateFormat;
    final boolean showThread;
    final boolean threadInBraces;
    final int threadMaxLength;
    final boolean useColors;
    final Map<String, AnsiColor> colors = new HashMap<>();

    public LoggerConfiguration(String name) {
        this.name = name;
        this.logLevel = getEnum(LOG_LEVEL, Level.INFO);
        this.printStream = getPrintStream(computeCustomSettings(PRINT_STREAM, "System.err"));
        this.path = getPath(computeCustomSettings(FILE, null));
        this.stripColorsForFile = Boolean.parseBoolean(computeCustomSettings(STRIP_COLORS_FOR_FILE, "true"));
        this.showDate = Boolean.parseBoolean(computeCustomSettings(SHOW_DATE, "true"));
        this.dateFormat = new SimpleDateFormat(computeCustomSettings(DATE_FORMAT, "dd.MM.yyyy HH:mm:ss.SSS"));
        this.showThread = Boolean.parseBoolean(computeCustomSettings(SHOW_THREAD_NAME, "true"));
        this.threadInBraces = Boolean.parseBoolean(computeCustomSettings(THREAD_IN_BRACES, "true"));
        this.threadMaxLength = getInt(THREAD_NAME_MAX_LENGTH, 16);
        this.useColors = Boolean.parseBoolean(computeCustomSettings(USE_COLORS, "true"));

        loadCustomColors();
    }

    private void loadCustomColors() {
        colors.put(COLOR + "date", getEnum(COLOR + "date", AnsiColor.WHITE));
        colors.put(COLOR + "level.error", getEnum(COLOR + "level.error", AnsiColor.RED));
        colors.put(COLOR + "level.warn", getEnum(COLOR + "level.warn", AnsiColor.YELLOW));
        colors.put(COLOR + "level.info", getEnum(COLOR + "level.info", AnsiColor.GREEN));
        colors.put(COLOR + "level.debug", getEnum(COLOR + "level.debug", AnsiColor.BLUE));
        colors.put(COLOR + "level.trace", getEnum(COLOR + "level.trace", AnsiColor.BLUE));
        colors.put(COLOR + "thread", getEnum(COLOR + "thread", AnsiColor.PURPLE));
        colors.put(COLOR + "name", getEnum(COLOR + "name", AnsiColor.CYAN));
        colors.put(COLOR + "message", getEnum(COLOR + "message", AnsiColor.WHITE));
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

    private PrintStream getPrintStream(String stream) {
        if(stream == null || stream.equalsIgnoreCase("null"))
            return null;
        if(stream.equalsIgnoreCase("System.out"))
            return System.out;
        if(stream.equalsIgnoreCase("System.err"))
            return System.err;
        return null;
    }

    private Path getPath(String fileName) {
        if(fileName == null || fileName.equalsIgnoreCase("null"))
            return null;

        return PATHS.computeIfAbsent(fileName, ignored -> {
            try {
                File file = new File(fileName);
                if(!file.exists())
                    file.createNewFile();
                return file.toPath();
            } catch (IOException exception) {
                Util.report("Failed to open File Output Stream", exception);
            }
            return null;
        });
    }

    private <T extends Enum<T>> T getEnum(String key, T defaultValue) {
        String value = computeCustomSettings(key, defaultValue.name());
        try {
            return Enum.valueOf(defaultValue.getDeclaringClass(), value);
        }catch(IllegalArgumentException | EnumConstantNotPresentException exception) {
            Util.report("Enum choice [" + key + "] was set to an invalid value", exception);
        }
        return defaultValue;
    }

    private int getInt(String key, int defaultValue) {
        String value = computeCustomSettings(key, null);
        if(value == null)
            return defaultValue;
        try {
            return Integer.parseInt(value);
        }catch(NumberFormatException exception) {
            Util.report("Integer choice [" + key + "] was set to an invalid value", exception);
        }
        return defaultValue;
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

    public Path getPath() {
        return path;
    }

    public boolean isStripColorsForFile() {
        return stripColorsForFile;
    }
}
