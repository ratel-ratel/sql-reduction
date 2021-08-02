package com.neo.util;

/**
 * Created by ratel on 2021/8/2.
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 运行Sql脚本
 * sql脚本放在resources下的sql文件夹下
 */
@Slf4j
public final class RunSqlScript {
    /**
     * <p>运行指定的sql脚本
     * @param sqlFileName 需要执行的sql脚本的名字
     */
    public static void run(String sqlFileName) throws IllegalAccessException, InstantiationException {
        try {
            // 获取数据库相关配置信息
            Properties props = Resources.getResourceAsProperties("db.properties");

            // jdbc 连接信息: 注: 现在版本的JDBC不需要配置driver，因为不需要Class.forName手动加载驱动
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            // 建立连接
            Connection conn = DriverManager.getConnection(url, username, password);


            // 创建ScriptRunner，用于执行SQL脚本
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setErrorLogWriter(null);
            runner.setLogWriter(null);
            // 执行SQL脚本
            runner.runScript(Resources.getResourceAsReader("sql/" + sqlFileName + ".sql"));

            // 关闭连接
            conn.close();

            // 若成功，打印提示信息
            log.info("====== SUCCESS ======");
        } catch (IOException | SQLException  |ClassNotFoundException e ) {
            e.printStackTrace();
        }
    }
}
