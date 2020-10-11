package com.briup.store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;

import com.briup.util.FileBackupNameEnum;
import org.apache.log4j.Logger;

import com.briup.bean.Enviroment;
import com.briup.util.ConnectionUtils;
import com.briup.util.FileBackup;

/**
 * 实现数的存储
 * 
 * @author wangm
 */
@SuppressWarnings("all")
public class StoreImpl implements IStore {
	private static final Logger logger = Logger.getLogger(StoreImpl.class);
	
	@Override
	public void store(Collection<Enviroment> collection){
		//将备份数据读加载到集合中
		try {
			Object object = FileBackup.recover(FileBackupNameEnum.CLINT_BACKUP_PATH.getPath(), true);
			if (object !=  null) {
				collection.addAll((Collection<? extends Enviroment>) object);
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		//声明连接对象
		Connection connection = null;
		PreparedStatement ps = null;
		//用来记录天数
		String day = "0";
		//用来记录缓存池添加的SQL条数
		int num = 0;
		
		try {
			//获取连接对象
			connection = ConnectionUtils.getConnection(false);
			for (Enviroment enviroment : collection) {
				//获取日期天数
				String day1 = enviroment.getGatheDate().toString().substring(8);
				//保证同一天只创建一个ps对象
				if (!day.equals(day1)) {
					//上一次循环，ps缓存池里面还有没执行的sql
					if (ps != null) {
						ps.executeBatch();
						ps.clearBatch();
						//关闭ps防止超出游标最大数
						ps.close();
					}
					day = day1;
					//拼接sql
					String sql = "insert into tbl_data_" + day + " values(common_seq.nextval,?,?,?,?,?,?,?,?,?)";
					ps = connection.prepareStatement(sql);
				}
				
				//insert into tbl_data_" + day + "values(common_seq.nextval,?,?,?,?,?,?,?,?,?)
				
				// "insert into tbl_data_" + day + " values(common_seq.nextval,?,?,?,?,?,?,?,?,?)"
				ps.setString(1, enviroment.getSrcId());
				ps.setString(2, enviroment.getDevId());
				ps.setLong(3, enviroment.getRegionId());
				ps.setString(4, enviroment.getName());
				ps.setLong(5, enviroment.getCount());
				ps.setInt(6, enviroment.getState());
				ps.setDouble(7, enviroment.getData());
				ps.setInt(8, enviroment.getReviceState());
				ps.setDate(9, enviroment.getGatheDate());
				//添加到批处理中
				ps.addBatch();
				num++;
				//如果缓存池中有100条sql语句时就执行批处理，并清空批处理
				if (num % 100 == 0) {
					ps.executeBatch();
					ps.clearBatch();
				}
			}
			//当循环结束后，缓存池中如果还有没执行的sql语句，最后执行一次
			if (ps != null) {
				ps.executeBatch();
				ps.clearBatch();
			}
			connection.commit();
			connection.close();
			logger.info("存储数据成功");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
				//备份数据
				FileBackup.store(FileBackupNameEnum.CLINT_BACKUP_PATH.getPath(), collection);
				connection.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
