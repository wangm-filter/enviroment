package com.briup.recive;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Collection;

import com.briup.bean.Enviroment;
import com.briup.store.StoreImpl;


/**
 * 自定义线程
 * 对socket进行处理
 * 将socket中包含的数据发序列化出来，得到Collection集合
 *
 * @author ASUS
 */
@SuppressWarnings("all")
public class MyThread extends Thread {
    private Socket socket;

    //创建对象时必须传入一个socket对象
    public MyThread(Socket socket) {
        super();
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //构建反序列化流
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //将数据从socket中反序列化出来
            Collection<Enviroment> collection = (Collection<Enviroment>) ois.readObject();
            //collection.forEach(System.out::println);
            //对数据进行存储操作
            StoreImpl store = new StoreImpl();
            //存储数据
            store.store(collection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
