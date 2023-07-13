/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.wireless.messaging;

import java.util.Date;

public interface Message {

    String getAddress();

    Date getTimestamp();

    void setAddress(String var1);
}
