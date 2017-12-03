<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<h2 align="center" style="font-weight: bold">欢迎注册新用户</h2><br/>
<form id="ff2" method="post" action="">
    <div style="position:absolute;left:25%;top:20%;">
        <div>
            <input id="name" name="name" class="easyui-textbox"
                   data-options="iconCls:'icon-man',
                       iconAlign:'left',
                       width:250,
                       height:30,
                       prompt:'请输入用户名',
                       required:true,
                       validType:['loginName'],
                "/><br/><br/>
        </div>
        <div>
            <input id="password" name="password" class="easyui-passwordbox"
                   data-options="
                       width:250,
                       height:30,
                       iconAlign:'left',
                       prompt:'请输入密码',
                       required:true,
                       validType:['length[6,20]',]
                "/><br/><br/>
        </div>
        <div>
            <input id="repassword" class="easyui-passwordbox"
                   required="required"
                   validType="equalTo['#password']"
                   data-options="
                       width:250,
                       height:30,
                       iconAlign:'left',
                       prompt:'请确认密码',
                       "/><br/><br/>
        </div>
        <div>
            <table style="width: 255px; height: 30px;">
                <tr>
                    <td width="155px">
                        <input id="verifyCode" name="code" class="easyui-textbox"
                               data-options="width:100,height:30,
                           iconAlign:'right',prompt:'请输入验证码'"/>
                    </td>
                    <td width="100px">
                        <img id="code" style="width: 100px; height: 30px;" src="/cmfz/admin/getImage"/>
                    </td>
                </tr>
            </table>
            <br/><br/>
        </div>
    </div>
</form>

