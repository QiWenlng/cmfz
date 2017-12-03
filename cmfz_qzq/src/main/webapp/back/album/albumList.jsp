<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#album").datagrid({
            url: '/cmfz/album/queryAll',

            striped: true,  //是否显示斑马线效果。

            fit: true,//当设置为true的时候面板大小将自适应父容器。下面的例子显示了一个面板，可以自动在父容器的最大范围内调整大小

            toolbar: '#albumTable',

            remoteSort: false, //定义从服务器对数据进行排序。

            pagination: true,  //如果为true，则在DataGrid控件底部显示分页工具栏。加入分页工具栏之后自动传递两个参数  page 当前页   rows:每页显示条数

            fitColumns: true,  //真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。根据列中指定width属性 百分比

            pagePosition: 'bottom', //定义分页工具栏的位置。可用的值有：'top','bottom','both'。

            rownumbers: true,  //如果为true，则显示一个行号列。`

            multiSort: true, //定义是否允许多列排序

            pageList: [5, 10, 15, 20, 500],

            columns: [[
                {title: "编号", field: "albumId", width: 240, align: 'center'},
                {
                    title: "封面", field: "imageUrl", width: 100, align: 'center', sortable: true,
                    //图片添加路径
                    formatter: function (value, row, index) {
                        return "<img style='height: 100px;width:100px' onclick=\"albumShowImage('" + row.imageUrl + "')\" src='" + row.imageUrl + "'/>";
                    }
                },
                {title: "专辑名称", field: "albumName", width: 120, align: 'center', sortable: true},
                {title: "章节数", field: "albumCount", width: 100, align: 'center', sortable: true},
                {title: "作者", field: "author", width: 100, align: 'center', sortable: true},
                {title: "播音者", field: "broadcastAuthor", width: 100, align: 'center', sortable: true},
                {title: "专辑分数", field: "score", width: 100, align: 'center', sortable: true},
                {title: "简介", field: "contentIntro", width: 100, align: 'center', sortable: true},
                {title: "出版日期", field: "publishDate", width: 120, align: 'center'},
                {
                    title: "操作", field: "options", width: 400, align: 'center',
                    formatter: function (value, row, index) {
                        return "<a class='del' onclick=\"removealbum('" + row.albumId + "')\" data-options=\"plain:true,iconCls:'icon-20130408025545236_easyicon_net_30'\">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                            "<a class='del' onclick=\"openAlbumModifyDialog('" + row.albumId + "')\" data-options=\"plain:true,iconCls:'icon-edit'\">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                            "<a class='del' onclick=\"openChapterQueryDialog('" + row.albumId + "')\" data-options=\"plain:true,iconCls:'icon-edit'\">查看章节</a>";
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


    //定义一个div   来创建一个easyui的弹窗展示图片
    function albumShowImage(img) {
        var simg = "http://....com/" + img;
        $('#showImage').dialog({
            title: '预览',
            width: 600,
            height: 750,
            resizable: true,
            closed: false,
            cache: false,
            modal: true
        });
        $("#simg").attr("src", simg);
    }

    //创建展示章节
    function openChapterQueryDialog(albumId) {

        //判断选项卡容器标题是否存在
        if (!$("#tab").tabs('exists', '专辑章节')) {
            //不存在则添加选项卡
            $("#tab").tabs('add', {
                title: '专辑章节',
                iconCls: 'icon-man',
                //在设置为true的时候，选项卡面板将显示一个关闭按钮，在点击的时候会关闭选项卡面板。
                closable: true,
                href: '/cmfz/back/chapter/chapterAlbumList.jsp',
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
    function openAlbumSaveDialog() {

        $("#albumSave").dialog({
            title: '添加功课',
            iconCls: 'icon-man',
            width: 400,
            height: 500,
            draggable: true,  //定义是否能够拖拽窗口。
            href: '/cmfz/back/album/saveAlbum.jsp',
            buttons: [{
                text: '保存',
                iconCls: 'icon-save',
                handler: saveAlbum,
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    //关闭当前对话框
                    $("#albumSave").dialog('close', true);
                },
            }],
        });
    }

    //添加方法
    function saveAlbum() {
        $("#albumSaveForm").form('submit', {
            url: '/cmfz/album/save',
            onSubmit: function () {
                //验证表单元素
                return $("#albumSaveForm").form('validate');
            },
            success: function (result) {
                var rr = eval("(" + result + ")");
                //data  都是json格式字符串   使用数据时将json转为js对象和数组
                $.messager.show({
                    title: '提示',
                    msg: rr.msg,
                });

                //关闭当前对话框
                $("#albumSave").dialog('close', true);

                //刷新datagrid
                //$("#album").datagrid('load'); //始终保持在第一页展示
                $("#album").datagrid('reload'); //始终保持在当前页展示
            },
        });
    }


    //删除
    function removeAlbum(albumId) {
        $.messager.confirm('提示', '确定要注销此功课吗？', function (result) {
            if (result) {
                $.post('/cmfz/album/remove', {'albumId': albumId}, function (data) {
                    var rr = eval("(" + data + ")");
                    $.messager.show({
                        title: '提示',
                        msg: rr.msg,
                    });

                    //更新页面datagrid
                    $("#album").datagrid('reload');
                }, 'JSON');
            }
        });
    }

    //添加修改用户对话框
    function openAlbumModifyDialog(albumId) {
        $("#albumModify").dialog({
            title: '修改用户信息',
            iconCls: 'icon-man',
            width: 400,
            height: 500,
            draggable: true,  //定义是否能够拖拽窗口。
            href: '/cmfz/back/album/modifyAlbum.jsp?albumId=' + albumId,
            buttons: [{
                text: '确认',
                iconCls: 'icon-man',
                handler: modifyAlbum,
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    //关闭当前对话框
                    $("#albumModify").dialog('close', true);
                },
            }],
        });
    }

    //修改用户方法
    function modifyAlbum() {
        $("#modifyAlbumForm").form('submit', {
            url: '/cmfz/album/modify',
            onSubmit: function () {
                return $("#albumSaveForm").form('validate');
            },
            success: function (result) {
                var rr = eval("(" + result + ")");
                //data  都是json格式字符串   使用数据时将json转为js对象和数组
                $.messager.show({
                    title: '提示',
                    msg: rr.msg,
                });

                //关闭当前对话框
                $("#albumModify").dialog('close', true);

                //刷新datagrid
                //$("#album").datagrid('load'); //始终保持在第一页展示
                $("#album").datagrid('reload'); //始终保持在当前页展示
            },
        });
    }

    //处理搜索
    function query(value, name) {
        $("#album").datagrid('load', '/cmfz/album/searcher?attribute=' + name + '&value=' + value);
    }

</script>


<%--创建datagrid--%>
<table id="album"></table>


<%--工具栏标签--%>
<div id="albumTable">
    <a href="#" class="easyui-linkbutton"
       data-options="iconCls:'icon-add',plain:true,onClick:openAlbumSaveDialog,">添加</a>

    <%--搜索框--%>
    <input id="albumSearch" class="easyui-searchbox"
           data-options="
               searcher:query,
               prompt:'请输入查询条件....',
               menu:'#albummm',
               width:200"
    />
    <%--搜索关键字下拉菜单--%>
    <div id="albummm" data-options="">
        <div data-options="name:'name',">姓名</div>
        <div data-options="name:'bir'">生日</div>
        <div data-options="name:'id'">编号</div>
    </div>
</div>

<%--用户保存对话框--%>
<div id="albumSave"></div>

<%--用户修改对话框--%>
<div id="albumModify"></div>

<%--计数器对话框--%>
<div id="count"></div>


