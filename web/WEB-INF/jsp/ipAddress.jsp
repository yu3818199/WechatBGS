<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- meta使用viewport以确保页面可自由缩放 -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">
        <script src="https://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="https://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>

        <title>查询结果</title>
    </head>
    <body>
         <div data-role="page" id="pageone">
            <div data-role="main" class="ui-content">
                <h2>查询结果</h2>
                <ul data-role="listview">
                        <li><a href="#">${message}</a></li>
                </ul>
            </div>
        </div> 
    </body>
</html>
