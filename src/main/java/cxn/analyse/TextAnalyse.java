package cxn.analyse;

import java.io.IOException;
import java.util.List;
/**
 * 文本分析接口
 * @author UPG-cxn 2015年11月18日 
 * @version V1.0
 */
public interface TextAnalyse {
	/**
	 * 分析文本，获取前五个关键字
	 * @param content
	 */
	public List<String> get5KeyWords(String text) throws IOException;
}
