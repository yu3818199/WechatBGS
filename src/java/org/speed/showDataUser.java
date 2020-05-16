/*
 * 当日访问用户详细信息查询
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

public class showDataUser implements Controller {

    private final String sql = "select (select concat(locale,isp) from iplocale where ipdec=speed.ip ) locale, concat(left(ip,4),\"*\",right(ip,4)) ip ,count(*) c from speed where date(update_time)=? group by ip ";

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mav = new ModelAndView("showDataUser");
        String date = request.getParameter("date");
        mav.addObject("date", date);
        System.out.println("查询" + date + "数据");

        DBConnect db = new DBConnect();

        List<showDataResult> page_result = new ArrayList<>();
        try {
            PreparedStatement pst = db.conn.prepareStatement(sql);
            pst.setString(1, date);
            ResultSet resultSet = pst.executeQuery();
            System.out.println(this.toString() + db.conn.toString());
            while (resultSet.next()) {
                showDataResult r = new showDataResult();
                r.setMessage(resultSet.getString(1) + "（" + resultSet.getString(2) + "）点击" + resultSet.getString(3) + "次");
                page_result.add(r);
                System.out.println(r.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("异常提醒：" + e);
        }
        mav.addObject("dataResult", page_result);
        System.out.println("次数统计结束");

        db.rollback();

        return mav;
    }
}
