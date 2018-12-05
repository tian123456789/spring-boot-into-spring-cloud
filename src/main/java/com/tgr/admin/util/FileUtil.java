package com.tgr.admin.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@SuppressWarnings("all")
public class FileUtil {
	/**
	 * 
	 * @param filePath 目标路径
	 * @param jpgName 目标名称（.jpg）
	 * @param source 数据来源
	 * @return 返回生成文件的磁盘路径
	 */
	public static String covertToJpg(String filePath, String jpgName, String source) {
		
		return "";
	}
	
	/**
	 * 判断图片url的有效性
	 * 
	 * @param str
	 * @return boolean
	 * @author lvwenwen
	 * 
	 */
	public static Boolean JudgeLive(String str){
		try {
            URL url = new URL(str);
            // 返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接。
            URLConnection uc = url.openConnection();
            // 打开的连接读取的输入流。
            InputStream in = uc.getInputStream();
            in.close();
            return true;
        } catch (Exception e) {
            return false;
        }
	}
	
	/**
	 * 把网络图片转本地图片
	 * 
	 * @param url
	 * @return
	 * 
	 * @author lvwenwen
	 * @throws Exception 
	 */
	public static File getnetFile(String str) throws Exception{
		String path = AppUtil.getAbsolutePath();
		//new一个URL对象  
		URL url = new URL(str);
        //打开链接  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        //设置请求方式为"GET"  
        conn.setRequestMethod("GET");  
        //超时响应时间为5秒  
        conn.setConnectTimeout(5 * 1000);  
        //通过输入流获取图片数据  
        InputStream inStream = conn.getInputStream();  
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
        byte[] data = readInputStream(inStream);  
        //new一个文件对象用来保存图片，默认保存当前工程根目录  
        File imageFile = new File(path+"lishi"+(int)(Math.random()*100)+(int)(Math.random()*1000)+".jpg");  
        //创建输出流  
        FileOutputStream outStream = new FileOutputStream(imageFile);  
        //写入数据  
        outStream.write(data);  
        //关闭输出流  
        outStream.close();  
        inStream.close();
        return imageFile;
	}
	
    private static byte[] readInputStream(InputStream inStream) throws IOException{  
    	ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
		//创建一个Buffer字符串  
		byte[] buffer = new byte[1024];  
		//每次读取的字符串长度，如果为-1，代表全部读取完毕  
		int len = 0;  
		//使用一个输入流从buffer里把数据读取出来  
		while( (len=inStream.read(buffer)) != -1 ){  
		    //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
		    outStream.write(buffer, 0, len);  
		}  
		//关闭输入流  
		inStream.close();  
		//把outStream里的数据写入内存  
		return outStream.toByteArray();  
    }  

}
