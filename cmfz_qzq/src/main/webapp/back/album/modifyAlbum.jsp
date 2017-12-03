<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#modifyAlbumForm").form('load', '/cmfz/album/queryOne?albumId=${param.albumId}');
    });
</script>
<div style="text-align: center;">
    <%--
        div 布局      加载多少展示多少
        table 布局    table 全部完毕才能展示
    --%>
    <form id="modifyAlbumForm" method="post">
        <div style="margin-top:20px;">
            专辑名称:<input class="easyui-textbox" name="albumName" data-options="required:true,prompt:'请输入专辑名称'"/><br/>
        </div>
        <div style="margin-top:20px;">
            章节数:<input class="easyui-textbox" name="albumCount" data-options="required:true,prompt:'请输入章节数'"/><br/>
        </div>
        <div style="margin-top:20px;">
            作者:<input class="easyui-textbox" name="author" data-options="required:true,prompt:'请输入作者'"/><br/>
        </div>
        <div style="margin-top:20px;">
            播音者:<input class="easyui-textbox" name="broadcastAuthor" data-options="required:true,prompt:'请输入播音者'"/><br/>
        </div>
        <div style="margin-top:20px;">
            专辑分数:<input class="easyui-textbox" name="score" data-options="required:true,prompt:'专辑分数'"/><br/>
        </div>
        <div style="margin-top:20px;">
            出版时间:<input class="easyui-datebox" name="publishDate" data-options="required:true,prompt:'出版时间'"/><br/>
        </div>
        <div style="margin-top:20px;">
            简介:<input class="easyui-textbox" name="contentIntro"
                      data-options="required:true,multiline:true,height:50,prompt:'简介'"/><br/>
        </div>
        <input type="text" name="albumId" hidden="hidden"/>
    </form>
</div>
