package com.rzrk.service;

import com.rzrk.common.Dictionary;

public interface DictionaryService {
	public void addDictionary(Dictionary dictionary);
	public Dictionary findDictionaryByKey(String key);
}
