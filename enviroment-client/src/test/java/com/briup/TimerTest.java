package com.briup;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerTest {
	public static void main(String[] args) {
		Timer timer = new Timer();
		
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				System.out.println("任务一");
//			}
//		}, 1000);
		
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				System.out.println("任务二");
//			}
//		}, 1000, 2000);
		
//		timer.schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				System.out.println("任务三");
//			}
//		}, new Date(),1000);
		
//		timer.schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				System.out.println("任务四");
//			}
//		}, 5000);
		
//		timer.schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				System.out.println("任务5");
//			}
//		}, 1000,2000);
//		timer.schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				System.out.println("任务6");
//			}
//		}, 1000,2000);
//		
		ScheduledExecutorService scheduledExecutorService  = 
				Executors.newScheduledThreadPool(5);
//		//程序延迟1秒执行，之后每隔3秒执行一次
//		scheduledExecutorService.scheduleAtFixedRate( () -> {
//			System.out.println("任务一   111");
//			System.out.println("任务一   222");
//			System.out.println("任务一   333");
//		}, 1, 3, TimeUnit.SECONDS);
		
		//程序延迟一秒执行，只执行一次
//		scheduledExecutorService.schedule(() -> {
//			System.out.println("任务二 111");
//			System.out.println("任务二 222");
//			System.out.println("任务二 333");
//		}, 1, TimeUnit.SECONDS);
		
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			System.out.println("任务三 javame");
			System.out.println("任务三 javase");
			System.out.println("任务三 javaee");
		}, 1, 2, TimeUnit.SECONDS);
	}
}






