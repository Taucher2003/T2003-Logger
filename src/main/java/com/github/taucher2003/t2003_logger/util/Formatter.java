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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {

    private final String name;
    private final LoggerConfiguration configuration;

    public Formatter(String name, LoggerConfiguration configuration) {
        if(configuration == null)
            throw new NullPointerException("LoggerConfiguration can not be null");
        this.name = name;
        this.configuration = configuration;
    }

    public String format(Level level, Throwable throwable) {
        return format(level, "An unexpected exception occurred");
    }

    public String format(Level level, String message) {
        StringBuilder builder = new StringBuilder();

        if(configuration.showDate)
            builder.append(formatDate()).append("  ");

        builder.append(colorizeLevel(level)).append(" ");

        if(configuration.showThread)
            builder.append(formatThread()).append(" ");

        builder.append(formatName()).append(" - ");

        builder.append(formatMessage(message));

        return builder.toString();
    }

    private String formatDate() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
        return Colorizer.white(format.format(date));
    }

    private String colorizeLevel(Level level) {
        switch (level) {
            case ERROR:
                return Colorizer.red(level.name());
            case WARN:
                return Colorizer.yellow(level.name());
            case INFO:
                return Colorizer.green(level.name());
            case DEBUG:
            case TRACE:
                return Colorizer.blue(level.name());
        }
        return "";
    }

    private String formatThread() {
        String name = "";
        if(configuration.threadInBraces)
            name = "[";

        name += Thread.currentThread().getName();

        if(configuration.threadInBraces)
            name += "]";

        return Colorizer.white(name);
    }

    private String formatName() {
        return Colorizer.cyan(name);
    }

    private String formatMessage(String message) {
        return Colorizer.white(message);
    }

    public static String format(String message, Object... objects) {
        String result = message;
        for(Object object : objects) {
            result = result.replaceFirst("\\{}", String.valueOf(object));
        }
        return result;
    }
}

