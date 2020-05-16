/*
 *  提交点击速度
 */
package org.speed;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.speed.db.DBConnect;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class commitSpeed implements Controller {

    private final String sql = "insert into speed(ip,speed,update_time,speed2) values(?,?,?,?)";

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String time = request.getParameter("time");
        String ip = request.getRemoteAddr();

        ModelAndView mav = new ModelAndView("commitSpeed");

        if (time == null) {
            mav.addObject("message", "提交时间为空");
            return mav;
        }

        DBConnect db = new DBConnect();
        try {
            PreparedStatement stmt = db.conn.prepareStatement(sql);
            stmt.setString(1, ip);
            stmt.setString(2, time);
            stmt.setTimestamp(3, (new Timestamp(System.currentTimeMillis())));
            stmt.setInt(4, Double.valueOf(Double.valueOf(time) * 10).intValue());
            System.out.print("执行语句：" + sql + ",");
            int result = stmt.executeUpdate();// 返回值代表收到影响的行数
            System.out.println(result + "行数据受影响");
            // 大于1000秒不提交数据库
            if (Double.valueOf(time) < 1000) {
                db.commit();
            } else {
                db.rollback();
            }
        } catch (SQLException e) {
            System.out.println("异常提醒：" + e);
            db.rollback();
        }

        mav.addObject("message", "提交成功" + time);
        return mav;
    }
}
