<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@include file="util.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>login</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css"
          href="css/style.css"/>
    <link rel="stylesheet" href="/springboot/ems/statics/plugin/message/css/jquery.my-message.1.1.css"/>
    <script type="text/javascript" src="/springboot/ems/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="/springboot/ems/statics/plugin/message/js/jquery.my-message.1.1.js"></script>
    <script type="text/javascript">
        $(function () {

            var message = new MyMessage.message({
                /*默认参数，下面为默认项*/
                iconFontSize: "20px", //图标大小,默认为20px
                messageFontSize: "16px", //信息字体大小,默认为12px
                showTime: 3000, //消失时间,默认为3000
                align: "center", //显示的位置类型center,right,left
                positions: { //放置信息距离周边的距离,默认为10px
                    top: "20%",
                    bottom: "10px",
                    right: "10px",
                    left: "10px"
                },
                type: "normal", //消息的类型，还有success,error,warning等，默认为normal
            });

            $("#inputForm").submit(function () {
                //登录
                $.post("/springboot/user/loginMessage", $(this).serialize(), function (result) {
                    if (result.success) {
                        location.href = "/springboot/emp/queryAll";
                    } else {
                        message.add(result.msg, "warning");
                    }
                }, "JSON");
                return false;//阻止表单提交
            });

            //发送验证码
            $("#mesageCode").click(function () {
                var phone = $(this).prev().val();
                $.post("/springboot/user/sendMessage", {"phone": phone}, function (result) {
                    if (result.success) {
                        message.add(result.msg, "success");
                    } else {
                        message.add(result.msg, "warning");
                    }
                }, "JSON");
            });

        });
    </script>
</head>

<body>
<div id="wrap">
    <div id="top_content">
        <div id="header">
            <div id="rightheader">
                <p>
                    <fmt:formatDate value="<%=new java.util.Date() %>" pattern="yyyy-MM-dd HH:mm:ss"/>
                    <br/>
                </p>
            </div>
            <div id="topheader">
                <h1 id="title">
                    <a href="#">main</a>
                </h1>
            </div>
            <div id="navigation">
            </div>
        </div>
        <div id="content">
            <p id="whereami">
            </p>
            <h1>
                login
            </h1>
            <form id="inputForm" action="" method="post">
                <table cellpadding="0" cellspacing="0" border="0"
                       class="form_table">
                    <tr>
                        <td valign="middle" align="right">
                            用户名:
                        </td>
                        <td valign="middle" align="left">
                            <input type="text" class="inputgri" name="name"/>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right">
                            短信验证码:
                        </td>
                        <td valign="middle" align="left">
                            <input type="text" class="inputgri" name="messageCode"/>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right">
                            手机号:
                        </td>
                        <td valign="middle" align="left">
                            <input type="text" class="inputgri" name="phone"/>
                            <input type="button" id="mesageCode" value="发送验证码... &raquo;"/>
                        </td>
                    </tr>
                </table>
                <p>
                    <input type="submit" class="button" value="登录 &raquo;"/>
                    <input type="button" class="button" onclick="location.href='/springboot/ems/regist.jsp'"
                           value="注册 &raquo;"/>
                </p>
            </form>
        </div>
    </div>
    <div id="footer">
        <div id="footer_bg">
            ABC@126.com
        </div>
    </div>
</div>
</body>
</html>
