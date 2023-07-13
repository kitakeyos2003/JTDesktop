/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.wireless.messaging;

import java.io.IOException;

public class SizeExceededException
        extends IOException {

    private static final long serialVersionUID = -1995561838805781257L;

    public SizeExceededException(String reason) {
        super(reason);
    }
}
