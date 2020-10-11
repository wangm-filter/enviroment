package com.briup;

import java.util.Collection;

import org.junit.Test;

import com.briup.bean.Enviroment;
import com.briup.gather.GatherImpl;
import com.briup.gather.IGather;

public class GatherTest {
	
	@Test
	public void t1() throws Exception {
		IGather gather = new GatherImpl();
		Collection<Enviroment> collection = gather.gather();
		for (Enviroment enviroment : collection) {
			System.out.println(enviroment);
		}
	}
}
