<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#modifyGuruForm").form('load', '/cmfz/guru/queryOne?guruId=${param.guruId}');
    });
</script>
<div style="text-align: center;">
    <%--
        div 布局      加载多少展示多少
        table 布局    table 全部完毕才能展示
    --%>
    <form id="modifyGuruForm" method="post" enctype="multipart/form-data">
        <div style="margin-top:40px;">
            名称:<input class="easyui-textbox" name="guruName" data-options="required:true,prompt:'请输入上师名称'"/><br/>
        </div>

        <div style="margin-top:20px;">
            状态:<input class="easyui-textbox" name="guruStatus" data-options="required:true,prompt:'状态'"/><br/>
        </div>

        <div style="margin-top:20px;">
            <input class="easyui-filebox" name="multipart" data-options="buttonText:'选择头像',require:true">
        </div>

        <input type="text" hidden="hidden" name="guruId"/>
    </form>
</div>
