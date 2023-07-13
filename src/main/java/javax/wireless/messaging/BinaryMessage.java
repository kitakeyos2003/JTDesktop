/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.wireless.messaging;

public interface BinaryMessage
        extends Message {

    public byte[] getPayloadData();

    public void setPayloadData(byte[] var1);
}
