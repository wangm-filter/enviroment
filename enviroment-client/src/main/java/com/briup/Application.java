package com.briup;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.briup.gather.GatherImpl;
import com.briup.gather.IGather;
import com.briup.send.ISend;
import com.briup.send.SendImpl;

/**
 * @author ASUS
 */
public class Application {
	public static void main(String[] args) {
		//创建采集对象
		IGather gather = new GatherImpl();
		//创建发送数据对象
		ISend send = new SendImpl();
		//创建定时器线程池
		ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5);
		//创建任务并执行
		//程序运行后，延迟一秒执行，之后每隔600秒执行一次
		threadPool.scheduleAtFixedRate(() -> {
			try {
				send.send(gather.gather());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 1, 600, TimeUnit.SECONDS);
	}
}
