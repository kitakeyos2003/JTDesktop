/*
 *  MicroEmulator
 *  Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
 *
 *  It is licensed under the following two licenses as alternatives:
 *    1. GNU Lesser General Public License (the "LGPL") version 2.1 or any newer version
 *    2. Apache License (the "AL") Version 2.0
 *
 *  You may not use this file except in compliance with at least one of
 *  the above two licenses.
 *
 *  You may obtain a copy of the LGPL at
 *      http://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt
 *
 *  You may obtain a copy of the AL at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the LGPL or the AL for the specific language governing permissions and
 *  limitations.
 *
 *  Contributor(s):
 *    daniel(at)angrymachine.com.ar
 */
package org.microemu.applet;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Vector;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.swing.Timer;

import org.microemu.DisplayComponent;
import org.microemu.EmulatorContext;
import org.microemu.MIDletBridge;
import org.microemu.MIDletContext;
import org.microemu.MicroEmulator;
import org.microemu.RecordStoreManager;
import org.microemu.app.launcher.Launcher;
import org.microemu.app.ui.swing.SwingDeviceComponent;
import org.microemu.app.util.MIDletResourceLoader;
import org.microemu.app.util.MIDletSystemProperties;
import org.microemu.device.DeviceDisplay;
import org.microemu.device.DeviceFactory;
import org.microemu.device.FontManager;
import org.microemu.device.InputMethod;
import org.microemu.device.impl.DeviceDisplayImpl;
import org.microemu.device.impl.DeviceImpl;
import org.microemu.device.j2se.J2SEDevice;
import org.microemu.device.j2se.J2SEDeviceDisplay;
import org.microemu.device.j2se.J2SEFontManager;
import org.microemu.device.j2se.J2SEInputMethod;
import org.microemu.device.ui.EventDispatcher;
import org.microemu.log.Logger;
import org.microemu.util.JadMidletEntry;
import org.microemu.util.JadProperties;
import org.microemu.util.MemoryRecordStoreManager;

public class Main extends Applet implements MicroEmulator {

    private static final long serialVersionUID = 1L;

    private final MIDlet midlet = null;

    private RecordStoreManager recordStoreManager;

    private final JadProperties manifest = new JadProperties();

    private final SwingDeviceComponent devicePanel;

    /**
     * Host name accessible by MIDlet
     */
    private String accessibleHost;

    private final EmulatorContext emulatorContext = new EmulatorContext() {
        private final InputMethod inputMethod = new J2SEInputMethod();

        private final DeviceDisplay deviceDisplay = new J2SEDeviceDisplay(this);

        private final FontManager fontManager = new J2SEFontManager();

        public DisplayComponent getDisplayComponent() {
            return devicePanel.getDisplayComponent();
        }

        public InputMethod getDeviceInputMethod() {
            return inputMethod;
        }

        public DeviceDisplay getDeviceDisplay() {
            return deviceDisplay;
        }

        public FontManager getDeviceFontManager() {
            return fontManager;
        }

        public InputStream getResourceAsStream(String name) {
            return getClass().getResourceAsStream(name);
        }

        public boolean platformRequest(String url) {
            try {
                getAppletContext().showDocument(new URL(url), "mini");
            } catch (Exception e) {
            }
            return false;
        }
    };

    public Main() {
        devicePanel = new SwingDeviceComponent();
        devicePanel.addKeyListener(devicePanel);
    }

    public void start() {
        devicePanel.requestFocus();

        new Thread("midlet_starter") {
            public void run() {
                try {
                    MIDletBridge.getMIDletAccess(midlet).startApp();
                } catch (MIDletStateChangeException ex) {
                    System.err.println(ex);
                }
            }
        }.start();

        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                devicePanel.requestFocus();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void stop() {
        MIDletBridge.getMIDletAccess(midlet).pauseApp();
    }

    public void destroy() {
        try {
            MIDletBridge.getMIDletAccess(midlet).destroyApp(true);
        } catch (MIDletStateChangeException ex) {
            System.err.println(ex);
        }
    }

    public RecordStoreManager getRecordStoreManager() {
        return recordStoreManager;
    }

    public String getAppProperty(String key) {
        if (key.equals("applet")) {
            return "yes";
        }

        String value = null;
        if (key.equals("microedition.platform")) {
            value = "MicroEmulator";
        } else if (key.equals("microedition.profiles")) {
            value = "MIDP-2.0";
        } else if (key.equals("microedition.configuration")) {
            value = "CLDC-1.0";
        } else if (key.equals("microedition.locale")) {
            value = Locale.getDefault().getLanguage();
        } else if (key.equals("microedition.encoding")) {
            value = System.getProperty("file.encoding");
        } else if (key.equals("microemu.applet")) {
            value = "true";
        } else if (key.equals("microemu.accessible.host")) {
            value = accessibleHost;
        } else if (getParameter(key) != null) {
            value = getParameter(key);
        } else {
            value = manifest.getProperty(key);
        }

        return value;
    }

    public InputStream getResourceAsStream(String name) {
        return emulatorContext.getResourceAsStream(name);
    }

    public int checkPermission(String permission) {
        // TODO

        return 0;
    }

    public boolean platformRequest(String url) {
        return emulatorContext.platformRequest(url);
    }

    public void notifyDestroyed(MIDletContext midletContext) {
    }

    public void destroyMIDletContext(MIDletContext midletContext) {
    }

    public Launcher getLauncher() {
        return null;
    }

    public String getAppletInfo() {
        return "Title: MicroEmulator \nAuthor: Bartek Teodorczyk, 2001";
    }

    public String[][] getParameterInfo() {
        String[][] info = {{"midlet", "MIDlet class name", "The MIDlet class name. This field is mandatory."},};

        return info;
    }

}
