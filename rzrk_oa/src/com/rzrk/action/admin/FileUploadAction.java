/**
\ * Project Name: Unicorn Online Shopping Center
 * Confidential and Proprietary
 * Copyright (C) 2011 By
 * Unicorn Development Company
 * All Rights Reserved
 */

package com.rzrk.action.admin;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.util.ImageUtil;
import com.rzrk.util.OfficeToHtmlUtil;
import com.rzrk.util.SettingUtil;

/**
 * 文件上传
 * 
 * @author kang.liu
 */
public class FileUploadAction extends BaseAdminAction {

	private static final long serialVersionUID = -4041917848708433121L;

	private File[] file;              //文件  
    private String[] fileFileName;    //文件名   
    private String[] filePath;        //文件路径
    private String downloadFilePath;  //文件下载路径
    private String downloadFileName;  //文件下载名称
    private InputStream inputStream; 
    
    /**
     * 文件上传
     * @return
     */
	public String fileUpload() {
		 Map<String,Object> map = new HashMap<String,Object>(); //json数据集合
		String path = SettingUtil.getSetting().getImageUploadRealPath();
		File file = new File(path); // 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()) {
			file.mkdir();
		}
		try {
			if (this.file != null) {
				File f[] = this.getFile();
				filePath = new String[f.length];
				for (int i = 0; i < f.length; i++) {
					String fileName = java.util.UUID.randomUUID().toString(); // 采用时间+UUID的方式随即命名
					String name = fileName + fileFileName[i].substring(fileFileName[i].lastIndexOf(".")); // 保存在硬盘中的文件名
					filePath[i] = ImageUtil.copyCommonFile(getServletContext(), f[i], name);
				}
				//JSONObject object = new JSONObject();
				if(fileFileName != null && fileFileName.length > 0){//文件名不为空
					map.put("fileFileName", fileFileName);
				}
				if(filePath != null && filePath.length > 0){//文件路径不为空
					map.put("filePath", filePath);
				}
				map.put("statu", Status.success);
			}
		} catch (Exception e) {
			return ajax(Status.error, "文件上传失败！");
		}
		return ajax(map);	
	}
	
	
	/**
	 * 文件下载
	 * @return
	 */
	@InputConfig(resultName = "error")
	public void downloadFile() {
		String path = downloadFilePath;//下载文件路径
		String fileName = downloadFileName;//下载文件名称
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			// path是指欲下载的文件的路径。
			File file = new File(this.getServletContext().getRealPath("/")+path.trim());
			// 取得文件名。
			if(fileName == null){
				 fileName = file.getName();
			}else{
				//fileName = new String(fileName.getBytes("ISO-8859-1"),"UTF-8").trim();
			}
			
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(this.getServletContext().getRealPath("/")+path.trim()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			String filenameString = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
			response.addHeader("Content-Disposition", "attachment;filename="+ filenameString);
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response
					.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 文件删除
	 * @return
	 */
	public String deleteloadFile(){
		String path = downloadFilePath;//服务器文件路径
		// path是指欲下载的文件的路径。
		File file = new File(this.getServletContext().getRealPath("/")+path.trim());
		try{
			//判断是否为文件 且存在
			if(file.exists() && file.isFile()){
				file.delete();
				return ajax(Status.success, "文件删除成功！");
			}
			return ajax(Status.error, "请选择文件类型！");
		}catch(Exception e){
			return ajax(Status.error, "文件删除失败！");
		}
	}
	
	/**
	 * 预览文件
	 * @return
	 */
	@InputConfig(resultName = "error")
	public String showFile(){
		String path = downloadFilePath;//服务器文件路径
		String fileName = downloadFileName;//下载文件名称
		String tempPath = ServletActionContext.getServletContext().getRealPath("/upload");//临时文件路径
		// path是指欲下载的文件的路径。
		File file = new File(this.getServletContext().getRealPath("/")+path.trim());
		String htmlName = null;
		try{
			if(isImage(file)){//判断是不是图片
				this.getRequest().setAttribute("imgUrl", this.getServletContext().getRealPath("/")+path.trim());
			}else{
				htmlName = OfficeToHtmlUtil.toHtmlString(file, tempPath);
			}
		}catch(Exception e){
			htmlName = "";
		}
		this.getRequest().setAttribute("htmlName", htmlName);
		return "show";
	}
	
	
	/**
	 * 判断文件是不是图片
	 * @param file
	 * @return
	 */
	public static final boolean isImage(File file){  
	 boolean flag = false;  
	 BufferedImage bufreader = null;
	 
	 try{
		  bufreader = ImageIO.read(file);
		  int width = bufreader.getWidth();
		  int height = bufreader.getHeight();
		  if(width == 0 || height == 0){
			 flag = false;
		  }else{
			 flag = true;
		  }
		
	 }catch (Exception e) {
		 flag = false;
	 }
		return flag;
 }
	
	public File[] getFile() {
		return file;
	}
	
	public void setFile(File[] file) {
		this.file = file;
	}
	
	public String[] getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	
	public String[] getFilePath() {
		return filePath;
	}

	public void setFilePath(String[] filePath) {
		this.filePath = filePath;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getDownloadFilePath() {
		return downloadFilePath;
	}

	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath;
	}


	public String getDownloadFileName() {
		return downloadFileName;
	}


	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
}
