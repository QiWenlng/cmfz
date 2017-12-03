<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>


<div style="text-align: center;">

    <form id="articleSaveForm" method="post">
        <div style="margin-top:20px;">
            标题:<input class="easyui-textbox" name="title" data-options="required:true,prompt:'标题'"/><br/>
        </div>

        <div style="margin-top:20px;">

            <input id="guru" name="guruId" class="easyui-combobox" data-options="
                        width:82,
                        height:30,
                        prompt:'作者',
                        valueField: 'guruId',
                        textField: 'guruName',
                        url: '/cmfz/guru/queryAllGuru',
                        onSelect: function(result){
                            $('#user').combobox('clear');
                            var url = '/cmfz/user/queryUser?guruId='+result.guruId;
                            $('#user').combobox('reload', url);
                        },
                "/>
            <input id="user" name="userId" class="easyui-combobox" data-options="
                        width:82,
                        height:30,
                        prompt:'公开发表',
                        valueField:'userId',
                        textField:'fname',
                "/>
        </div>

        <div>
            <textarea id="editor_id" name="articleUrl"/>
            <script>
                var editor = KindEditor.create('#editor_id', {
                    classpath: '/cmfz/back/kindeditor/plugins/code/prettify.css',
                    width: '99%',
                    height: '330',
                    resizeType: 1,
                    allowPreviewEmoticons: false,
                    allowImageUpload: true,//允许上传图片
                    allowFileManager: true, //允许对上传图片进行管理
                    uploadJson: '${pageContext.request.contextPath}/file/upload',
                    fileManagerJson: '${pageContext.request.contextPath}/file/findAll',
                    afterUpload: function () {
                        this.sync();
                    }, //图片上传后，将上传内容同步到textarea中
                    afterBlur: function () {
                        this.sync();
                    },   ////失去焦点时，将上传内容同步到textarea中
                    items: [
                        'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
                        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
                        'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                        'anchor', 'link', 'unlink', '|', 'about'
                    ]
                });
            </script>
        </div>

    </form>
</div>



