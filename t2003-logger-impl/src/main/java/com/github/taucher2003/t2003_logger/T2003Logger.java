/*
 * Copyright 2022 Niklas van Schrick and the T2003-Logger contributors
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

import com.github.taucher2003.t2003_logger.util.AnsiColor;
import com.github.taucher2003.t2003_logger.util.Formatter;
import com.github.taucher2003.t2003_logger.util.LoggerConfiguration;
import org.slf4j.event.Level;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.Util;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

import static org.slf4j.event.Level.DEBUG;
import static org.slf4j.event.Level.ERROR;
import static org.slf4j.event.Level.INFO;
import static org.slf4j.event.Level.TRACE;
import static org.slf4j.event.Level.WARN;

public class T2003Logger extends MarkerIgnoringBase {

    private static final long serialVersionUID = -632788891211436180L;

    private final LoggerConfiguration configuration;
    private final Formatter formatter;

    public T2003Logger(String name) {
        this.configuration = new LoggerConfiguration(name);
        this.formatter = new Formatter(name, configuration);
    }

    public void log(Level level, String message) {
        log(level, new FormattingTuple(message));
    }

    public void log(Level level, String message, Throwable throwable) {
        log(level, new FormattingTuple(message, null, throwable));
    }

    public void log(Level level, FormattingTuple formattingTuple) {
        String format = formatter.format(level, formattingTuple.getMessage());
        log(configuration.getPrintStream(), format, formattingTuple.getThrowable());
        if(configuration.getPath() != null)
            log(configuration.getPath(), format, formattingTuple.getThrowable());
    }

    private void log(PrintStream stream, String formatted, Throwable t) {
        stream.println(formatted);
        if(t != null)
            t.printStackTrace(stream);
        stream.flush();
    }

    private void log(Path path, String formatted, Throwable t) {
        StringBuilder result = configuration.isStripColorsForFile()
                ? new StringBuilder(AnsiColor.stripColors(formatted))
                : new StringBuilder(formatted);

        if(t != null)
            appendStackTrace(result, t);

        result.append("\n");
        try {
            Files.write(path, result.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException exception) {
            Util.report("Failed to write log to file", exception);
        }
    }

    private void appendStackTrace(StringBuilder builder, Throwable throwable) {
        builder.append("\n");
        Set<Throwable> throwables = Collections.newSetFromMap(new IdentityHashMap<>());
        throwables.add(throwable);
        builder.append(throwable.getClass().getName())
                .append(": ")
                .append(throwable.getLocalizedMessage())
                .append("\n");

        for(StackTraceElement ste : throwable.getStackTrace())
            builder.append("\tat ").append(ste.toString()).append("\n");

        for(Throwable suppressed : throwable.getSuppressed())
            appendEnclosedStackTrace(builder, suppressed, SUPPRESSED_CAPTION, "\t", throwables);

        Throwable cause = throwable.getCause();
        if(cause != null)
            appendEnclosedStackTrace(builder, cause, CAUSED_CAPTION, "", throwables);
    }

    private final String SUPPRESSED_CAPTION = "Suppressed: ";
    private final String CAUSED_CAPTION = "Caused by: ";

    private void appendEnclosedStackTrace(StringBuilder builder, Throwable throwable, String caption, String indent, Set<Throwable> throwables) {
        if(throwables.contains(throwable)) {
            builder.append(caption).append("[CIRCULAR REFERENCE: ").append(throwable).append("]");
        }else {
            throwables.add(throwable);
            builder.append(indent)
                    .append(caption)
                    .append(throwable.getClass().getName())
                    .append(": ")
                    .append(throwable.getLocalizedMessage())
                    .append("\n");

            for(StackTraceElement ste : throwable.getStackTrace())
                builder.append(indent).append("\tat ").append(ste.toString()).append("\n");

            for(Throwable suppressed : throwable.getSuppressed())
                appendEnclosedStackTrace(builder, suppressed, SUPPRESSED_CAPTION, indent + "\t", throwables);

            Throwable cause = throwable.getCause();
            if(cause != null)
                appendEnclosedStackTrace(builder, cause, CAUSED_CAPTION, indent, throwables);
        }
    }

    public boolean isLevelEnabled(Level level) {
        return level.toInt() >= configuration.getLogLevel().toInt();
    }

    public Level getLevel() {
        return configuration.getLogLevel();
    }

    public void setLevel(Level level) {
        configuration.setLogLevel(level);
    }

    @Override
    public boolean isTraceEnabled() {
        return isLevelEnabled(TRACE);
    }

    @Override
    public void trace(String msg) {
        if (isTraceEnabled())
            log(TRACE, msg);
    }

    @Override
    public void trace(String format, Object arg) {
        if (isTraceEnabled())
            log(TRACE, MessageFormatter.format(format, arg));
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        if (isTraceEnabled())
            log(TRACE, MessageFormatter.format(format, arg1, arg2));
    }

    @Override
    public void trace(String format, Object... arguments) {
        if (isTraceEnabled())
            log(TRACE, MessageFormatter.arrayFormat(format, arguments));
    }

    @Override
    public void trace(String msg, Throwable t) {
        if (isTraceEnabled())
            log(TRACE, msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return isLevelEnabled(DEBUG);
    }

    @Override
    public void debug(String msg) {
        if (isDebugEnabled())
            log(DEBUG, msg);
    }

    @Override
    public void debug(String format, Object arg) {
        if (isDebugEnabled())
            log(DEBUG, MessageFormatter.format(format, arg));
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        if (isDebugEnabled())
            log(DEBUG, MessageFormatter.format(format, arg1, arg2));
    }

    @Override
    public void debug(String format, Object... arguments) {
        if (isDebugEnabled())
            log(DEBUG, MessageFormatter.arrayFormat(format, arguments));
    }

    @Override
    public void debug(String msg, Throwable t) {
        if (isDebugEnabled())
            log(DEBUG, msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return isLevelEnabled(INFO);
    }

    @Override
    public void info(String msg) {
        if (isInfoEnabled())
            log(INFO, msg);
    }

    @Override
    public void info(String format, Object arg) {
        if (isInfoEnabled())
            log(INFO, MessageFormatter.format(format, arg));
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        if (isInfoEnabled())
            log(INFO, MessageFormatter.format(format, arg1, arg2));
    }

    @Override
    public void info(String format, Object... arguments) {
        if (isInfoEnabled())
            log(INFO, MessageFormatter.arrayFormat(format, arguments));
    }

    @Override
    public void info(String msg, Throwable t) {
        if (isInfoEnabled())
            log(INFO, msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return isLevelEnabled(WARN);
    }

    @Override
    public void warn(String msg) {
        if (isWarnEnabled())
            log(WARN, msg);
    }

    @Override
    public void warn(String format, Object arg) {
        if (isWarnEnabled())
            log(WARN, MessageFormatter.format(format, arg));
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        if (isWarnEnabled())
            log(WARN, MessageFormatter.format(format, arg1, arg2));
    }

    @Override
    public void warn(String format, Object... arguments) {
        if (isWarnEnabled())
            log(WARN, MessageFormatter.arrayFormat(format, arguments));
    }

    @Override
    public void warn(String msg, Throwable t) {
        if (isWarnEnabled())
            log(WARN, msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return isLevelEnabled(ERROR);
    }

    @Override
    public void error(String msg) {
        if (isErrorEnabled())
            log(ERROR, msg);
    }

    @Override
    public void error(String format, Object arg) {
        if (isErrorEnabled())
            log(ERROR, MessageFormatter.format(format, arg));
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        if (isErrorEnabled())
            log(ERROR, MessageFormatter.format(format, arg1, arg2));
    }

    @Override
    public void error(String format, Object... arguments) {
        if (isErrorEnabled())
            log(ERROR, MessageFormatter.arrayFormat(format, arguments));
    }

    @Override
    public void error(String msg, Throwable t) {
        if (isErrorEnabled())
            log(ERROR, msg, t);
    }

    public Formatter getFormatter() {
        return formatter;
    }
}
