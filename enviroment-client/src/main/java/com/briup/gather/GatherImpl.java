package com.briup.gather;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.briup.bean.Enviroment;
import com.briup.util.FileBackup;
import com.briup.util.FileBackupNameEnum;
import com.briup.util.FileNamesEnum;

/**
 * 对原始数据进行解析
 *
 * @author wangm
 */
@SuppressWarnings("ALL")
public class GatherImpl implements IGather {
    /**
     * 1.将文件中的数据进行读取
     * 2.将读取好的数据以 | 进行分割
     * 3.将分割好的数据放到Environment对象中
     * 4.然后将Environment对象存储到集合中，并将集合对象返回
     *
     * @return
     * @throws Exception
     */
    @Override
    public Collection<Enviroment> gather() throws Exception {
        BufferedReader buffer = null;
        //存储解析好的对象
        List<Enviroment> list = new ArrayList<>();
        //记录当前的字节数
        long line = 0;
        try {
            //PathDemo2.class.getResource("/message.properties")
            //"src/main/resources/radwtmp"
            FileReader fileReader = new FileReader(GatherImpl.class.getClassLoader().getResource("radwtmp").getPath());
            buffer = new BufferedReader(fileReader);
            //用来记录字节数
            //读取count文件中记录的数据
            Object object = FileBackup.recover(FileNamesEnum.CLIENT_COUNT_PATH.getPath(), true);
            //取出存储的字节数
            if (object != null) {
                line += (Long) object;
            }
            //跳过指定字节再读取文件
            buffer.skip(line);

            String str = null;
            //读取备份文件中的数据
            Object dataObj = FileBackup.recover(FileBackupNameEnum.CLINT_BACKUP_PATH.getPath(), true);
            //如果数据不为空，则把数据放到list集合中
            if (dataObj != null) {
                list.addAll((Collection<? extends Enviroment>) dataObj);
            }

            while ((str = buffer.readLine()) != null) {
                //记录字节数，回车换行占两个字节，所以加2
                line = str.length() + 2;
                String[] strings = str.split("\\|");
                if (strings.length == 0) {
                    return null;
                }

                Enviroment enviroment = new Enviroment();
                enviroment.setSrcId(strings[0]);
                enviroment.setDevId(strings[1]);
                enviroment.setRegionId(Long.parseLong(strings[2]));
                enviroment.setCount(Long.parseLong(strings[4]));
                enviroment.setState(Integer.parseInt(strings[5]));
                enviroment.setReviceState(Integer.parseInt(strings[7]));
                Long longs = Long.parseLong(strings[8]);
                enviroment.setGatheDate(new Date(longs));
                if ("16".equals(strings[3])) {
                    enviroment.setName("温度");
                    //两个参数的parseInt方法为把几进制的数转换为十进制的数
                    int result = Integer.parseInt(strings[6].substring(0, 4), 16);
                    double data = ((float) result * 0.00268127) - 46.85;
                    enviroment.setData(data);
                    list.add(enviroment);

                    enviroment.setSrcId(strings[0]);
                    enviroment.setDevId(strings[1]);
                    enviroment.setRegionId(Long.parseLong(strings[2]));
                    enviroment.setName("湿度");
                    enviroment.setCount(Long.parseLong(strings[4]));
                    enviroment.setState(Integer.parseInt(strings[5]));
                    result = Integer.parseInt(strings[6].substring(4, 8), 16);
                    data = ((float) result * 0.00190735) - 6;
                    enviroment.setData(data);
                    enviroment.setReviceState(Integer.parseInt(strings[7]));
                    longs = Long.parseLong(strings[8]);
                    enviroment.setGatheDate(new Date(longs));

                    list.add(enviroment);

                } else if ("256".equals(strings[3])) {
                    enviroment.setName("光照强度");
                    enviroment.setData(Integer.parseInt(strings[6].substring(0, 4), 16));

                    list.add(enviroment);
                } else {
                    enviroment.setName("CO2数据");
                    enviroment.setData(Integer.parseInt(strings[6].substring(0, 4), 16));

                    list.add(enviroment);
                }
            }
            FileBackup.store(FileNamesEnum.CLIENT_COUNT_PATH.getPath(), line);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            //发生异常后将解析好的数据保存到list集合中
            FileBackup.store(FileBackupNameEnum.CLINT_BACKUP_PATH.getPath(), list);
            return null;
        } finally {
            try {
                if (buffer != null) {
                    buffer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
