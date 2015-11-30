package cxn.analyse;

import java.io.IOException;
import java.util.List;

import com.hankcs.hanlp.summary.TextRankKeyword;

import cxn.crawler.PageParser;

/**
 * hanLP分词工具，常用方法 <br>
 * GitHub地址：<em>https://github.com/hankcs/HanLP</em>
 * 
 * @author UPG-cxn 2015年11月18日
 * @version V1.0
 */
public class HanLPTextAnalyse implements TextAnalyse {

	public List<String> get5KeyWords(String text) throws IOException {
		if(PageParser.HOME_PAGE.equals(text)){
			return null;
		}else{
			return TextRankKeyword.getKeywordList(text, 5);
		}
	}

//	public static void main(String[] args) throws IOException {
//		String reader = new String(
//				"原标题：澳洲海滩现奇特生物似外星人：满身斑点 形状奇特据英国《每日邮报》11月15日报道"
//						+ "，近日，澳大利亚黄金海岸布罗德本特海滩惊现奇特蓝色生物，状如科幻电影中的外星球生物。据悉，该生物是当地人露辛达·弗瑞（ Lucinda Fry）"
//						+ "在海滩散步时发现的。她将其在沙滩中蠕动的画面拍摄成视频并放到网上，网友纷纷猜测这神秘的生物到底是什么。经格里菲斯大学"
//						+ "海洋生物学家凯莉·皮特（Kylie Pitt）鉴定，该海洋生物为大西洋海神海蛞蝓，俗称海燕，或者蓝龙，以水螅、水母等为主食，体态优雅、颜色鲜艳，外型美得不像是地球上的生物");
//
//		HanLPTextAnalyse tools = new HanLPTextAnalyse();
//		System.out.println(tools.get5KeyWords(reader));
//	}
}
