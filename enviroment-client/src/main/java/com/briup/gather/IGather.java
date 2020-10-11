package com.briup.gather;

import java.util.Collection;

import com.briup.bean.Enviroment;
/**
 * 对原始数据进行解析
 * @author ASUS
 */
@SuppressWarnings("ALL")
public interface IGather {
	/**
	 * 1.把原始数据解析为Java识别的对象
	 * 2.把解析好的对象存入容器中
	 * 
	 * @return
	 * @throws Exception
	 */
	Collection<Enviroment> gather() throws Exception ;
	
}

