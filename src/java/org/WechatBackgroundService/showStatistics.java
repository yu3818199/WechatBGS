/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.WechatBackgroundService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.WechatBackgroundService.db.JDBCUtil;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author yu_qi
 */
public class showStatistics implements Controller {

    private final String sql = "SELECT speed2,count(*) FROM speed where speed2<10 group by speed2 order by speed2";

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mav = new ModelAndView("showStatistics");
        mav.addObject("message", "统计数据");

        List<timeResult> page_result = new ArrayList<>();

        try {

            Connection connection
                    = JDBCUtil.beginTransaction();
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            System.out.println(this.toString() + connection.toString());
            while (resultSet.next()) {
                timeResult r = new timeResult();
                r.setMessage("0." + (Integer.valueOf(resultSet.getString(1)) - 1) + "-0." + resultSet.getString(1) + "秒 共" + resultSet.getString(2) + "人");
                page_result.add(r);
                System.out.println(r.getMessage());
            }
            //System.out.println(this.toString() + JDBCUtil.getConnection().toString());
            JDBCUtil.rollbackTransaction();
        } catch (SQLException e) {
            System.out.println("异常提醒：" + e);
        }

        mav.addObject("timeResult", page_result);

        return mav;
    }
}
