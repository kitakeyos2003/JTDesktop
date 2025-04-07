# J2ME to Java Swing Project Converter  

This project provides a library for converting J2ME (Java 2 Micro Edition) projects to Java Swing projects. With this library, you can easily migrate your existing J2ME applications to Java Swing, allowing you to take advantage of the enhanced capabilities and features provided by Java Swing.  

## Table of Contents  

- [Introduction](#introduction)  
- [Installation](#installation)  
- [Usage](#usage)  
- [Example](#example)  
- [Contributing](#contributing)  
- [License](#license)  

## Introduction  

J2ME is a Java platform designed for resource-constrained devices such as mobile phones and embedded systems. It has been widely used for developing applications for feature phones in the past. However, with the advancements in technology and the popularity of smartphones, many developers are looking to migrate their J2ME projects to more modern platforms.  

The J2ME to Java Swing Project Converter provides a solution to this problem. It allows you to convert your J2ME projects to Java Swing, which is a powerful and feature-rich UI framework for desktop applications. By converting your J2ME project, you can leverage the capabilities of Java Swing and easily adapt your application to run on desktop computers.  

## Installation  

To use the J2ME to Java Swing Project Converter, you can include the library in your Java project using [JitPack](https://jitpack.io/#kitakeyos2003/JTDesktop).  

### Using Maven  

Add the JitPack repository to your `pom.xml`:  

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```  

Then, add the dependency:  

```xml
<dependency>
    <groupId>com.github.kitakeyos2003</groupId>
    <artifactId>JTDesktop</artifactId>
    <version>0.0.4-alpha</version>
</dependency>
```  

### Manual Installation  

Alternatively, you can download the library JAR file from the [releases](https://github.com/kitakeyos2003/JTDesktop/releases) section and add it to your project's classpath.  

## Usage  

Using the J2ME to Java Swing Project Converter is straightforward. You need to follow these steps:  

1. Create a new Java project and add this JTD library.  
2. Create a class `Main` with the method `main`.  
   - Create an instance of class `Application` with parameters `name`, `midlet`, `width`, `height`, `icon`.  
   - Use the `start` method to run the application.  
3. Build and run the program.  

Make sure that your J2ME project follows the standard structure and conventions to ensure a successful conversion process.  

## Example  

Here is an example of how to use the J2ME to Java Swing Project Converter:  

```java
import com.barteo.emulator.app.Application;
import me.kitakeyos.MGOMidlet;

public class Main {
    public static void main(String[] args) {
        Application application = new Application("Gopet", MGOMidlet.class, (short) 400, (short) 300, args, "/icon.png");
        application.start();
    }
}
```  

## Contributing  

Contributions to the J2ME to Java Swing Project Converter are welcome! If you encounter any issues, have suggestions, or would like to contribute new features, please feel free to submit a pull request. Make sure to follow the existing code style and provide clear commit messages for your changes.  

## License  

This project is licensed under the [MIT License](LICENSE). You are free to modify, distribute, and use the code for both personal and commercial purposes. See the [LICENSE](LICENSE) file for more details.  
