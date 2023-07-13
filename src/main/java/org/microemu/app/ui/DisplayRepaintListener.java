package org.microemu.app.ui;

import org.microemu.device.MutableImage;

public interface DisplayRepaintListener {

    void repaintInvoked(MutableImage image);

}
