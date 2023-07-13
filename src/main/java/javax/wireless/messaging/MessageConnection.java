/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.wireless.messaging;

import javax.microedition.io.Connection;

public interface MessageConnection
        extends Connection {

    public static final String BINARY_MESSAGE = "binary";
    public static final String MULTIPART_MESSAGE = "multipart";
    public static final String TEXT_MESSAGE = "text";

    public Message newMessage(String var1);

    public Message newMessage(String var1, String var2);

    public int numberOfSegments(Message var1);

    public Message receive();

    public void send(Message var1);

    public void setMessageListener(MessageListener var1);
}
