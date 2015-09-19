package com.rzrk.common;

public class CheckInfo {
	private boolean error;
	private StringBuffer errorSb;
	private StringBuffer infoSb;
	
	public CheckInfo() {
		errorSb = new StringBuffer();
		infoSb = new StringBuffer();
	}
	
	public boolean isError() {
		return error;
	}


	public void addError(String errorStr){
		error = true;
		errorSb.append(errorStr).append("\n");
	}
	
	public void addInfo(String infoStr){
		infoSb.append(infoStr).append("\n");
	}
	
	public String errorMessage(){
		return errorSb.toString();
	}
	
	public String infoMessage(){
		return infoSb.toString();
	}
	
}
