/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.common;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GzipResponseWrapper extends HttpServletResponseWrapper implements Serializable {
	
	private static final long serialVersionUID = 8367490736472222429L;
	
	private int statusCode = SC_OK;
	private final ServletOutputStream servletOutputStream;
	private PrintWriter printWriter;

	public GzipResponseWrapper(HttpServletResponse response, OutputStream outputStream) {
		super(response);
		this.servletOutputStream = new GzipResponseStream(outputStream);
	}
	
	@Override
    public ServletOutputStream getOutputStream() {
		return servletOutputStream;
	}
	
	@Override
    public void setStatus(int code) {
		statusCode = code;
		super.setStatus(code);
	}
	
	@Override
    public void setStatus(int code, String msg) {
		statusCode = code;
		super.setStatus(code);
	}
	
	public int getStatus() {
		return statusCode;
	}
	
	@Override
    public void sendError(int i) throws IOException {
		statusCode = i;
		super.sendError(i);
	}
	
	@Override
    public void sendError(int i, String string) throws IOException {
		statusCode = i;
		super.sendError(i, string);
	}
	
	@Override
    public void sendRedirect(String string) throws IOException {
		statusCode = HttpServletResponse.SC_MOVED_TEMPORARILY;
		super.sendRedirect(string);
	}
	
	@Override
    public void reset() {
		super.reset();
		statusCode = SC_OK;
	}
    
	@Override
    public PrintWriter getWriter() throws IOException {
		if (printWriter == null) {
			printWriter = new PrintWriter(new OutputStreamWriter(servletOutputStream, getCharacterEncoding()), true);
		}
		return printWriter;
	}

	@Override
    public void flushBuffer() throws IOException {
		flush();
	}
    
	public void flush() throws IOException {
		if (printWriter != null) {
			printWriter.flush();
		}
		servletOutputStream.flush();
	}

}