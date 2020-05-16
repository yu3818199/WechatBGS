/*
 * 网页输出各时间段查询结果
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

public class showStatistics implements Controller {

    private final String sql = "SELECT speed2,count(*) FROM speed group by speed2 order by speed2";

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mav = new ModelAndView("showStatistics");
        mav.addObject("message", "统计数据");

        List<timeResult> page_result = new ArrayList<>();

        DBConnect db = new DBConnect();

        try {
            PreparedStatement pst = db.conn.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            System.out.println(this.toString() + db.conn.toString());
            while (resultSet.next()) {
                timeResult r = new timeResult();
                r.setMessage(((double) Integer.valueOf(resultSet.getInt(1)) / 10) + "-" + ((double) (resultSet.getInt(1) + 1) / 10) + "秒 共" + resultSet.getString(2) + "人");
                page_result.add(r);
                System.out.println(r.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("异常提醒：" + e);
        }

        db.rollback();
        mav.addObject("timeResult", page_result);

        return mav;
    }
}
