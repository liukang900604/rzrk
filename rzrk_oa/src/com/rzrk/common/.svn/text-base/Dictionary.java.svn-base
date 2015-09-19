package com.rzrk.common;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Dictionary{
	String key;//可以唯一标示
	String note;//不能唯一标示
	Set<DictionaryItem> dictionaryItems = new LinkedHashSet<DictionaryItem>();
	
	public Dictionary() {
	}
	public Dictionary(String key,String note) {
		this.key = key;
		this.note = note;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	public Set<DictionaryItem> getDictionaryItems() {
		return dictionaryItems;
	}
	public void setDictionaryItems(Set<DictionaryItem> dictionaryItems) {
		this.dictionaryItems = dictionaryItems;
	}
	
	
	public Dictionary addDictionaryItems( DictionaryItem dictionaryItem) {
		this.dictionaryItems.add(dictionaryItem);
		return this;
	}
	
	public DictionaryItem findByKey(String key) {
		for(DictionaryItem item : dictionaryItems){
			if(StringUtils.equals(item.getKey(),key)){
				return item;
			}
		}
		return null;
	}

	public DictionaryItem findByText(String text) {
		for(DictionaryItem item : dictionaryItems){
			if(StringUtils.equals(item.getText(),text)){
				return item;
			}
		}
		return null;
	}

	public DictionaryItem findByKeyWithDefault(String key) {
		for(DictionaryItem item : dictionaryItems){
			if(StringUtils.equals(item.getKey(),key)){
				return item;
			}
		}
		return findByDefault();
	}

	public DictionaryItem findByTextWithDefault(String text) {
		for(DictionaryItem item : dictionaryItems){
			if(StringUtils.equals(item.getText(),text)){
				return item;
			}
		}
		return findByDefault();
	}

	public DictionaryItem findByDefault() {
		for(DictionaryItem item : dictionaryItems){
			if(item.isDefaultItem()){
				return item;
			}
		}
		return null;
	}

	
}
