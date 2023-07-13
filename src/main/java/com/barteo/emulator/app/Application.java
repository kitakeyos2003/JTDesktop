package com.barteo.emulator.app;

import org.microemu.app.Main;

import javax.microedition.midlet.MIDlet;

public class Application {

    private String name;
    private Class<? extends MIDlet> midlet;
    private short width, height;
    private String args[];
    private String icon;

    public Application(String name, Class<? extends MIDlet> midlet, short width, short height, String[] args, String icon) {
        this.name = name;
        this.midlet = midlet;
        this.width = width;
        this.height = height;
        this.args = args;
        this.icon = icon;
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

    public void start() {
        Main.startApp(this);
    }
}