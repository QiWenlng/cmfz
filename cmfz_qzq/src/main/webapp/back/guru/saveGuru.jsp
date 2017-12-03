<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>


<div style="text-align: center;">

    <form id="guruSaveForm" method="post" enctype="multipart/form-data">
        <div style="margin-top:40px;">
            <input class="easyui-textbox" name="guruName" data-options="required:true,prompt:'请输入姓名'"/><br/>
        </div>

        <div style="margin-top:20px;">
            <input class="easyui-textbox" name="guruStatus" data-options="required:true,prompt:'状态'"/><br/>
        </div>
        <div style="margin-top:20px;">
            <input class="easyui-textbox" name="intro"
                   data-options="required:true,multiline:true,height:50,prompt:'个人简介'"/><br/>
        </div>

        <div style="margin-top:20px;">
            <input class="easyui-filebox" name="multipart" data-options="buttonText:'选择头像',require:true">
        </div>
    </form>
</div>



