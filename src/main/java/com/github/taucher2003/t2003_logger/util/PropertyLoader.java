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

import org.slf4j.helpers.Util;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

public class PropertyLoader {

    private static final String LOGGER_CONFIG_FILE = "logger_config.properties";
    private static final Properties CONFIG = new Properties();

    static {
        InputStream config = AccessController.doPrivileged((PrivilegedAction<InputStream>) () -> {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if(loader == null) {
                return ClassLoader.getSystemResourceAsStream(LOGGER_CONFIG_FILE);
            }
            return loader.getResourceAsStream(LOGGER_CONFIG_FILE);
        });
        if(config != null) {
            try {
                CONFIG.load(config);
            } catch (IOException exception) {
                Util.report("Failed to load configuration", exception);
            }
        }
    }

    public static String getString(String name) {
        String property = null;

        try {
            property = System.getProperty(name);
        }catch(SecurityException ignored) {}

        return property == null ? CONFIG.getProperty(name) : property;
    }

    public static String getString(String name, String defaultValue) {
        String value = getString(name);
        return value == null ? defaultValue : value;
    }

    public static boolean getBoolean(String name, boolean defaultValue) {
        String value = getString(name);
        return value == null ? defaultValue : value.equals("true");
    }

    public static <T extends Enum<T>> T getEnum(String name, T defaultValue, Class<T> enumClass) {
        String value = getString(name);
        try {
            return Enum.valueOf(enumClass, value);
        }catch(EnumConstantNotPresentException ignored) {
            return defaultValue;
        }
    }
}
