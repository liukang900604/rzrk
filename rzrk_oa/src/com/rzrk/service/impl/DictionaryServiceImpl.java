package com.rzrk.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import com.rzrk.common.Dictionary;
import com.rzrk.service.DictionaryService;

@Service("dictionaryServiceImpl")
public class DictionaryServiceImpl implements DictionaryService {
	Map<String, Dictionary> dictionaryMap = new ConcurrentHashMap<String, Dictionary>();
	
	public void addDictionary(Dictionary dictionary){
		dictionaryMap.put(dictionary.getKey(), dictionary);
	}
	
	@Override
	public Dictionary findDictionaryByKey(String key) {
		return dictionaryMap.get(key);
	}



}
