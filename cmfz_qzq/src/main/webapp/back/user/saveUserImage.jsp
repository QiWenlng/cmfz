<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<div style="text-align: center;">

    <form id="userImageForm" method="post" enctype="multipart/form-data">
        <input type="text" name="userId" value="${param.userId}" hidden="hidden"/>
        <div style="margin-top:30px;">
            <input class="easyui-filebox" name="multipart" data-options="buttonText:'选择图片',require:true">
        </div>
    </form>
</div>



