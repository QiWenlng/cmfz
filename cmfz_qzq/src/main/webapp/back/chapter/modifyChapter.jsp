<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#modifyChapterForm").form('load', '/cmfz/chapter/queryOne?chapterId=${param.chapterId}');
    });
</script>
<div style="text-align: center;">
    <%--
        div 布局      加载多少展示多少
        table 布局    table 全部完毕才能展示
    --%>
    <form id="modifyChapterForm" method="post">
        <div style="margin-top:40px;">
            名称:<input class="easyui-textbox" name="chapterName" data-options="required:true,prompt:'标题'"/><br/>
        </div>
        <div style="margin-top:40px;">
            集数:<input class="easyui-textbox" name="chapterCode" data-options="required:true,prompt:'集数'"/><br/>
        </div>

        <input type="text" hidden="hidden" name="chapterId"/>
    </form>
</div>
