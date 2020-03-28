/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.WechatBackgroundService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.WechatBackgroundService.db.JDBCUtil;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author yu_qi
 */
public class commitSpeed implements Controller {

    private final String sql = "insert into speed(ip,speed,update_time,speed2) values(?,?,?,?);";

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String time = request.getParameter("time");
        String ip = request.getRemoteAddr();

        ModelAndView mav = new ModelAndView("commitSpeed");

        if (time == null) {
            mav.addObject("message", "提交时间为空");
            return mav;
        }

        try {
            Connection connection
                    = JDBCUtil.beginTransaction();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, ip);
            stmt.setString(2, time);
            stmt.setTimestamp(3, (new Timestamp(System.currentTimeMillis())));
            stmt.setInt(4, Double.valueOf(Double.valueOf(time) * 10).intValue());
            System.out.print(connection.toString() + "执行语句：" + sql + ",");
            int result = stmt.executeUpdate();// 返回值代表收到影响的行数
            System.out.println(result + "行数据受影响");
            // 大于100秒不提交数据库
            if (Double.valueOf(time) < 100) {
                JDBCUtil.commitTransaction();
            } else {
                JDBCUtil.rollbackTransaction();
            }
        } catch (SQLException e) {
            System.out.println("异常提醒：" + e);
        }

        mav.addObject("message", "提交成功" + time);
        return mav;
    }
}
