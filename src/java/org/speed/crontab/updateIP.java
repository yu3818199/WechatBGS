/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.speed.crontab;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.speed.db.JDBCUtil;

public class updateIP {

    /* mysql 语法 */
    private final String sql = "insert into iplocale select a.ip,ip.locale,ip.isp from (select ip from speed where ip in (select ip from speed where ip not in (select ipdec from iplocale)) GROUP BY ip) a , ip where LPAD(conv(inet_aton(a.ip),10,16),8,0) between ip.iphex1 and ip.iphex2";

    public void execute() {

        System.out.println("启动任务 " + this.toString() + " " + (new Date(System.currentTimeMillis())).toLocaleString());

        try {
            Connection connection
                    = JDBCUtil.beginTransaction();
            PreparedStatement stmt = connection.prepareStatement(sql);
            System.out.print(connection.toString() + "执行语句：" + sql + ",");
            int result = stmt.executeUpdate();// 返回值代表收到影响的行数
            System.out.println(result + "行数据受影响");
            JDBCUtil.commitTransaction();
        } catch (SQLException e) {
            System.out.println("异常提醒：" + e);
            try {
                JDBCUtil.rollbackTransaction();
            } catch (SQLException ex) {
                System.out.println(this.toString() + "rollbackTransaction error " + ex.toString());
            }
        }
    }
}
