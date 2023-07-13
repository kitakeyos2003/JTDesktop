/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.wireless.messaging;

public interface TextMessage
        extends Message {

    String getPayloadText();

    void setPayloadText(String var1);
}
