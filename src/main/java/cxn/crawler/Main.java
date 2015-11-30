package cxn.crawler;

import java.util.Date;

import org.jsoup.helper.StringUtil;

import cxn.util.LogUtils;


public class Main {
	
	public static String baseUrl="http://money.163.com";
	public static String keyWord="15/1129";
	public static String src="网易财经";
	public static Byte lock=0;
//	public static int count=20000;
	
	final static LinkFilter filter = new LinkFilter() {
		public boolean accept(String url) {
			return accept(url, null);
		}

		public boolean accept(String url,String keyWord) {
			if(url.startsWith(baseUrl)){
				if(StringUtil.isBlank(keyWord)){
					return true;
				}else if(url.indexOf(keyWord)!=-1){
					return true;
				}
			}
			return false;
		}
	};
	
	public void getExcuteTime(CallBack callBack){
		long start = System.nanoTime();
		callBack.excute();//回调函数执行
		long end = System.nanoTime();
		System.out.println("Time Cost:"+(end-start)/1000000+"ms");
	}

	/**
	 * 执行
	 * @param threadCount 线程总数
	 * @author cxn 2015年11月10日
	 */
	public void crawling(int threadCount) {
		CrawlerThread ct = new CrawlerThread();
		
		for(int i=0;i<threadCount;i++){
			new Thread(ct).start();
		}
	}

	public static void main(String[] args) {
		Date start = new Date();
		
		Main crawler = new Main();
		LinkQueue.init(new String[] {baseUrl});
		crawler.crawling(10);
		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		LogUtils.log(baseUrl,start,new Date(),LinkQueue.getVisitedNum()+LinkQueue.getErrorNum(),LinkQueue.getVisitedNum(),LinkQueue.getErrorNum());
		System.out.println("---END---");
	}
}