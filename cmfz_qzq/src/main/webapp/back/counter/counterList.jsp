<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#counter").datagrid({
            url: '/cmfz/counter/query',

            striped: true,  //是否显示斑马线效果。

            fit: true,//当设置为true的时候面板大小将自适应父容器。下面的例子显示了一个面板，可以自动在父容器的最大范围内调整大小

            toolbar: '#counterTable',

            remoteSort: false, //定义从服务器对数据进行排序。

            pagination: true,  //如果为true，则在DataGrid控件底部显示分页工具栏。加入分页工具栏之后自动传递两个参数  page 当前页   rows:每页显示条数

            fitColumns: true,  //真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。根据列中指定width属性 百分比

            pagePosition: 'bottom', //定义分页工具栏的位置。可用的值有：'top','bottom','both'。

            rownumbers: true,  //如果为true，则显示一个行号列。`

            multiSort: true, //定义是否允许多列排序

            pageList: [5, 10, 15, 20, 500],

            columns: [[
                {title: "编号", field: "counterId", width: 240, align: 'center'},
                {title: "功课编号", field: "hwId", width: 120, align: 'center', sortable: true},
                {title: "数值", field: "count", width: 100, align: 'center', sortable: true},
                {title: "所属用户Id", field: "userId", width: 120, align: 'center'},
                {title: "修改日期", field: "updateDate", width: 100, align: 'center', sortable: true},
                {
                    title: "操作", field: "options", width: 200, align: 'center',
                    formatter: function (value, row, index) {
                        return "<a class='del' onclick=\"removeCounter('" + row.counterId + "')\" data-options=\"plain:true,iconCls:'icon-20130408025545236_easyicon_net_30'\">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                            "<a class='del' onclick=\"counterModifyDialog('" + row.counterId + "')\" data-options=\"plain:true,iconCls:'icon-edit'\">增加计数</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                            "<a class='del' onclick=\"openCounterModifyDialog('" + row.counterId + "')\" data-options=\"plain:true,iconCls:'icon-edit'\">修改计数</a>";
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

    //创建添加对话框
    function openCounterSaveDialog() {
        $("#counterSave").dialog({
            title: '添加计数器',
            iconCls: 'icon-man',
            width: 200,
            height: 150,
            draggable: true,  //定义是否能够拖拽窗口。
            href: '/cmfz/back/counter/saveCounter.jsp',
            buttons: [{
                text: '保存',
                iconCls: 'icon-save',
                handler: saveCounter,
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    //关闭当前对话框
                    $("#counterSave").dialog('close', true);
                },
            }],
        });
    }

    //添加方法
    function saveCounter() {
        $("#counterSaveForm").form('submit', {
            url: '/cmfz/counter/save',
            onSubmit: function () {
                //验证表单元素
                return $("#counterSaveForm").form('validate');
            },
            success: function (result) {
                var rr = eval("(" + result + ")");
                //data  都是json格式字符串   使用数据时将json转为js对象和数组
                $.messager.show({
                    title: '提示',
                    msg: rr.msg,
                });

                //关闭当前对话框
                $("#counterSave").dialog('close', true);

                //刷新datagrid
                //$("#tt").datagrid('load'); //始终保持在第一页展示
                $("#counter").datagrid('reload'); //始终保持在当前页展示
            },
        });
    }


    //删除
    function removeCounter(counterId) {
        $.messager.confirm('提示', '确定要注销此计数器吗？', function (result) {
            if (result) {
                $.post('/cmfz/counter/remove', {'counterId': counterId}, function (data) {
                    $.messager.show({
                        title: '提示',
                        msg: data.msg,
                    });

                    //更新页面datagrid
                    $("#counter").datagrid('reload');
                }, 'JSON');
            }
        });
    }

    //增加计数方法  单个增加
    function counterModifyDialog(counterId) {
        $.post("/cmfz/counter/modify", {"counterId": counterId, "counterStatus": "2"}, function (data) {
            //更新页面datagrid
            $("#counter").datagrid('reload');
        }, "JSON");
    }

    //添加修改用户对话框
    function openCounterModifyDialog(counterId) {
        $("#counterUpdate").dialog({
            title: '修改用户信息',
            iconCls: 'icon-man',
            width: 400,
            height: 500,
            draggable: true,  //定义是否能够拖拽窗口。
            href: '/cmfz/back/counter/modifyCounter.jsp?counterId=' + counterId,
            buttons: [{
                text: '确认',
                iconCls: 'icon-man',
                handler: modifyCounter,
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    //关闭当前对话框
                    $("#counterUpdate").dialog('close', true);
                },
            }],
        });
    }

    //修改用户方法
    function modifyCounter() {
        $("#modifyCounterForm").form('submit', {
            url: '/cmfz/counter/modify',
            onSubmit: function () {
                return $("#counterSaveForm").form('validate');
            },
            success: function (result) {
                var rr = eval("(" + result + ")");
                //data  都是json格式字符串   使用数据时将json转为js对象和数组
                $.messager.show({
                    title: '提示',
                    msg: rr.msg,
                });

                //关闭当前对话框
                $("#counterUpdate").dialog('close', true);

                //刷新datagrid
                //$("#tt").datagrid('load'); //始终保持在第一页展示
                $("#counter").datagrid('reload'); //始终保持在当前页展示
            },
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

    //处理搜索
    function query(value, name) {
        $("#counter").datagrid('load', '/cmfz/homework/searcher?attribute=' + name + '&value=' + value);
    }

</script>


<%--创建datagrid--%>
<table id="counter"></table>


<%--工具栏标签--%>
<div id="counterTable">
    <a href="#" class="easyui-linkbutton"
       data-options="iconCls:'icon-add',plain:true,onClick:openCounterSaveDialog,">添加</a>

    <%--搜索框--%>
    <input id="counterSearch" class="easyui-searchbox"
           data-options="
               searcher:query,
               prompt:'请输入查询条件....',
               menu:'#countermm',
               width:200"
    />
    <%--搜索关键字下拉菜单--%>
    <div id="countermm" data-options="">
        <div data-options="name:'name',">姓名</div>
        <div data-options="name:'bir'">生日</div>
        <div data-options="name:'id'">编号</div>
    </div>
</div>

<%--用户保存对话框--%>
<div id="counterSave"></div>

<%--用户修改对话框--%>
<div id="counterUpdate"></div>


