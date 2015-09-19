package com.rzrk.util;

import java.io.BufferedReader;
import java.io.IOException;   
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.*;   
import org.apache.commons.httpclient.methods.*;  

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.deserializer.JSONObjectDeserializer;
import com.rzrk.exception.PersonalException;
import com.rzrk.util.JsonUtil;
import com.rzrk.vo.UserInModelPo;

public class HttpClientUtil {
	  
	/**  
	 *最简单的HTTP客户端,用来演示通过GET或者POST方式访问某个页面 
	  *@authorLiudong 
	*/  
	  
	public static void main(String[] args) {  
		
		
		String res = sendPost("http://www.gegregrrgergergregregre.com", "");
		System.out.println(res);
		
//		String url = "http://127.0.0.1:8080/modeltest/getUsersByOA.action";
//		String param = "pageNum=1&numPerPage=50&_=1439892513878";
//		
//		String urltest = "http://localhost:8080/admin/contract!save.action";
//		String paramtest = "";
//		String result = HttpClientUtil.sendPost(url	, param);
//		com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(result);
//		com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) jsonArray.get(0);
//		com.alibaba.fastjson.JSONObject s = (com.alibaba.fastjson.JSONObject) jsonObject.get("_id");
//		System.out.println(s.get("$oid"));
		//UserInModelPo userInModelPo = JsonUtil.toObject(result, UserInModelPo.class);
		//String result = HttpClientDemo.sendPost(urltest	, paramtest);
		/*HttpClient client = new HttpClient();   
	      // 设置代理服务器地址和端口        
	  
	      //client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);   
	      // 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https   
	      //HttpMethod method=new GetMethod("http://localhost:8080/externalService!saveExternalInfo.action?externalInfo.contractCategoryId=4028811e4f2b0381014f2b0b65270000&externalInfo.content=");  
	      //使用POST方法  
	      HttpMethod method = null;
	      client.getHostConfiguration().setHost( "localhost" , 8080, "http" );  
	      method = getPostMethod();    // 使用 POST 方式提交数据   
	      client.executeMethod(method);   //打印服务器返回的状态   
	      System.out.println(method.getStatusLine());   //打印结果页面  
	      String response=new String(method.getResponseBodyAsString().getBytes("8859-1"));  
	  
	      //打印返回的信息  
	      System.out.println(response);  
	      method.releaseConnection();*/
	 }  
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
			throw new PersonalException("model增加用户请求异常："+e.getMessage());
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
}
