<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/cmfz/back/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/cmfz/back/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/cmfz/back/easyui/themes/IconExtension.css">

    <link rel="stylesheet" href="/cmfz/back/kindeditor/themes/default/default.css"/>
    <link rel="stylesheet" href="/cmfz/back/kindeditor/plugins/code/prettify.css"/>

    <script type="text/javascript" src="/cmfz/back/easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="/cmfz/back/easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/cmfz/back/easyui/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/cmfz/back/easyui/js/jquery-easyui-validater.js"></script>
    <script src="/cmfz/back/echarts/echarts.min.js"></script>

    <script charset="utf-8" src="/cmfz/back/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="/cmfz/back/kindeditor/lang/zh-CN.js"></script>
    <script charset="utf-8" src="/cmfz/back/kindeditor/plugins/code/prettify.js"></script>
    <script>

        $(function () {

            //先获取后台数据
            $.post("/cmfz/menu/queryAll", function (result) {
                //遍历以及菜单
                $.each(result, function (index, itemMenu) {
                    //设置主题内容的外层div
                    var content = "<div style='text-align: center'>";
                    //遍历所有二级菜单
                    $.each(itemMenu.children, function (i, child) {
                        var con = child.name + "#" + child.iconCls + "#" + child.href;
                        content += "<div onclick=\"addTabs('" + con + "')\" style='width: 90%;margin:5px 0px 5px 0px;border: 1px #95B8E7 solid;' class='easyui-linkbutton'  " +
                            " data-options=\"plain:true,iconCls:'" + child.iconCls + "'\" >" + child.name + "</div><br/>";
                    });
                    //主题内容外层div尾部
                    content += "</div>";
                    //追加面板
                    $("#aa").accordion('add', {
                        title: itemMenu.name,
                        iconCls: itemMenu.iconCls,
                        content: content,
                    });
                });
            }, "JSON");
        });


        //添加选项卡容器
        function addTabs(content) {
            var conArray = content.split("#");
            //标题
            var title = conArray[0];
            //图标
            var iconCls = conArray[1];
            //页面内容路径
            var href = conArray[2];

            //判断选项卡容器标题是否存在
            if (!$("#tab").tabs('exists', title)) {
                //不存在则添加选项卡
                $("#tab").tabs('add', {
                    title: title,
                    iconCls: iconCls,
                    //在设置为true的时候，选项卡面板将显示一个关闭按钮，在点击的时候会关闭选项卡面板。
                    closable: true,
                    href: href,
                    tools: [{
                        iconCls: 'icon-reload',
                        handler: function () {
                            //获取当前选中面板
                            var tab = $("#tab").tabs('getSelected');
                            //调用panel刷新方法
                            tab.panel('refresh');
                        }
                    }],

                });
            } else {
                //存在选中当前选项卡
                $("#tab").tabs('select', title);
            }
        }


    </script>
</head>
<body class="easyui-layout" id="main">
<%--头部栏--%>
<div data-options="region:'north',collapsible:false,href:'/cmfz/back/main/header.jsp',split:false"
     style="height:100px;"></div>
<%--中间左栏--%>
<div data-options="region:'west',collapsible:false,title:'菜单',split:false" style="width:200px;">
    <%--用来产生accordion控件的div--%>
    <div id="aa" class="easyui-accordion" data-options="fit:true"></div>
</div>

<%--中间主页栏--%>
<div data-options="region:'center',title:'主页'" style="padding:5px;">
    <%--选项卡容器--%>
    <div id="tab" class="easyui-tabs" data-options="fit:true"></div>
</div>

<%--底部--%>
<div data-options="region:'south',split:false,href:'/cmfz/back/main/footer.jsp' " style="height: 50px"></div>
</body>
</html>
