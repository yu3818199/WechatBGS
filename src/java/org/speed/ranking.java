/*
 * 输入速度，返回排名
 */
package org.speed;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.speed.db.DBConnect;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ranking implements Controller {

    private final String sql = "select a,b,concat(round((b-a)/b*100,0),'%') c from (select (SELECT count(*)+1 FROM speed WHERE speed<?) a ,count(*) b from speed) t1";

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mav = new ModelAndView("ranking");
        String time = request.getParameter("time");

        DBConnect db = new DBConnect();
        try {
            PreparedStatement pst = db.conn.prepareStatement(sql);
            pst.setString(1, time);
            ResultSet resultSet = pst.executeQuery();
            if (!resultSet.next()) {
                mav.addObject("message", "无记录");
            }
            rankingData data = new rankingData();
            System.out.println(resultSet.getString("a") + " " + resultSet.getString("b") + " " + resultSet.getString("c"));
            data.setA(resultSet.getString("a"));
            data.setB(resultSet.getString("b"));
            data.setC(resultSet.getString("c"));
            JSONArray jsonArray = JSONArray.fromObject(data);
            mav.addObject("message", jsonArray.toString());
        } catch (Exception e) {
            System.out.println("异常提醒：" + e);
            mav.addObject("message", "查询失败");
        }

        db.rollback();

        return mav;

    }
}
