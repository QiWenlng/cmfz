<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>


<%--<script>
    $(function(){
        $.get("/cmfz/article/queryOne",{"articleId":"${param.articleId}"},function (result) {
            alert(result.title);
            $("#show").append(result.articleUrl);
        },"JSON");
    });
</script>--%>
<%--展示文章--%>
<div>
    <textarea id="editor_id" name="articleUrl"/>
    <script>

        // KindEditor.ready(function (K) {
        var editor = KindEditor.create('#editor_id', {
            classpath: '/cmfz/back/kindeditor/plugins/code/prettify.css',
            width: '99%',
            height: '500',
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

        $(function () {
            $.get("/cmfz/article/queryOne", {"articleId": "${param.articleId}"}, function (result) {
                //kindeditor 回显
                editor.html(result.articleUrl);
            }, "JSON");
        });

        // });
    </script>
</div>