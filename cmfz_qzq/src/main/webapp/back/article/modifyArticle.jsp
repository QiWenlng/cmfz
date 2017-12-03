<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#modifyArticleForm").form('load', '/cmfz/article/queryOne?articleId=${param.articleId}');
    });
</script>
<div style="text-align: center;">
    <%--
        div 布局      加载多少展示多少
        table 布局    table 全部完毕才能展示
    --%>
    <form id="modifyArticleForm" method="post">
        <div style="margin-top:40px;">
            标题:<input class="easyui-textbox" name="title" data-options="required:true,prompt:'标题'"/><br/>
        </div>
        <div style="margin-top:40px;">
            日期:<input class="easyui-datebox" name="publishDate" data-options="required:true,prompt:'发表日期'"/><br/>
        </div>

        <input type="text" hidden="hidden" name="articleId"/>
    </form>
</div>
