package com.briup;

import java.sql.Date;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.briup.recive.ReciveImpl;

public class ReciveTest {
	private Logger logger = Logger.getLogger(ReciveTest.class);
	@Test
	public void t1() {
		ReciveImpl reciveImpl = new ReciveImpl();
		reciveImpl.recive();
	}
	
	@Test
	public void t2() {
		logger.debug("日志一");
		logger.warn("日志2");
		logger.info("日志3");
		logger.error("日志4");
		logger.fatal("日志5");
	}
	@Test
	public void t3() {
		Date date = new Date(System.currentTimeMillis());
//		System.out.println(date.toString().substring(8));
		System.out.println(date.toString().split("-")[2]);
	}
}
