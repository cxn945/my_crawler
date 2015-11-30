package cxn.crawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;

import cxn.access.MysqlConnInstance;
import cxn.analyse.HanLPTextAnalyse;
import cxn.analyse.TextAnalyse;
import cxn.util.HttpClientUtils;
import cxn.util.LogUtils;

public class DownloadTool {
	/**
	 * 根据 url 和网页类型生成需要保存的网页的文件名 去除掉 url 中非文件名字符
	 */
	public static String getFileNameByUrl(String url, String contentType) {
		// remove http://
		url = url.substring(7);
		// text/html类型
		if (contentType.indexOf("html") != -1) {
			url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".txt";
			return url;
		}else {
			// 如application/pdf类型
			return url.replaceAll("[\\?/:*|<>\"]", "_") + "."+ contentType.substring(contentType.lastIndexOf("/") + 1);
		}
	}

	/**
	 * 提取网页文本，并保存到本地
	 * @param html
	 * @param filePath
	 * @author cxn 2015年11月6日
	 */
	public static void saveToLocal(String html, String filePath) {
		FileOutputStream out=null;
		
		try {
			String text = Jsoup.parse(html).text();
			
			out = new FileOutputStream(new File(filePath),false);
			out.write(text.getBytes());
			out.flush();
			out.close();
		} catch (Exception e) {
			LogUtils.logException(e);
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				System.out.println("close failed!");
			}
		}
	}

	/**
	 * 下载url指向的网页，保存在本地文件夹
	 * @param url
	 * @return boolean
	 * @author cxn 2015年11月5日
	 */
	public static boolean downloadPage2File(String url){
		System.out.println(Thread.currentThread().getId()+"--Get Page File--:"+url);
		try {
			Map<String, String> reqMap = HttpClientUtils.requestByGet(url);
			
			PageParser.extracLinks(reqMap.get("body"), Main.filter);
			String filePath = "F:\\spider_list\\" + getFileNameByUrl(url,reqMap.get("type"));
			
			saveToLocal(reqMap.get("body"), filePath);
		}catch(Exception e){
			LogUtils.logException(e);
		}
		
		return true;
	}
	/**
	 * 下载url指向的网页，保存在数据库
	 * @param url
	 * @return boolean
	 * @author cxn 2015年11月18日
	 */
	public static boolean downloadPage2DB(String url){
		System.out.println(Thread.currentThread().getId()+"--Get Page DB--:"+url);
		try {
			Map<String, String> reqMap = HttpClientUtils.requestByGet(url);
			
			PageParser.extracLinks(reqMap.get("html"), Main.filter);
			String body = PageParser.get163NewsBody(reqMap.get("html"));
			
			TextAnalyse ta 
//				= new IKTextAnayse();
				= new HanLPTextAnalyse();
			List<String> keyWords = ta.get5KeyWords(body);
			Object[] params = new Object[5];
			params[0]=Main.src;//来源
			params[1]=url;
			params[2]=body;
			params[3]=keyWords;
			params[4]=1;
			MysqlConnInstance.insert("insert into page_visited(src,url,content,key_words,status,ctime,mtime) values(?,?,?,?,?,NOW(),NOW())",params);
		}catch(Exception e){
			LogUtils.logException(e);
			e.printStackTrace();
		}
		
		return true;
	}
	
}
