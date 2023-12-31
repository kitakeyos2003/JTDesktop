/*
 *  MicroEmulator
 *  Copyright (C) 2002-2006 Bartek Teodorczyk <barteo@barteo.net>
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
 */
package com.barteo.emulator.device;

import org.microemu.device.ui.UIFactory;

import com.barteo.emulator.EmulatorContext;

/*
 * @deprecated use org.microemu.device.Device
 */
public class Device extends org.microemu.device.impl.DeviceImpl {

    /**
     * @deprecated
     */
    public void init(EmulatorContext context) {
        super.init(context);
    }

    /**
     * @deprecated
     */
    public void init(EmulatorContext context, String config) {
        super.init(context, config);
    }

    public UIFactory getUIFactory() {
        return null;
    }

}
