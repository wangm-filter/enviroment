package com.briup.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 创建连接池对象工具类
 * 
 * @author ASUS
 */
public class ConnectionUtils {
    //声明连接池
    private static DruidDataSource dataSource = null;

    //让连接池只创建一次
    static {
        /*
         * 读取properties文件中的数据
         * 可以灵活的更改文件中的数据
         * 把写死的数据写活
         */
        //ConnectionUtils.class.getClassLoader().getResource("jdbc.properties").getPath()
        //"src/main/resources/jdbc.properties"
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(ConnectionUtils.class.getClassLoader().getResource("jdbc.properties").getPath()));

            dataSource = new DruidDataSource();
            dataSource.setDriverClassName(properties.getProperty("jdbc.driverClassName"));
            dataSource.setUrl(properties.getProperty("jdbc.url"));
            dataSource.setUsername(properties.getProperty("jdbc.username"));
            dataSource.setPassword(properties.getProperty("jdbc.password"));

            dataSource.setInitialSize(Integer.parseInt(properties.getProperty("jdbc.init")));
            dataSource.setMaxActive(Integer.parseInt(properties.getProperty("jdbc.max")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(boolean autoCommit) throws SQLException {
        Connection connection = dataSource.getConnection();

        connection.setAutoCommit(autoCommit);
        return connection;
    }

    public static Connection getConnection() throws SQLException {
        return getConnection(false);
    }
}
