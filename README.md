# T2003 Logger

This project is an own implementation of the SLF4J API, a standardized Logger API.
It has many options for configuration which will be listed below.

## 🚩 Table of Contents

<ol>
    <li><a href="#⚡-installation">Installation</a></li>
    <li><a href="#⚙-configuration">Configuration</a></li>
    <li><a href="#🔮-contributing">Contributing</a></li>
</ol>

## ⚡ Installation

This project is published with the Maven Central repository.

### Maven

```xml
<dependency>
    <groupId>com.github.taucher2003</groupId>
    <artifactId>t2003-logger</artifactId>
    <version>VERSION</version>
    <scope>compile</scope>
</dependency>
```

### Gradle

```groovy
dependencies {
    implementation 'com.github.taucher2003:t2003-logger:VERSION'
}
```

## ⚙ Configuration

### Default Settings

| Name          | Description                                   | Possible values                 | Default value |
|---------------|-----------------------------------------------|---------------------------------|---------------|
| logging.level | Sets the minimum level which should be logged | TRACE, DEBUG, INFO, WARN, ERROR | INFO          |
| logging.printstream | Sets the Print Stream where the logger should print | System.out, System.err | System.err |
| logging.file | Sets a file, where the log output should be written | \<every valid file name> | null |
| logging.file_strip_colors | Removes the colors from the log before written to the file | true, false | true |
| logging.show_date | Sets if the date should be shown in log messages | true, false | true |
| logging.date_format | Set the date format as SimpleDateFormat | \<any valid simple date format> | dd.MM.yyyy HH:mm:ss.SSS|
| logging.show_thread_name | Sets if the thread name should be shown | true, false | true
| logging.thread_name_in_braces | Sets if the thread name should be shown in braces [] | true, false | true |
| logging.thread_name_max_length | Set the maximum length of the thread name. The name gets shortened, if it is to long | \<any valid integer> | 16 |
| logging.use_colors | Sets if the logger should use colors | true, false | true |

### Color Settings

Every color setting has the following options:
- BLACK
- RED
- GREEN
- YELLOW
- BLUE
- PURPLE
- CYAN
- WHITE
- (RESET)

| Name                      | Description                  | Default value |
|---------------------------|------------------------------|---------------|
| logging.color.date        | The color of the Date        | WHITE         |
| logging.color.level.error | The color of ERROR Level     | RED           |
| logging.color.level.warn  | The color of WARN Level      | YELLOW        |
| logging.color.level.info  | The color of INFO Level      | GREEN         |
| logging.color.level.debug | The color of DEBUG Level     | BLUE          |
| logging.color.level.trace | The color of TRACE Level     | BLUE          |
| logging.color.thread      | The color of the Thread name | PURPLE        |
| logging.color.name        | The color of the Class name  | CYAN          |
| logging.color.message     | The color of the log message | WHITE         |

## 🔮 Contributing

Contributions are very welcome. Feel free to open issues, fork the project and create pull requests.
Contribution guidelines maybe follow later.
Every contribution is valuable, so don't be a dick.