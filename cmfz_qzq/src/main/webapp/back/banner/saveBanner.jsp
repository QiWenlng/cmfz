<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>


<div style="text-align: center;">

    <form id="bannerSaveForm" method="post" enctype="multipart/form-data">
        <div style="margin-top:40px;">
            <input class="easyui-textbox" name="imageName" data-options="required:true,prompt:'请输入轮播图名称'"/><br/>
        </div>

        <div style="margin-top:20px;">
            <input class="easyui-textbox" name="status" data-options="required:true,prompt:'0/1:参与/不参与轮播'"/><br/>
        </div>

        <div style="margin-top:20px;">
            <input class="easyui-filebox" name="multipart" data-options="buttonText:'选择图片',require:true">
        </div>
    </form>
</div>



