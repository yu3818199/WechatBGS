/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.speed.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtil {

    private static DataSource ds = new ComboPooledDataSource("database");

    /**
     * 它为null表示没有事务 它不为null表示有事务 当开启事务时，需要给它赋值 当结束事务时，需要给它赋值为null
     * 并且在开启事务时，让dao的多个方法共享这个Connection
     */
    private static ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();

    /**
     * 开启事务,返回连接
     */
    public static Connection beginTransaction() throws SQLException {

        /* 当前线程存在有效连接 */
        if (threadConnection.get() != null) {
            System.out.println("已经开启了事务，不能重复开启！先回滚");
            rollbackTransaction();
        }

        /* 从c3p0获取有效连接 */
        Connection con = ds.getConnection();//获取一个有效连接
        con.setAutoCommit(false);//设置为手动提交
        threadConnection.set(con);//把当前事务连接放到threadConnection中
        return con;
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() throws SQLException {
        Connection con = threadConnection.get();//获取当前线程的事务连接
        //System.out.println("commitTransaction"+tl.toString());
        if (con == null) {
            System.out.println("没有事务不能回滚！");
            return;
        }
        con.commit();//提交事务
        con.close();//关闭连接
        con = null;//表示事务结束！
        threadConnection.remove();
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() throws SQLException {
        Connection con = threadConnection.get();//获取当前线程的事务连接
        //System.out.println("rollbackTransaction"+tl.toString());
        if (con == null) {
            System.out.println("没有事务不能回滚！");
            return ;
        }
        con.rollback();
        con.close();
        con = null;
        threadConnection.remove();
    }

    /**
     * 获取当前连接
     */
    public static Connection getConnection() throws SQLException {

        Connection con = threadConnection.get();//获取当前线程的事务连接
        if (con != null) {
            return con;
        }
        throw new SQLException("没有启动事务！");
    }

}
