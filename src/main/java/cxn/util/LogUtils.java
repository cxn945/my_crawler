package cxn.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * log工具
 * @author UPG-cxn 2015年11月9日 
 * @version V1.0
 */
public class LogUtils {
	
	/**
	 * 将异常写入到exception.log文件中
	 * @param ex
	 * @author cxn 2015年11月9日
	 */
	public static void logException(Exception ex){
		PrintWriter pw = null;
		StringWriter sw = null;
		FileOutputStream os = null;
		sw = new StringWriter();
		pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		
		try {
			os = new FileOutputStream(new File("exception.log"),true);
			os.write((DateTimeUtil.getNowTimeStr()+"\r\n"+sw.toString()).getBytes());
		} catch (Exception e) {
			System.err.println("记录异常失败！");
		}finally{
			try {
				sw.close();
				pw.close();
				os.close();
			} catch (IOException e) {
				System.err.println("关闭异常日志IO流失败！");
			}
		}
	}
	/**
	 * 爬行日志，记录在crawler.log中
	 * @param url
	 * @param start
	 * @param end
	 * @param count	总访问数
	 * @param succNum	成功次数 
	 * @param errorNum	失败次数
	 * @author cxn 2015年11月18日
	 */
	public static void log(String url,Date start,Date end,int count,int succNum,int errorNum){
		FileOutputStream os = null;
		String startTime = "起始时间："+ DateTimeUtil.toDateTimeString(start);
		String endTime = "结束时间："+ DateTimeUtil.toDateTimeString(end);
		try {
			os = new FileOutputStream(new File("crawler.log"),true);
			os.write("\r\n".getBytes());
			os.write(("baseUrl:"+url).getBytes());
			os.write("\r\n".getBytes());
			os.write(startTime.getBytes());
			os.write("\r\n".getBytes());
			os.write(endTime.getBytes());
			os.write("\r\n".getBytes());
			os.write(("访问总数："+count).getBytes());
			os.write("\r\n".getBytes());
			os.write(("成功次数："+succNum).getBytes());
			os.write("\r\n".getBytes());
			os.write(("失败次数："+errorNum).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
