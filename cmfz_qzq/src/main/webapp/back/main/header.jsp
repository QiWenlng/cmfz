<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="text-align: center">
    <h1>合力泰管理系统 V1.0</h1>
</div>
<div id="welcome">
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${sessionScope.name}&nbsp;&nbsp;欢迎您！
    <a class="easyui-linkbutton" data-options="iconCls:'icon-man',onClick:openUserLoginDialog, ">登录</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-door_out',onClick:logout,">登出</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-door_out',onClick:registerUser,">注册</a>
</div>

<%--用户登录对话框--%>
<div id="lin"></div>

<%--用户注册对话框--%>
<div id="reg"></div>

<script>
    function openUserLoginDialog() {
        $("#lin").dialog({
            title: '用户登录',
            iconCls: 'icon-man',
            width: 300,
            height: 300,
            draggable: true,  //定义是否能够拖拽窗口。
            href: '/cmfz/back/admin/login.jsp',
            buttons: [{
                text: '确认',
                iconCls: 'icon-man',
                handler: login,
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    //关闭当前对话框
                    $("#lin").dialog('close', true);
                },
            }],
        });
    }

    function login() {
        $("#ll").form('submit', {
            url: '/cmfz/admin/login',
            onSubmit: function () {
                return $("#ll").form('validate');
            },
            success: function (result) {
                var rr = eval("(" + result + ")");
                //data  都是json格式字符串   使用数据时将json转为js对象和数组
                $.messager.show({
                    title: '提示',
                    msg: rr.msg,
                });

                //关闭当前对话框
                $("#lin").dialog('close', true);

                location.href = "/cmfz/back/main/main.jsp";

                //刷新datagrid
                //$("#tt").datagrid('load'); //始终保持在第一页展示
                //$("#main").layout('refresh'); //刷新布局
            }
        });
    }

    function logout() {
        $.get("/cmfz/admin/logout", function (result) {
            //var rr=eval("("+result+")");
            //data  都是json格式字符串   使用数据时将json转为js对象和数组
            $.messager.show({
                title: '提示',
                msg: '登出成功',
                //msg:rr,
            });
            location.href = "/cmfz/back/main/main.jsp";
            //$("#main").layout('refresh');
        });
    }

    function registerUser() {
        $("#reg").dialog({
            title: '注册用户',
            iconCls: 'icon-man',
            width: 500,
            height: 600,
            maximizable: true,
            minimizable: true,
            collapsible: true,
            resizable: false,
            href: '/cmfz/back/admin/register.jsp',
            buttons: [{
                text: '注册',
                iconCls: 'icon-save',
                handler: function () {
                    $("#ff2").form('submit', {
                        url: '/cmfz/admin/register?' + $(this).serialize(),
                        onSubmit: function () {
                            var isValid = $(this).form('validate');
                            if (!isValid) {
                                $.messager.progress('close');	// 如果表单是无效的则隐藏进度条
                            }
                            return isValid;	// 返回false终止表单提交
                        },
                        success: function (result) {
                            var data = eval("(" + result + ")");
                            $.messager.alert("提示", data.msg, "info");

                            //关闭当前对话框
                            $("#reg").dialog('close', true);
                        }
                    });
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    //location.href='/homework_easyui_171111';
                    $("#reg").dialog('close', true);
                }
            }]
        });
    }


    //修改日期默认输入格式formatter必须和parser一起配合使用
    $.fn.datebox.defaults.formatter = function (date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        return y + "/" + m + "/" + d;
    };

    $.fn.datebox.defaults.parser = function (s) {
        if (!s) return new Date();
        var ss = s.split('/');
        var y = parseInt(ss[0], 10);
        var m = parseInt(ss[1], 10);
        var d = parseInt(ss[2], 10);
        if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
            return new Date(y, m - 1, d);
        } else {
            return new Date();
        }
    };
</script>
