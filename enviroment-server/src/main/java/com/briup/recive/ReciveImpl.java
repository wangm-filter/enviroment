package com.briup.recive;

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * 接受客户端发送过来的数据
 * 
 * @author ASUS
 */
@SuppressWarnings("all")
public class ReciveImpl implements IRecive{
	//创建日志对象
	private static final Logger logger = Logger.getLogger(ReciveImpl.class);

	/**
	 * 接收发送过来的数据
	 * 因为客户端每隔10分钟就发送一次数据
	 * 所以后一次的数据会等到前一次的数据
	 * 接收完之后才会处理，这显然会降低程序的执行速度
	 * 所以要使用线程
	 * 即客户端发送一次数据，就开启一个线程去接收并处理数据
	 */
	@Override
	public void recive() {
		try {
			logger.debug("服务器开启");
			//创建服务端ServerSocket对象
			ServerSocket serverSocket = new ServerSocket(8888);
			logger.debug("正准备接收："+serverSocket.getInetAddress()+"发送过来的数据");
			//保证服务端永远开启
			while(true) {
				/*
				 * 当accept执行完成，三次握手就建立完成了
				 * 返回的socket中就包含了客户端传输的数据
				 */
				Socket socket = serverSocket.accept();
				//为了提高效率所以开启一个线程去处理socket
				MyThread thread = new MyThread(socket);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
