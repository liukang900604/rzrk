package com.rzrk.common.contract;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Product;
import com.rzrk.service.AdminService;
import com.rzrk.service.ProductService;

@Component("contractParserProduct")
public class ContractParserProduct implements ContractParser {
	private static final String CACHE_PRE = "contractParser.product";

	@Resource(name = "productServiceImpl")
	ProductService productService;

	@Resource(name = "cacheManager")
	GeneralCacheAdministrator generalCacheAdministrator;

	@Override
	public String parse(String text) {
		if(StringUtils.isEmpty(text)) return null;
		String key = CACHE_PRE+".text." + text;
		String value = null;
		try {
			value = (String) generalCacheAdministrator.getFromCache(key);
		} catch (NeedsRefreshException e) {
			boolean updateSucceeded = false;
			try {
				Product product = (Product) productService.getProductByAnyName(text);
				if (product != null) {
					value = product.getId();
					generalCacheAdministrator.putInCache(key, value);
					updateSucceeded = true;
				}
			} catch (Exception ex) {
				e.printStackTrace();
			} finally {
				if (!updateSucceeded) {
					generalCacheAdministrator.cancelUpdate(key);
				}
			}
		}

		return value;
	}
	
	@Override
	public List<String> parseList(String text) {
		ArrayList<String> list = new ArrayList<String>();
		if(StringUtils.isEmpty(text)) return list;
		List<Product> productList = productService.getProductListByAnyName(text);
		for(Product product : productList){
			list.add(product.getId());
		}
		return list;
	}

	@Override
	public String format(String value) {
		if(StringUtils.isEmpty(value)) return null;
		String key = CACHE_PRE+".value." + value;
		String text = null;
		try {
			text = (String) generalCacheAdministrator.getFromCache(key);
		} catch (NeedsRefreshException e) {
			boolean updateSucceeded = false;
			try {
				Product product = productService.get(value);
				if (product != null) {
					text = product.getName();
					generalCacheAdministrator.putInCache(key, text);
					updateSucceeded = true;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				if (!updateSucceeded) {
					generalCacheAdministrator.cancelUpdate(key);
				}
			}
		}
		return text;
	}

	@Override
	public String getName() {
		return "产品";
	}

	public void removeCache(Product product){
		generalCacheAdministrator.removeEntry(CACHE_PRE+".text." + product.getName());
		generalCacheAdministrator.removeEntry(CACHE_PRE+".text." + product.getNameShort());
		generalCacheAdministrator.removeEntry(CACHE_PRE+".text." + product.getNameSShort());
		generalCacheAdministrator.removeEntry(CACHE_PRE+".value." + product.getId());
	}
}
