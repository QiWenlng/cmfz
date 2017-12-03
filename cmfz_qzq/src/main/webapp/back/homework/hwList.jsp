<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#hw").datagrid({
            url: '/cmfz/homework/queryAll?phone=17611003900',

            striped: true,  //是否显示斑马线效果。

            fit: true,//当设置为true的时候面板大小将自适应父容器。下面的例子显示了一个面板，可以自动在父容器的最大范围内调整大小

            toolbar: '#hwTable',

            remoteSort: false, //定义从服务器对数据进行排序。

            pagination: true,  //如果为true，则在DataGrid控件底部显示分页工具栏。加入分页工具栏之后自动传递两个参数  page 当前页   rows:每页显示条数

            fitColumns: true,  //真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。根据列中指定width属性 百分比

            pagePosition: 'bottom', //定义分页工具栏的位置。可用的值有：'top','bottom','both'。

            rownumbers: true,  //如果为true，则显示一个行号列。`

            multiSort: true, //定义是否允许多列排序

            pageList: [5, 10, 15, 20, 500],

            columns: [[
                {title: "编号", field: "hwId", width: 240, align: 'center'},
                {title: "功课名称", field: "hwName", width: 120, align: 'center', sortable: true},
                {title: "状态", field: "hwStatus", width: 100, align: 'center', sortable: true},
                {title: "所属用户Id", field: "userId", width: 120, align: 'center'},
                {
                    title: "操作", field: "options", width: 400, align: 'center',
                    formatter: function (value, row, index) {
                        return "<a class='del' onclick=\"removeHw('" + row.hwId + "')\" data-options=\"plain:true,iconCls:'icon-20130408025545236_easyicon_net_30'\">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                            "<a class='del' onclick=\"openHwModifyDialog('" + row.hwId + "')\" data-options=\"plain:true,iconCls:'icon-edit'\">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                            "<a class='del' onclick=\"openCounterQueryDialog('" + row.hwId + "')\" data-options=\"plain:true,iconCls:'icon-edit'\">查看计数器</a>";
                    }
                },
            ]],
            onLoadSuccess: function (data) {
                console.log(data);
                //渲染已经在页面形成按钮为linkbutton
                $(".del").linkbutton();
            }
        });
    });

    //创建展示计数器对话框
    function openCounterQueryDialog(hwId) {
        $.get("/cmfz/counter/savehwId", {"hwId": hwId, "phone": "17611003900"}, function () {

        }, "JSON");

        //判断选项卡容器标题是否存在
        if (!$("#tab").tabs('exists', '计数管理')) {
            //不存在则添加选项卡
            $("#tab").tabs('add', {
                title: '计数管理',
                iconCls: 'icon-man',
                //在设置为true的时候，选项卡面板将显示一个关闭按钮，在点击的时候会关闭选项卡面板。
                closable: true,
                href: '/cmfz/back/counter/counterList.jsp',
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
            $hw.tabs('select', title);
        }
    }


    //创建添加对话框
    function openHwSaveDialog() {

        $("#hwSave").dialog({
            title: '添加功课',
            iconCls: 'icon-man',
            width: 200,
            height: 150,
            draggable: true,  //定义是否能够拖拽窗口。
            href: '/cmfz/back/homework/saveHw.jsp',
            buttons: [{
                text: '保存',
                iconCls: 'icon-save',
                handler: saveHw,
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    //关闭当前对话框
                    $("#hwSave").dialog('close', true);
                },
            }],
        });
    }

    //添加方法
    function saveHw() {
        $("#hwSaveForm").form('submit', {
            url: '/cmfz/homework/save',
            onSubmit: function () {
                //验证表单元素
                return $("#hwSaveForm").form('validate');
            },
            success: function (result) {
                var rr = eval("(" + result + ")");
                //data  都是json格式字符串   使用数据时将json转为js对象和数组
                $.messager.show({
                    title: '提示',
                    msg: rr.msg,
                });

                //关闭当前对话框
                $("#hwSave").dialog('close', true);

                //刷新datagrid
                //$("#hw").datagrid('load'); //始终保持在第一页展示
                $("#hw").datagrid('reload'); //始终保持在当前页展示
            },
        });
    }


    //删除
    function removeHw(hwId) {
        $.messager.confirm('提示', '确定要注销此功课吗？', function (result) {
            if (result) {
                $.post('/cmfz/homework/remove', {'hwId': hwId}, function (data) {
                    var rr = eval("(" + data + ")");
                    $.messager.show({
                        title: '提示',
                        msg: rr.msg,
                    });

                    //更新页面datagrid
                    $("#hw").datagrid('reload');
                }, 'JSON');
            }
        });
    }

    //添加修改用户对话框
    function openHwModifyDialog(hwId) {
        $("#hwModify").dialog({
            title: '修改用户信息',
            iconCls: 'icon-man',
            width: 400,
            height: 500,
            draggable: true,  //定义是否能够拖拽窗口。
            href: '/cmfz/back/homework/modifyHw.jsp?hwId=' + hwId,
            buttons: [{
                text: '确认',
                iconCls: 'icon-man',
                handler: modifyHw,
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    //关闭当前对话框
                    $("#hwModify").dialog('close', true);
                },
            }],
        });
    }

    //修改用户方法
    function modifyHw() {
        $("#modifyHwForm").form('submit', {
            url: '/cmfz/homework/modify',
            onSubmit: function () {
                return $("#hwSaveForm").form('validate');
            },
            success: function (result) {
                var rr = eval("(" + result + ")");
                //data  都是json格式字符串   使用数据时将json转为js对象和数组
                $.messager.show({
                    title: '提示',
                    msg: rr.msg,
                });

                //关闭当前对话框
                $("#hwModify").dialog('close', true);

                //刷新datagrid
                //$("#hw").datagrid('load'); //始终保持在第一页展示
                $("#hw").datagrid('reload'); //始终保持在当前页展示
            },
        });
    }

    //添加修改用户对话框
    function importHwDialog() {
        $("#hwModify").dialog({
            title: '导入Excell表',
            iconCls: 'icon-man',
            width: 200,
            height: 150,
            draggable: true,  //定义是否能够拖拽窗口。
            href: '/cmfz/back/homework/excellHw.jsp',
            buttons: [{
                text: '确认',
                iconCls: 'icon-man',
                handler: importHw,
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    //关闭当前对话框
                    $("#hwModify").dialog('close', true);
                },
            }],
        });
    }

    function importHw() {
        $("#importHwForm").form('submit', {
            url: '/cmfz/homework/importHomework',
            onSubmit: function () {
                return $("#hwSaveForm").form('validate');
            },
            success: function (result) {
                var rr = eval("(" + result + ")");
                //data  都是json格式字符串   使用数据时将json转为js对象和数组
                $.messager.show({
                    title: '提示',
                    msg: rr.msg,
                });

                //关闭当前对话框
                $("#hwModify").dialog('close', true);

                //刷新datagrid
                //$("#hw").datagrid('load'); //始终保持在第一页展示
                $("#hw").datagrid('reload'); //始终保持在当前页展示
            },
        });
    }


    function exportHw() {
        $.post('/cmfz/homework/exportHomework', function (data) {
            $.messager.show({
                title: '提示',
                msg: data.msg,
            });

            //更新页面datagrid
            $("#hw").datagrid('reload');
        }, 'JSON');
    }


    //处理搜索
    function query(value, name) {
        $("#hw").datagrid('load', '/cmfz/homework/searcher?attribute=' + name + '&value=' + value);
    }

</script>


<%--创建datagrid--%>
<table id="hw"></table>


<%--工具栏标签--%>
<div id="hwTable">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true,onClick:openHwSaveDialog,">添加</a>

    <%--搜索框--%>
    <input id="hwSearch" class="easyui-searchbox"
           data-options="
               searcher:query,
               prompt:'请输入查询条件....',
               menu:'#hwmm',
               width:200"
    />
    <%--搜索关键字下拉菜单--%>
    <div id="hwmm" data-options="">
        <div data-options="name:'name',">姓名</div>
        <div data-options="name:'bir'">生日</div>
        <div data-options="name:'id'">编号</div>
    </div>

    <a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true,onClick:exportHw,">导出Excell表</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true,onClick:importHwDialog,">导入Excell表</a>
</div>

<%--用户保存对话框--%>
<div id="hwSave"></div>

<%--用户修改对话框--%>
<div id="hwModify"></div>

<%--计数器对话框--%>
<div id="count"></div>


