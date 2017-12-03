<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<div style="text-align: center;">
    <%--
        div 布局      加载多少展示多少
        table 布局    table 全部完毕才能展示
    --%>
    <form id="modifyCounterForm" method="post">
        <div style="margin-top:40px;">
            <input class="easyui-textbox" name="count" data-options="required:true,prompt:'请输入次数'"/><br/>
        </div>
        <div>
            加:<input type="radio" name="counterStatus" value="0"/>
            减:<input type="radio" name="counterStatus" value="1"/>
        </div>

        <input type="text" name="counterId" value="${param.counterId}" hidden="hidden"/>
    </form>
</div>
