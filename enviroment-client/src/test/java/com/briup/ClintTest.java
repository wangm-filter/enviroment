package com.briup;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.briup.gather.GatherImpl;
import com.briup.gather.IGather;
import com.briup.send.ISend;
import com.briup.send.SendImpl;

public class ClintTest {
	public static void main(String[] args) {
		IGather gather = new GatherImpl();
		ISend send = new SendImpl();
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			try {
				send.send(gather.gather());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 2, 600, TimeUnit.SECONDS);
	}
}
