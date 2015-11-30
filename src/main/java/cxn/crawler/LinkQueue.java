package cxn.crawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class LinkQueue{
	/**已访问的 url 集合*/
	private static Set<String> visitedUrl = new HashSet<String>(500);
	/**待访问的 url 集合*/
	private static Queue<String> waitUrl = new PriorityQueue<String>(500);
	/**当前正在访问的url*/
	private static Set<String> nowReqUrlSet = new HashSet<String>(10);
	/**发生异常的url集合*/
	private static LinkedList<String> errorUrl = new LinkedList<String>();

	
	/**
	 * 使用种子初始化 URL 队列
	 */
	public static void init(String[] seeds) {
		for (int i = 0; i < seeds.length; i++){
			addUnvisitedUrl(seeds[i]);
		}
	}

	/**
	 * 添加到访问过的URL队列中
	 */
	public static void addVisitedUrl(String url) {
		visitedUrl.add(url);
	}

	/**
	 * 未访问的URL出队列
	 * @return Object
	 * @author cxn 2015年11月5日
	 */
	public static synchronized String pollUrl() {
		String url = waitUrl.poll();
		while(nowReqUrlSet.contains(url)){
			url = waitUrl.poll();
		}
		nowReqUrlSet.add(url);
		return url;
	}

	/**
	 * 将解析出的url添加到未访问列表，先做校验，确保每个url只被访问一次
	 * @param url
	 * @author cxn 2015年11月5日
	 */
	public static synchronized void addUnvisitedUrl(Set<String> urls) {
		for (String link : urls) {
			addUnvisitedUrl(link);
		}
	}
	public static void addUnvisitedUrl(String url) {
		if (url != null){
			url = url.trim();
			if(!url.equals("") && !visitedUrl.contains(url) && !waitUrl.contains(url) && !nowReqUrlSet.contains(url)){
				waitUrl.add(url);
			}
		}
	}

	/** 获得已经访问的URL数目*/
	public static int getVisitedNum() {
		return visitedUrl.size();
	}
	/** 获得访问失败的URL数目*/
	public static int getErrorNum() {
		return errorUrl.size();
	}

	/** 判断未访问的URL队列中是否为空*/
	public static boolean waitUrlsEmpty() {
		return waitUrl.isEmpty();
	}
	/**是否可结束，所有URL都已经遍历*/
	public static boolean canEnd(){
		return waitUrl.isEmpty()&&nowReqUrlSet.isEmpty() ;
	}
	
	/** 加入到异常url队列*/
	public static void addErrorUrl(String url){
		if(url!=null){
			errorUrl.add(url);
		}
	}
	/**冲正在访问的url set中移除*/
	public static void removeNowReqUrlSet(String url){
		if(nowReqUrlSet.size()>0){
			nowReqUrlSet.remove(url);
		}
	}
}