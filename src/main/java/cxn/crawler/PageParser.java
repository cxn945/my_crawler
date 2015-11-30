package cxn.crawler;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

@SuppressWarnings("serial")
public class PageParser {
	
	
	public final static String HOME_PAGE = "HOME PAGE,NO ARTICLE"; 
	/**
	 * 获取一个网页上的链接,并加入到队列中
	 * @param content
	 * @param filter 用来过滤链接
	 * @return Set<String>
	 * @author cxn 2015年11月5日
	 */
	public static Set<String> extracLinks(String content, LinkFilter filter) {
		Set<String> links = new HashSet<String>();
		try {
			Parser parser = new Parser(content);
			// parser.setEncoding("utf-8");
			// 过滤 <frame >标签的 filter，用来提取 frame 标签里的 src 属性所表示的链接
			NodeFilter frameFilter = new NodeFilter() {
				public boolean accept(Node node) {
					if (node.getText().startsWith("frame src=")) {
						return true;
					} else {
						return false;
					}
				}
			};
			// OrFilter 来设置过滤 <a> 标签，和 <frame> 标签
			OrFilter linkFilter = new OrFilter(new NodeClassFilter(
					LinkTag.class), frameFilter);
			// 得到所有经过过滤的标签
			NodeList list = parser.extractAllNodesThatMatch(linkFilter);
			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);
				if (tag instanceof LinkTag){
					LinkTag link = (LinkTag) tag;
					String linkUrl = link.getLink();
					if(linkUrl.startsWith("http") && filter.accept(linkUrl, Main.keyWord)){
						links.add(linkUrl);
					}else if(linkUrl.startsWith("/") && filter.accept(Main.baseUrl+linkUrl, Main.keyWord)){
						links.add(Main.baseUrl+linkUrl);
					}
				}else{
					// 提取 frame 里 src 属性的链接如 <frame src="test.html"/>
					String frame = tag.getText();
					int start = frame.indexOf("src=");
					frame = frame.substring(start);
					int end = frame.indexOf(" ");
					if (end == -1){
						end = frame.indexOf(">");
					}
					String frameUrl = frame.substring(5, end - 1);
					if (filter.accept(frameUrl)){
						links.add(frameUrl);
					}
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		LinkQueue.addUnvisitedUrl(links);
		return links;
	}
	/**
	 * 获取网页内容（过滤掉各种标签）
	 * @param html
	 */
	public static String getText(String html){
		return Jsoup.parse(html).text();
	}
	/**
	 * 获取网易新闻正文
	 * @param html
	 */
	public static String get163NewsBody(String html){
		Elements elements = Jsoup.parse(html).select("div[id=endText]");
		if(elements!=null && elements.size()>0){
			return elements.text();
		}else{
			return HOME_PAGE;
		}
	}
}