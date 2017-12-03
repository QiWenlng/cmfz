<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#chapterAlbum").datagrid({
            url: '/cmfz/chapterAlbum/queryAll',

            striped: true,  //是否显示斑马线效果。

            fit: true,//当设置为true的时候面板大小将自适应父容器。下面的例子显示了一个面板，可以自动在父容器的最大范围内调整大小

            toolbar: '#chapterAlbumTable',

            remoteSort: false, //定义从服务器对数据进行排序。

            pagination: true,  //如果为true，则在DataGrid控件底部显示分页工具栏。加入分页工具栏之后自动传递两个参数  page 当前页   rows:每页显示条数

            fitColumns: true,  //真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。根据列中指定width属性 百分比

            pagePosition: 'bottom', //定义分页工具栏的位置。可用的值有：'top','bottom','both'。

            rownumbers: true,  //如果为true，则显示一个行号列。`

            multiSort: true, //定义是否允许多列排序

            pageList: [5, 10, 15, 20, 500],

            columns: [[
                {title: "编号", field: "chapterId", width: 240, align: 'center'},
                {title: "集数", field: "chapterCode", width: 120, align: 'center'},
                {title: "大小", field: "chapterSize", width: 120, align: 'center'},
                {title: "章节名称", field: "chapterName", width: 120, align: 'center'},
                {title: "路径", field: "downloadUrl", width: 120, align: 'center'},
                {title: "所属专辑编号", field: "albumId", width: 120, align: 'center'},
                {
                    title: "操作", field: "options", width: 400, align: 'center',
                    formatter: function (value, row, index) {
                        return "<a class='del' onclick=\"removeChapter('" + row.chapterId + "')\" data-options=\"plain:true,iconCls:'icon-20130408025545236_easyicon_net_30'\">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                            "<a class='del' onclick=\"openChapterModifyDialog('" + row.chapterId + "')\" data-options=\"plain:true,iconCls:'icon-edit'\">修改</a>";
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
    function openChapterSaveDialog() {

        $("#chapterSave").dialog({
            title: '添加文章',
            iconCls: 'icon-man',
            width: 400,
            height: 500,
            draggable: true,  //定义是否能够拖拽窗口。
            href: '/cmfz/back/chapter/saveChapter.jsp',
            buttons: [{
                text: '保存用户',
                iconCls: 'icon-save',
                handler: saveChapter,
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    //关闭当前对话框
                    $("#chapterSave").dialog('close', true);
                },
            }],
        });
    }

    //添加方法
    function saveChapter() {
        $("#chapterSaveForm").form('submit', {
            url: '/cmfz/chapter/save',
            onSubmit: function () {
                //验证表单元素
                return $("#chapterSaveForm").form('validate');
            },
            success: function (result) {
                var rr = eval("(" + result + ")");
                //data  都是json格式字符串   使用数据时将json转为js对象和数组
                $.messager.show({
                    title: '提示',
                    msg: rr.msg,
                });

                //关闭当前对话框
                $("#chapterSave").dialog('close', true);

                //刷新datagrid
                //$("#chapter").datagrid('load'); //始终保持在第一页展示
                $("#chapter").datagrid('reload'); //始终保持在当前页展示
            },
        });
    }

    //删除
    function removeChapter(chapterId) {
        $.messager.confirm('提示', '确定要删除此文章吗？', function (result) {
            if (result) {
                $.post('/cmfz/chapter/remove', {'chapterId': chapterId}, function (data) {
                    var rr = eval("(" + data + ")");
                    $.messager.show({
                        title: '提示',
                        msg: rr.msg,
                    });

                    //更新页面datagrid
                    $("#chapter").datagrid('reload');
                }, 'JSON');
            }
        });
    }

    //添加修改对话框
    function openChapterModifyDialog(chapterId) {
        $("#chapterModify").dialog({
            title: '修改信息',
            iconCls: 'icon-man',
            width: 400,
            height: 500,
            draggable: true,  //定义是否能够拖拽窗口。
            href: '/cmfz/back/chapter/modifyChapter.jsp?chapterId=' + chapterId,
            buttons: [{
                text: '确认',
                iconCls: 'icon-man',
                handler: modifyChapter,
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    //关闭当前对话框
                    $("#chapterModify").dialog('close', true);
                },
            }],
        });
    }

    //修改方法
    function modifyChapter() {
        $("#modifyChapterForm").form('submit', {
            url: '/cmfz/chapter/modify',
            onSubmit: function () {
                return $("#chapterSaveForm").form('validate');
            },
            success: function (result) {
                var rr = eval("(" + result + ")");
                //data  都是json格式字符串   使用数据时将json转为js对象和数组
                $.messager.show({
                    title: '提示',
                    msg: rr.msg,
                });

                //关闭当前对话框
                $("#chapterModify").dialog('close', true);

                //刷新datagrid
                //$("#chapter").datagrid('load'); //始终保持在第一页展示
                $("#chapter").datagrid('reload'); //始终保持在当前页展示
            },
        });
    }

    //处理搜索
    function query(value, name) {
        $("#chapter").datagrid('load', '/homework_easyui_171111/emp/searcher?attribute=' + name + '&value=' + value);
    }

</script>


<%--创建datagrid--%>
<table id="chapterAlbum"></table>


<%--工具栏标签--%>
<div id="chapterAlbumTable">
    <a href="#" class="easyui-linkbutton"
       data-options="iconCls:'icon-add',plain:true,onClick:openChapterSaveDialog,">添加</a>

    <%--搜索框--%>
    <input id="chapterAlbumSearch" class="easyui-searchbox"
           data-options="
               searcher:query,
               prompt:'请输入查询条件....',
               menu:'#chapterAlbummm',
               width:200"
    />
    <%--搜索关键字下拉菜单--%>
    <div id="chapterAlbummm" data-options="">
        <div data-options="name:'name',">姓名</div>
        <div data-options="name:'bir'">生日</div>
        <div data-options="name:'id'">编号</div>
    </div>
</div>

<%--用户保存对话框--%>
<div id="chapterSave"></div>

<%--用户修改对话框--%>
<div id="chapterModify"></div>

<%--上传头像对话框--%>
<div id="chapterImage"></div>

<%--展示图片--%>
<div id="showImage"></div>