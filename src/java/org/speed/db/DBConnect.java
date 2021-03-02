/*
 *  数据库类
 */
package org.speed.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBConnect {

    private static DataSource ds = new ComboPooledDataSource("database");

    public Connection conn;

    public DBConnect() {
        try {
            conn = ds.getConnection();//获取一个有效连接
            conn.setAutoCommit(false);//设置为手动提交
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 提交事务
     * @throws java.sql.SQLException
     */
    public void commit() throws SQLException {
        conn.commit();//提交事务
        conn.close();//关闭连接
        conn = null;
    }

    /**
     * 回滚事务
     * @throws java.sql.SQLException
     */
    public void rollback() throws SQLException {
        conn.rollback();
        conn.close();
        conn = null;
    }


}
