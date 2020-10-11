package com.briup.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.lang3.StringUtils;
/**
 * 备份模块
 * @author ASUS
 */ 
public class FileBackup {
	/**
	 * 将数据备份到文件中
	 * 
	 * @param path
	 * @param object
	 * @throws Exception
	 */
	public static void store(String path,Object object ) throws Exception {
		//判空处理
		if (StringUtils.isBlank(path)) {
			return;
		}
		//创建文件
		File file = new File(path);
		//如果文件不存在，那么就创建一个新文件
		if(!file.exists()){
			file.createNewFile();
		}
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(object);
		oos.flush();
		oos.close();
	}
	
	/**
	 * 从文件中读数据
	 * 
	 * @param path
	 * @param isDelete
	 * @return
	 * @throws Exception
	 */
	public static Object recover(String path,boolean isDelete) throws Exception {
		//判空处理
		if (StringUtils.isBlank(path)) {
			return null;
		}
		//创建文件
		File file = new File(path);
		//如果文件不存在，也就是没有数据，所以返回空
		if (!file.exists()) {
			return null;
		}
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		//如果传参为true则删除文件
		if (isDelete) {
			file.delete();
		}
		return ois.readObject();
	}
}
