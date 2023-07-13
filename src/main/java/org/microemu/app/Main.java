/**
 *  MicroEmulator
 *  Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
 *
 * It is licensed under the following two licenses as alternatives: 1. GNU
 * Lesser General Public License (the "LGPL") version 2.1 or any newer version
 * 2. Apache License (the "AL") Version 2.0
 *
 * You may not use this file except in compliance with at least one of the above
 * two licenses.
 *
 * You may obtain a copy of the LGPL at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt
 *
 * You may obtain a copy of the AL at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the LGPL
 * or the AL for the specific language governing permissions and limitations.
 */
package org.microemu.app;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.microedition.midlet.MIDletStateChangeException;
import javax.swing.JFrame;

import com.barteo.emulator.app.Application;
import org.microemu.DisplayComponent;
import org.microemu.EmulatorContext;
import org.microemu.MIDletBridge;
import org.microemu.app.capture.AnimatedGifEncoder;
import org.microemu.app.ui.Message;
import org.microemu.app.ui.swing.DropTransferHandler;
import org.microemu.app.ui.swing.SwingDeviceComponent;
import org.microemu.app.ui.swing.SwingErrorMessageDialogPanel;
import org.microemu.app.ui.swing.SwingSelectDevicePanel;
import org.microemu.app.util.DeviceEntry;
import org.microemu.device.DeviceDisplay;
import org.microemu.device.DeviceFactory;
import org.microemu.device.FontManager;
import org.microemu.device.InputMethod;
import org.microemu.device.impl.DeviceDisplayImpl;
import org.microemu.device.impl.Rectangle;
import org.microemu.device.j2se.J2SEDevice;
import org.microemu.device.j2se.J2SEDeviceDisplay;
import org.microemu.device.j2se.J2SEFontManager;
import org.microemu.device.j2se.J2SEInputMethod;
import org.microemu.log.Logger;
import org.microemu.log.QueueAppender;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    protected Common common;

    protected SwingSelectDevicePanel selectDevicePanel = null;

    private SwingDeviceComponent devicePanel;

    private final QueueAppender logQueueAppender;

    private AnimatedGifEncoder encoder;

    protected EmulatorContext emulatorContext = new EmulatorContext() {

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
            return MIDletBridge.getCurrentMIDlet().getClass().getResourceAsStream(name);
        }

        public boolean platformRequest(final String URL) {
            new Thread(new Runnable() {
                public void run() {
                    Message.info("MIDlet requests that the device handle the following URL: " + URL);
                }
            }).start();

            return false;
        }
    };

    private final ActionListener menuExitListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            synchronized (Main.this) {
                if (encoder != null) {
                    encoder.finish();
                    encoder = null;
                }
            }
            Config.setWindow("main", new Rectangle(Main.this.getX(), Main.this.getY(), Main.this.getWidth(), Main.this
                    .getHeight()), true);

            System.exit(0);
        }
    };

    private final WindowAdapter windowListener = new WindowAdapter() {
        public void windowClosing(WindowEvent ev) {
            menuExitListener.actionPerformed(null);
        }

        public void windowIconified(WindowEvent ev) {
            MIDletBridge.getMIDletAccess(MIDletBridge.getCurrentMIDlet()).pauseApp();
        }

        public void windowDeiconified(WindowEvent ev) {
            try {
                MIDletBridge.getMIDletAccess(MIDletBridge.getCurrentMIDlet()).startApp();
            } catch (MIDletStateChangeException ex) {
                System.err.println(ex);
            }
        }
    };

    public Main() {
        this(null);
    }

    public Main(DeviceEntry defaultDevice) {

        this.logQueueAppender = new QueueAppender(1024);
        Logger.addAppender(logQueueAppender);

        addWindowListener(windowListener);
        setResizable(false);
        Config.loadConfig(defaultDevice, emulatorContext);
        Logger.setLocationEnabled(Config.isLogConsoleLocationEnabled());

        Rectangle window = Config.getWindow("main", new Rectangle(0, 0, 160, 120));
        this.setLocation(window.x, window.y);

        getContentPane().add(createContents(getContentPane()), "Center");

        selectDevicePanel = new SwingSelectDevicePanel(emulatorContext);

        this.common = new Common(emulatorContext);
        this.common.loadImplementationsFromConfig();
        Message.addListener(new SwingErrorMessageDialogPanel(this));

        devicePanel.setTransferHandler(new DropTransferHandler());
    }

    public static void startApp(Application application) {
        Main app = new Main();
        List params = new ArrayList();
        StringBuffer debugArgs = new StringBuffer();
        String[] args = application.getArgs();
        for (int i = 0; i < args.length; i++) {
            params.add(args[i]);
            if (debugArgs.length() != 0) {
                debugArgs.append(", ");
            }
            debugArgs.append("[").append(args[i]).append("]");
        }
        if (params.contains("--headless")) {
            Headless.main(args);
            return;
        }

        app.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource(application.getIcon())));
        if (app.common.initParams(params, app.selectDevicePanel.getSelectedDeviceEntry(), J2SEDevice.class)) {
            DeviceDisplayImpl deviceDisplay = (DeviceDisplayImpl) DeviceFactory.getDevice().getDeviceDisplay();
            if (deviceDisplay.isResizable()) {
                deviceDisplay.setDisplayRectangle(new Rectangle(0, 0, application.getWidth(), application.getHeight()));
            }
        }
        app.updateDevice();
        app.setTitle(application.getName());
        app.validate();
        app.setVisible(true);
        app.common.startApp(application.getName(), application.getMidlet());
    }

    protected Component createContents(Container parent) {
        devicePanel = new SwingDeviceComponent();
        devicePanel.addKeyListener(devicePanel);
        addKeyListener(devicePanel);

        return devicePanel;
    }

    protected void updateDevice() {
        devicePanel.init();
        pack();
        devicePanel.requestFocus();
    }

    private abstract class CountTimerTask extends TimerTask {

        protected int counter;

        public CountTimerTask(int counter) {
            this.counter = counter;
        }

    }

}
