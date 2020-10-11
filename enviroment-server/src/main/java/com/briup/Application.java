package com.briup;

import com.briup.recive.ReciveImpl;

/**
 * server端程序入口
 * 
 * @author ASUS
 */ 
public class Application {
	public static void main(String[] args) {
		ReciveImpl recive = new ReciveImpl();
		recive.recive();
	}
}
