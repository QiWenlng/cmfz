<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#modifyUserForm").form('load', '/cmfz/user/queryOne?id=${param.userId}');

    });
</script>
<div style="text-align: center;">
    <%--
        div 布局      加载多少展示多少
        table 布局    table 全部完毕才能展示
    --%>
    <form id="modifyUserForm" method="post">
        <div style="margin-top:40px;">
            ID:<input class="easyui-textbox" name="userId" readonly="readonly" data-options="required:true"/><br/>
        </div>
        <div style="margin-top:20px;">
            姓名:<input class="easyui-textbox" name="name" data-options="required:true,prompt:'请输入姓名'"/><br/>
        </div>
        <div style="margin-top:20px;">
            法名:<input class="easyui-textbox" name="fname" data-options="required:true,prompt:'请输入法名'"/><br/>
        </div>
        <div style="margin-top:20px;">
            手机:<input class="easyui-textbox" name="phone" data-options="required:true,prompt:'请输入手机'"/><br/>
        </div>
        <div style="margin-top: 20px;">
            密码:<input class="easyui-passwordbox" name="password" data-options="required:true,prompt:'请输入密码'"/><br/>
        </div>
        <div style="margin-top: 20px;">
            签名:<input class="easyui-textbox" name="signature"
                      data-options="required:true,multiline:true,height:50,prompt:'个性签名'"/><br/>
        </div>
        <div style="margin-top:20px;">性别:
            <select id="sex" class="easyui-combobox" name="sex" style="width:60px;" data-options="panelHeight:90">
                <option value="-1">请选择性别</option>
                <option>男</option>
                <option>女</option>
                <option>其他</option>
            </select>
        </div>
        <div style="margin-top:20px;">
            省:
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
            市:
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
    </form>
</div>
