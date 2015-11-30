package cxn.crawler;


public class CrawlerThread implements Runnable{

	public void run() {
		doCrawl();
	}
	
	private void doCrawl(){
		while (!LinkQueue.canEnd()) {
			//当前没有未遍历URL，等待1s后在进行判断
			if(LinkQueue.waitUrlsEmpty()){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				String reqUrl = LinkQueue.pollUrl();
				if(reqUrl!=null){
//					boolean flag = DownloadTool.downloadPage2File(reqUrl);
					boolean flag = DownloadTool.downloadPage2DB(reqUrl);
					if(flag){
						LinkQueue.addVisitedUrl(reqUrl);
					}else{
						LinkQueue.addErrorUrl(reqUrl);
					}
					LinkQueue.removeNowReqUrlSet(reqUrl);
				}
			}
		}
		notifyMain();
	}
	/**线程关闭前的操作，最后剩下main线程、数据库链接线程活动*/
	private synchronized void notifyMain(){
		System.out.println(Thread.currentThread().getThreadGroup().activeCount());
		if(Thread.currentThread().getThreadGroup().activeCount()==3){
			synchronized (Main.lock) {
				Main.lock.notifyAll();
			}
		}
	}
}
