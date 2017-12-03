<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>


<div style="text-align: center;">

    <form id="userSaveForm" method="post">
        <div style="margin-top:40px;">
            <input class="easyui-textbox" name="name" data-options="required:true,prompt:'请输入姓名'"/><br/>
        </div>
        <div style="margin-top:20px;">
            <input class="easyui-textbox" name="fname" data-options="required:true,prompt:'请输入法名'"/><br/>
        </div>
        <div style="margin-top:20px;">
            <input class="easyui-textbox" name="phone" data-options="required:true,prompt:'请输入手机'"/><br/>
        </div>
        <div style="margin-top: 20px;">
            <input class="easyui-passwordbox" name="password" data-options="required:true,prompt:'请输入密码'"/><br/>
        </div>
        <div style="margin-top: 20px;">
            <input class="easyui-passwordbox" name="repassword" data-options="required:true,prompt:'再次输入密码'"/><br/>
        </div>
        <div style="margin-top: 20px;">
            <input class="easyui-textbox" name="signature"
                   data-options="required:true,multiline:true,height:50,prompt:'个性签名'"/><br/>
        </div>
        <div style="margin-top:20px;">
            <select id="sex" class="easyui-combobox" name="sex" style="width:60px;" data-options="panelHeight:90">
                <option value="-1">性别</option>
                <option>男</option>
                <option>女</option>
                <option>其他</option>
            </select>
        </div>
        <div>

            <input id="province" name="province" class="easyui-combobox" data-options="
                        width:82,
                        height:30,
                        prompt:'省份',
                        valueField: 'code',
                        textField: 'name',
                        url: '/cmfz/province/queryProvince',
                        onSelect: function(result){
                            $('#city').combobox('clear');
                            var url = '/cmfz/province/queryCity?code='+result.code;
                            $('#city').combobox('reload', url);
                            $('#address').val(result.name);
                        },
                "/>
            <input id="city" name="city" class="easyui-combobox" data-options="
                        width:82,
                        height:30,
                        prompt:'城市',
                        valueField:'code',
                        textField:'name',
                        onSelect:function(result){
                            //拼接当前值加城市
                            var p=$('#address').val();
                            $('#address').val(p + result.name);
                        },
                "/>
            <input id="address" type="text" name="address" hidden="hidden"/>
        </div>
        <div style="margin-top:20px;">
            <input class="easyui-textbox" name="code" data-options="required:true,prompt:'请输入验证码'"/><br/>
            <img id="code" style="width: 100px; height: 30px;" src="/cmfz/admin/getImage"/>
            <script type="text/javascript">
                $(function () {
                    $("#code").click(function () {
                        $(this).attr("src", "/cmfz/admin/getImage?time=(new Date()).getTime()");
                    });
                });
            </script>
        </div>
    </form>
</div>



