/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.speed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.speed.db.JDBCUtil;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class showDataDetail implements Controller {

    private final String sql = "select  (select concat(locale,isp) from iplocale where ipdec=speed.ip ) locale,date_format(update_time, '%H:%i:%s'),speed  from speed where date(update_time)=? order by update_time desc";

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mav = new ModelAndView("showDataDetail");
        String date = request.getParameter("date");
        mav.addObject("date", date);
        System.out.println("查询" + date + "数据");

        Connection connection
                = JDBCUtil.beginTransaction();

        List<showDataResult> page_result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, date);
            ResultSet resultSet = pst.executeQuery();
            System.out.println(this.toString() + connection.toString());
            while (resultSet.next()) {
                showDataResult r = new showDataResult();
                r.setMessage(resultSet.getString(2) + " " + resultSet.getString(3) + "秒 " + resultSet.getString(1));
                page_result.add(r);
                System.out.println(r.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("异常提醒：" + e);
        }
        mav.addObject("dataResult", page_result);
        System.out.println("次数统计结束");

        JDBCUtil.rollbackTransaction();

        return mav;
    }
}
