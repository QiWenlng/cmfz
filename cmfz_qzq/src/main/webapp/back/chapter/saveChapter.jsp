<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>


<div style="text-align: center;">

    <form id="chapterSaveForm" method="post" enctype="multipart/form-data">
        <div style="margin-top:40px;">
            名称:<input class="easyui-textbox" name="chapterName" data-options="required:true,prompt:'标题'"/><br/>
        </div>
        <div style="margin-top:40px;">
            集数:<input class="easyui-textbox" name="chapterCode" data-options="required:true,prompt:'集数'"/><br/>
        </div>

        <div style="margin-top:20px;">
            <input class="easyui-filebox" name="multipart" d data-options="buttonText:'选择音频',require:true">
        </div>
        <div>
            <input id="album" name="albumId" class="easyui-combobox" data-options="
                    width:82,
                    height:30,
                    prompt:'作者',
                    valueField: 'albumId',
                    textField: 'albumName',
                    url: '/cmfz/album/queryAlbum',
                "/>
        </div>
    </form>
</div>



