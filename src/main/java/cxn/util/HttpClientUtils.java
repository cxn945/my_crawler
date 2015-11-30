package cxn.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * http方法
 * @author UPG-cxn 2015年11月9日 
 * @version V1.0
 */
public class HttpClientUtils {

	/**
	 * 通过Get方式获取请求内容
	 * @param url
	 * @return String
	 * @author cxn 2015年11月9日
	 * @throws Exception 
	 */
	public static Map<String,String> requestByGet(String url) throws Exception {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		Map<String,String> result = new HashMap<String, String>();
		try {
			HttpGet httpget = new HttpGet(Str2URI(url));
			CloseableHttpResponse response = httpClient.execute(httpget);
			int statusCode = response.getStatusLine().getStatusCode();
			if ( statusCode!= 200) {
				throw new Exception("Connection Failed!"+"statusCode="+statusCode+": "+url);
			}
			if(response.getEntity().getContentLength()/1024/1024>=1){
				throw new Exception("文件大于1M，不予处理！ url:"+url);
			}
			result.put("html", EntityUtils.toString(response.getEntity()));
			result.put("type", response.getHeaders("Content-Type")[0].getValue());
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	/**
	 * String型的url转为uri，避免异常字符报错。
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 * @throws URISyntaxException
	 * @author cxn 2015年11月30日
	 */
	public static URI Str2URI(String url) throws MalformedURLException, URISyntaxException{
		URL u = new URL(url);
		URI uri = new URI(u.getProtocol(), u.getHost(), u.getPath(), u.getQuery());
		return uri;
	}
}
