package cxn.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CollectionUtils{


	/**
	 * 按value进行降序排序
	 * @param map
	 * @return Map<K,V>
	 * @author cxn 2015年11月18日
	 */
	public static <K, V extends Comparable<? super V>> Map<K,V> sortByValue(Map<K, V> map){
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K,V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>(){
			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return (o2.getValue()).compareTo( o1.getValue() );
			}
			
		});
		Map<K, V> result = new LinkedHashMap<K, V>();
		for(Map.Entry<K, V> entry:list){
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
