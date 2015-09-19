package com.rzrk.common.product;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.rzrk.service.ProductRecordService;

@Component
public class ProductRecordEngine {
	@Resource
	ProductRecordService productRecordService;
	ScheduledExecutorService scheduledThreadPool;
	//TODO 稍后需要改成队列化
	
	
	public ProductRecordEngine() {
		scheduledThreadPool= Executors.newScheduledThreadPool(1);
	}
	
	@PreDestroy
	public void stop(){
		scheduledThreadPool.shutdown();
	};

	
	public Future postAll(){
		return (Future) scheduledThreadPool.schedule(new Runnable() {
			@Override
			public void run() {
				productRecordService.disposeAll();
			}
		},1,TimeUnit.SECONDS);
	};
	
//	public Future post(final String productDetailId ){
//		return (Future) scheduledThreadPool.submit(new Runnable() {
//			@Override
//			public void run() {
//				productRecordService.dispose(productDetailId);
//			}
//		});
//	};
	
	public Future clearAll(){
		return (Future) scheduledThreadPool.submit(new Runnable() {
			@Override
			public void run() {
				productRecordService.deleteAll();
			}
		});	}
	
}
