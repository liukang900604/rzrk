/*
 * *
 *  * Project Name: rzrk Web
 *  * Confidential and Proprietary
 *  * Copyright (C) 2011 By
 *  * rzrk Company
 *  * All Rights Reserved
 *
 */

package com.rzrk;

/**
 * Created with IntelliJ IDEA.
 * User: Ypu
 * Date: 12-5-24
 * Time: 下午6:46
 * To change this template use File | Settings | File Templates.
 */
public class RzrkException  extends Exception {

    public RzrkException() {
        super();
    }
    public RzrkException(String msg) {
        super(msg);
    }
    public RzrkException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public RzrkException(Throwable cause) {
        super(cause);
    }

}
