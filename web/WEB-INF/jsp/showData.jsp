<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- meta使用viewport以确保页面可自由缩放 -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">
        <script src="https://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="https://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
        <title>小程序后台数据查询</title>
    </head>
    <body>
        <div data-role="page" id="pageone">
            <div data-role="header">
                <h1>小程序后台数据查询</h1>
                <div data-role="navbar">
                    <ul>
                        <li><a href="#" onclick="show_timeResult()" data-icon="star">每天点击次数</a></li>
                        <li><a href="#" onclick="show_userResult()" data-icon="info">每天访问人数</a></li>
                    </ul>
                </div>
            </div>
            <div data-role="main" class="ui-content">
                <div id="timeResult">
                    <ul data-role="listview">
                        <c:forEach items="${timeResult}" var="result">
                            <li><a href="showDataDetail?date=${result.date}" data-transition="slide">${result.message}</a></li>
                            </c:forEach>
                    </ul>
                </div>
                <div id="userResult">
                    <ul data-role="listview">
                        <c:forEach items="${userResult}" var="result">
                            <li><a href="showDataUser?date=${result.date}" data-transition="slide">${result.message}</a></li>
                            </c:forEach>
                    </ul>
                </div>
            </div>
            <div data-role="footer">
                <h1> </h1>
            </div>
        </div> 
    </body>
</html>
<script>
    function show_timeResult()
    {
        $("#timeResult").show();
        $("#userResult").hide();
    }
    function show_userResult()
    {
        $("#timeResult").hide();
        $("#userResult").show();
    }
    $("#timeResult").show();
    $("#userResult").hide();
</script>