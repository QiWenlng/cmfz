<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#modifyBannerForm").form('load', '/cmfz/banner/queryOne?id=${param.id}');
    });
</script>
<div style="text-align: center;">
    <%--
        div 布局      加载多少展示多少
        table 布局    table 全部完毕才能展示
    --%>
    <form id="modifyBannerForm" method="post" enctype="multipart/form-data">
        <div style="margin-top:40px;">
            名称:<input class="easyui-textbox" name="imageName" data-options="required:true,prompt:'请输入轮播图名称'"/><br/>
        </div>

        <div style="margin-top:20px;">
            状态:<input class="easyui-textbox" name="status" data-options="required:true,prompt:'0/1:参与/不参与轮播'"/><br/>
        </div>
        <input type="text" hidden="hidden" name="id"/>
    </form>
</div>
