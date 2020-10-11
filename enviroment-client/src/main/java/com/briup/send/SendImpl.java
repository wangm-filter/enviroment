package com.briup.send;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;

import com.briup.bean.Enviroment;
import com.briup.util.FileBackup;

/**
 * @author ASUS
 */
public class SendImpl implements ISend{

	@Override
	public void send(Collection<Enviroment> collection) {
		ObjectOutputStream oos = null;
		Socket socket = null;
		try {
			//从备份文件中读取数据
			Object obj = FileBackup.recover("src/main/resources/sendData.txt", true);
			if (obj != null) {
				collection.addAll((Collection<? extends Enviroment>) obj);
			}
			socket = new Socket("127.0.0.1", 8888);
			oos = new ObjectOutputStream(socket.getOutputStream());
			//把对象序列化传出去
			oos.writeObject(collection);
			//刷新缓存
			oos.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				//发生异常时把数据放到备份文件中
				FileBackup.store("src/main/resources/sendData.txt", collection);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				assert socket != null;
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (oos != null) {
					oos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
