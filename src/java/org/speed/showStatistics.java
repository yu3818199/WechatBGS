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

/**
 *
 * @author yu_qi
 */
public class showStatistics implements Controller {

    private final String sql = "SELECT speed2,count(*) FROM speed group by speed2 order by speed2";

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mav = new ModelAndView("showStatistics");
        mav.addObject("message", "统计数据");

        List<timeResult> page_result = new ArrayList<>();
        Connection connection
                = JDBCUtil.beginTransaction();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            System.out.println(this.toString() + connection.toString());
            while (resultSet.next()) {
                timeResult r = new timeResult();
                r.setMessage(((double) Integer.valueOf(resultSet.getInt(1)) / 10) + "-" + ((double) (resultSet.getInt(1) + 1) / 10) + "秒 共" + resultSet.getString(2) + "人");
                page_result.add(r);
                System.out.println(r.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("异常提醒：" + e);
        }

        JDBCUtil.rollbackTransaction();
        mav.addObject("timeResult", page_result);

        return mav;
    }
}
