package cxn.crawler;

public interface LinkFilter {
	
	/**
	 * 以startUrl开头进行过滤
	 * @param startUrl
	 * @return boolean
	 * @author cxn 2015年11月9日
	 */
	public boolean accept(String startUrl);
	/**
	 * 是否含指定的关键字
	 * @param url
	 * @param keyWord
	 * @return boolean
	 * @author cxn 2015年11月9日
	 */
	public boolean accept(String url,String keyWord);
	
}
