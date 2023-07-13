/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.wireless.messaging;

public interface MultipartMessage
        extends Message {

    boolean addAddress(String var1, String var2);

    void addMessagePart(MessagePart var1);

    @Override
    String getAddress();

    String[] getAddresses(String var1);

    String getHeader(String var1);

    MessagePart getMessagePart(String var1);

    MessagePart[] getMessageParts();

    String getStartContentId();

    String getSubject();

    boolean removeAddress(String var1, String var2);

    void removeAddresses();

    void removeAddresses(String var1);

    boolean removeMessagePart(MessagePart var1);

    boolean removeMessagePartId(String var1);

    boolean removeMessagePartLocation(String var1);

    @Override
    void setAddress(String var1);

    void setHeader(String var1, String var2);

    void setStartContentId(String var1);

    void setSubject(String var1);
}
