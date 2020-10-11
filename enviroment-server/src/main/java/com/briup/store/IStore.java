package com.briup.store;

import java.util.Collection;

import com.briup.bean.Enviroment;

/**
 * 存储数据
 * 
 * @author ASUS
 */
public interface IStore {
	void store(Collection<Enviroment> collection) throws Exception;
}
