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
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.speed.db.JDBCUtil;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author yu_qi
 */
public class ipAddress implements Controller {

    private final String sql = "select * from ip where ? between iphex1 and iphex2";

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //String ip = ipToHex(request.getRemoteAddr());
        String ip = ipToHex(request.getRemoteAddr());
        System.out.println("当前地址：" + ip + "(" + request.getRemoteAddr() + ")");
        ModelAndView mav = new ModelAndView("ipAddress");

        try {
            Connection connection
                    = JDBCUtil.beginTransaction();
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, ip);
            ResultSet resultSet = pst.executeQuery();
            if (!resultSet.next()) {
                mav.addObject(
                        "message", "本地址无记录");
            }
            System.out.println(resultSet.getString("locale") + " " + resultSet.getString("isp"));
            mav.addObject(
                    "message", resultSet.getString("locale") + " " + resultSet.getString("isp"));
        } catch (SQLException e) {
            System.out.println("异常提醒：" + e);
            mav.addObject(
                    "message", "查询失败");
        }

        JDBCUtil.rollbackTransaction();

        return mav;
    }

    private String ipToHex(String ipString) {
        String[] ip = ipString.split("\\.");
        StringBuilder hexip = new StringBuilder();
        for (String str : ip) {
            if (Integer.toHexString(Integer.parseInt(str)).length() == 1) {
                hexip.append("0");
            }
            hexip.append(Integer.toHexString(Integer.parseInt(str)).toUpperCase());
        }
        return hexip.toString();
    }

}
