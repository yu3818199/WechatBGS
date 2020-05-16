/*
 * 每次访问次数及用户数汇总统计
 */
package org.speed;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.speed.db.DBConnect;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class showData implements Controller {

    private final String sql_time = "select date(update_time) d,count(*) n from speed group by d order by d desc limit 30";
    private final String sql_user = "select d,count(*) n from (select date(update_time) d,ip,count(*) n from speed group by d ,ip ) t1 group by d desc limit 30";

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mav = new ModelAndView("showData");
        mav.addObject("message", "统计数据");

        DBConnect db = new DBConnect();

        List<showDataResult> page_result_time = new ArrayList<>();
        try {
            PreparedStatement pst = db.conn.prepareStatement(sql_time);
            ResultSet resultSet = pst.executeQuery();
            System.out.println(this.toString() + db.conn.toString());
            while (resultSet.next()) {
                showDataResult r = new showDataResult();
                r.setMessage(resultSet.getString(1) + " 共" + resultSet.getString(2) + "次");
                r.setDate(resultSet.getString(1));
                page_result_time.add(r);
                System.out.println(r.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("异常提醒：" + e);
        }
        mav.addObject("timeResult", page_result_time);
        System.out.println("次数统计结束");

        List<showDataResult> page_result_user = new ArrayList<>();
        try {
            PreparedStatement pst = db.conn.prepareStatement(sql_user);
            ResultSet resultSet = pst.executeQuery();
            System.out.println(this.toString());
            while (resultSet.next()) {
                showDataResult r = new showDataResult();
                r.setMessage(resultSet.getString(1) + " 共" + resultSet.getString(2) + "人");
                r.setDate(resultSet.getString(1));
                page_result_user.add(r);
                System.out.println(r.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("异常提醒：" + e);
        }
        mav.addObject("userResult", page_result_user);
        System.out.println("人数统计结束");

        db.rollback();

        return mav;
    }
}
