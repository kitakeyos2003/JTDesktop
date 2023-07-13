/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.wireless.messaging;

import javax.microedition.io.Connection;

public interface MessageConnection
        extends Connection {

    String BINARY_MESSAGE = "binary";
    String MULTIPART_MESSAGE = "multipart";
    String TEXT_MESSAGE = "text";

    Message newMessage(String var1);

    Message newMessage(String var1, String var2);

    int numberOfSegments(Message var1);

    Message receive();

    void send(Message var1);

    void setMessageListener(MessageListener var1);
}
