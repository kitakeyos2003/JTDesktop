package com.barteo.emulator.app;

import org.microemu.app.Main;

import javax.microedition.midlet.MIDlet;

public class Application {

    private final String name;
    private final Class<? extends MIDlet> midlet;
    private final short width;
    private final short height;
    private final String[] args;
    private final String icon;
    private String manifest;

    public Application(String name, Class<? extends MIDlet> midlet, short width, short height, String icon, String[] args) {
        this.name = name;
        this.midlet = midlet;
        this.width = width;
        this.height = height;
        this.icon = icon;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public Class<? extends MIDlet> getMidlet() {
        return midlet;
    }

    public short getWidth() {
        return width;
    }

    public short getHeight() {
        return height;
    }

    public String[] getArgs() {
        return args;
    }

    public String getIcon() {
        return icon;
    }
    public String getManifest() {
        return manifest;
    }
    public void setManifest(String path) {
        this.manifest = path;
    }

    public void start() {
        Main.startApp(this);
    }
}
