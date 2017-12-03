<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#modifyHwForm").form('load', '/cmfz/homework/queryOne?hwId=${param.hwId}');
    });
</script>
<div style="text-align: center;">
    <%--
        div 布局      加载多少展示多少
        table 布局    table 全部完毕才能展示
    --%>
    <form id="modifyHwForm" method="post">
        <div style="margin-top:40px;">
            <input class="easyui-textbox" name="name" data-options="required:true,prompt:'请输入功课名称'"/><br/>
        </div>
        <input type="text" name="hwId" hidden="hidden"/>
    </form>
</div>
