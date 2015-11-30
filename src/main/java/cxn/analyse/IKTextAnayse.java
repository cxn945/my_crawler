package cxn.analyse;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import cxn.util.CollectionUtils;
/**
 * IKAnalyser文本分析
 * @author UPG-cxn 2015年11月17日 
 * @version V1.0
 */
public class IKTextAnayse implements TextAnalyse{

	public List<String> get5KeyWords(String content) throws IOException{
		Map<String, Integer> wordsFren=new HashMap<String, Integer>();
        IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(content), true);
        Lexeme lexeme;
        while ((lexeme = ikSegmenter.next()) != null) {
        	String word = lexeme.getLexemeText();
            if(word.length()>1){
                if(wordsFren.containsKey(word)){
                    wordsFren.put(word,wordsFren.get(word)+1);
                }else {
                    wordsFren.put(word,1);
                }
            }
        }
        wordsFren = CollectionUtils.sortByValue(wordsFren);
//        System.out.println(wordsFren);
        List<String> keyWords = new ArrayList<String>(wordsFren.keySet());
        if(keyWords.size()>5){
        	return keyWords.subList(0, 5);
        }else{
        	return keyWords;
        }
	}

	
//	public static void main(String[] args) throws IOException{
//		String content = new String(
//				"原标题：澳洲海滩现奇特生物似外星人：满身斑点 形状奇特据英国《每日邮报》11月15日报道"
//						+ "，近日，澳大利亚黄金海岸布罗德本特海滩惊现奇特蓝色生物，状如科幻电影中的外星球生物。据悉，该生物是当地人露辛达·弗瑞（ Lucinda Fry）"
//						+ "在海滩散步时发现的。她将其在沙滩中蠕动的画面拍摄成视频并放到网上，网友纷纷猜测这神秘的生物到底是什么。经格里菲斯大学"
//						+ "海洋生物学家凯莉·皮特（Kylie Pitt）鉴定，该海洋生物为大西洋海神海蛞蝓，俗称海燕，或者蓝龙，以水螅、水母等为主食，体态优雅、颜色鲜艳，外型美得不像是地球上的生物");
//		IKTextAnayse anayse = new IKTextAnayse();
//		System.out.println(anayse.get5KeyWords(content));
//	}
}
