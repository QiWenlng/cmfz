<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#banner").datagrid({
            url: '/cmfz/banner/queryAll',

            striped: true,  //是否显示斑马线效果。

            fit: true,//当设置为true的时候面板大小将自适应父容器。下面的例子显示了一个面板，可以自动在父容器的最大范围内调整大小

            toolbar: '#bannerTable',

            remoteSort: false, //定义从服务器对数据进行排序。

            pagination: true,  //如果为true，则在DataGrid控件底部显示分页工具栏。加入分页工具栏之后自动传递两个参数  page 当前页   rows:每页显示条数

            fitColumns: true,  //真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。根据列中指定width属性 百分比

            pagePosition: 'bottom', //定义分页工具栏的位置。可用的值有：'top','bottom','both'。

            rownumbers: true,  //如果为true，则显示一个行号列。`

            multiSort: true, //定义是否允许多列排序

            pageList: [5, 10, 15, 20, 500],

            columns: [[
                {title: "编号", field: "id", width: 240, align: 'center'},
                {title: "图片路径", field: "imageUrl", width: 120, align: 'center', sortable: true},
                {title: "图片名称", field: "imageName", width: 100, align: 'center', sortable: true},
                {title: "上传时间", field: "uploadDate", width: 120, align: 'center'},
                {title: "是否轮播", field: "status", width: 120, align: 'center'},
                {
                    title: "操作", field: "options", width: 200, align: 'center',
                    formatter: function (value, row, index) {
                        return "<a class='del' onclick=\"removeBanner('" + row.id + "')\" data-options=\"plain:true,iconCls:'icon-20130408025545236_easyicon_net_30'\">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                            "<a class='del' onclick=\"bannerQueryDialog('" + row.imageUrl + "')\" data-options=\"plain:true,iconCls:'icon-edit'\">查看轮播</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                            "<a class='del' onclick=\"openBannerModifyDialog('" + row.id + "')\" data-options=\"plain:true,iconCls:'icon-edit'\">修改</a>";
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
    function openBannerSaveDialog() {
        $("#bannerSave").dialog({
            title: '添加轮播',
            iconCls: 'icon-man',
            width: 300,
            height: 300,
            draggable: true,  //定义是否能够拖拽窗口。
            href: '/cmfz/back/banner/saveBanner.jsp',
            buttons: [{
                text: '保存',
                iconCls: 'icon-save',
                handler: saveBanner,
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    //关闭当前对话框
                    $("#bannerSave").dialog('close', true);
                },
            }],
        });
    }

    //添加方法
    function saveBanner() {
        $("#bannerSaveForm").form('submit', {
            url: '/cmfz/banner/save',
            onSubmit: function () {
                //验证表单元素
                return $("#bannerSaveForm").form('validate');
            },
            success: function (result) {
                var rr = eval("(" + result + ")");
                //data  都是json格式字符串   使用数据时将json转为js对象和数组
                $.messager.show({
                    title: '提示',
                    msg: rr.msg,
                });

                //关闭当前对话框
                $("#bannerSave").dialog('close', true);

                //刷新datagrid
                //$("#tt").datagrid('load'); //始终保持在第一页展示
                $("#banner").datagrid('reload'); //始终保持在当前页展示
            },
        });
    }


    //删除
    function removeBanner(id) {
        $.messager.confirm('提示', '确定要注销此轮播图吗？', function (result) {
            if (result) {
                $.post('/cmfz/banner/remove', {'id': id}, function (data) {
                    $.messager.show({
                        title: '提示',
                        msg: data.msg,
                    });

                    //更新页面datagrid
                    $("#banner").datagrid('reload');
                }, 'JSON');
            }
        });
    }

    //查看轮播
    function bannerQueryDialog(imageUrl) {
        $("#bannerQuery").dialog({
            title: '轮播图',
            iconCls: 'icon-man',
            width: 500,
            height: 600,
            draggable: true,  //定义是否能够拖拽窗口。
            href: '/cmfz/back/banner/queryBannerImage.jsp?imageUrl=' + imageUrl,
        });
    }

    //添加修改轮播图对话框
    function openBannerModifyDialog(id) {
        $("#bannerUpdate").dialog({
            title: '修改轮播图信息',
            iconCls: 'icon-man',
            width: 300,
            height: 300,
            draggable: true,  //定义是否能够拖拽窗口。
            href: '/cmfz/back/banner/modifyBanner.jsp?id=' + id,
            buttons: [{
                text: '确认',
                iconCls: 'icon-man',
                handler: modifyBanner,
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    //关闭当前对话框
                    $("#bannerUpdate").dialog('close', true);
                },
            }],
        });
    }

    //修改轮播图方法
    function modifyBanner() {
        $("#modifyBannerForm").form('submit', {
            url: '/cmfz/banner/modify',
            onSubmit: function () {
                return $("#bannerSaveForm").form('validate');
            },
            success: function (result) {
                var rr = eval("(" + result + ")");
                //data  都是json格式字符串   使用数据时将json转为js对象和数组
                $.messager.show({
                    title: '提示',
                    msg: rr.msg,
                });

                //关闭当前对话框
                $("#bannerUpdate").dialog('close', true);

                //刷新datagrid
                //$("#tt").datagrid('load'); //始终保持在第一页展示
                $("#banner").datagrid('reload'); //始终保持在当前页展示
            },
        });
    }

    //处理搜索
    function query(value, name) {
        $("#banner").datagrid('load', '/cmfz/homework/searcher?attribute=' + name + '&value=' + value);
    }

</script>


<%--创建datagrid--%>
<table id="banner"></table>


<%--工具栏标签--%>
<div id="bannerTable">
    <a href="#" class="easyui-linkbutton"
       data-options="iconCls:'icon-add',plain:true,onClick:openBannerSaveDialog,">添加</a>

    <%--搜索框--%>
    <input id="bannerSearch" class="easyui-searchbox"
           data-options="
               searcher:query,
               prompt:'请输入查询条件....',
               menu:'#bannermm',
               width:200"
    />
    <%--搜索关键字下拉菜单--%>
    <div id="bannermm" data-options="">
        <div data-options="name:'name',">姓名</div>
        <div data-options="name:'bir'">生日</div>
        <div data-options="name:'id'">编号</div>
    </div>
</div>

<%--用户保存对话框--%>
<div id="bannerSave"></div>

<%--用户修改对话框--%>
<div id="bannerUpdate"></div>

<%--查看轮播对话框--%>
<div id="bannerQuery"></div>


