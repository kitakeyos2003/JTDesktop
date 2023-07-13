/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.wireless.messaging;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MessagePart {

    private final String id;
    private final String location;
    private final String encoding;
    private final String mimetype;
    private byte[] content;

    public MessagePart(byte[] contents, int offset, int length, String mimeType, String contentId, String contentLocation, String enc) {
        this.id = contentId;
        this.location = contentLocation;
        this.encoding = enc;
        this.mimetype = mimeType;
        this.content = contents;
    }

    public MessagePart(byte[] contents, String mimeType, String contentId, String contentLocation, String enc) {
        this.id = contentId;
        this.location = contentLocation;
        this.encoding = enc;
        this.mimetype = mimeType;
        this.content = contents;
    }

    public MessagePart(InputStream is, String mimeType, String contentId, String contentLocation, String enc) {
        this.id = contentId;
        this.location = contentLocation;
        this.encoding = enc;
        this.mimetype = mimeType;
        this.content = new byte[32768];
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int count = 0;
        while (count != -1) {
            try {
                count = is.read(this.content, 0, this.content.length);
                buffer.write(this.content, 0, count);
            } catch (Exception e) {
                count = -1;
            }
        }
        this.content = buffer.toByteArray();
    }

    public byte[] getContent() {
        return this.content;
    }

    public InputStream getContentAsStream() {
        return new ByteArrayInputStream(this.content);
    }

    public String getContentID() {
        return this.id;
    }

    public String getContentLocation() {
        return this.location;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public int getLength() {
        return this.content.length;
    }

    public String getMIMEType() {
        return this.mimetype;
    }
}
