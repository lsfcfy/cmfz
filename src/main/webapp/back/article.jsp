<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 2019/10/31
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" var="path"/>

<script>
    $(function () {
        $("#article").jqGrid({
            url:"${path}/article/queryAll",
            datatype:"json",
            styleUI:"Bootstrap",
            autowidth:true,
            rowNum:5,
            rowList:[5,10,15],
            pager:"#article-page",
            page:1,
            viewrecords:true,
            caption : "文章列表",
            editurl :"",
            colNames:["编号","标题","作者","简介","内容","创建时间","操作"],
            colModel:[
                {name:"id",hidden:true,align:"center"},
                {name:"title",editable:true,align:"center"},
                {name:"author",editable:true,align:"center"},
                {name:"brief",editable:true,align:"center"},
                {name:"content",editable:true,align:"center",hidden:true},//文本字段可能过多，所以为了视觉体验隐藏
                {name:"createDate",align:"center"},
                {name:"operation",align:"center",formatter:function (value,option,rows) {
                        return "<a class='btn btn-primary' onclick=\"openModal('edit','"+rows.id+"')\"'>修改</a>"+
                               "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
                            "<a class='btn btn-danger' >删除</a>"
                    }}                                                                                  ///////////////////
            ]
        }).navGrid("#article-page",{edit:false,add:false,del:false,search:false});
    })


    function openModal(oper,id) {
        if("add"==oper){
            $("#article-id").val("");
            $("#article-title").val("");
            $("#article-author").val("");
            $("#article-brief").val("");
            KindEditor.html("#editor_id","");
        }
        if("edit"==oper){
            var article = $("#article").jqGrid("getRowData",id);
            $("#article-id").val(article.id);
            $("#article-title").val(article.title);
            $("#article-author").val(article.author);
            $("#article-brief").val(article.brief);
            KindEditor.html("#editor_id",article.content);
        }
        $("#article-modal").modal("show");
    }


    function save(){
        var id = $("#article-id").val();
        var url = "";
        if(id){
            url= "${path}/article/update";
        }else{
            url = "${path}/article/insert";
        }
        $.ajax({
            url:url,
            type:"post",
            data:$("#article-form").serialize(),
            datatype:"json",
            success:function () {
                $("#article").trigger("reloadGrid");
            }
        })
    }

    KindEditor.create('#editor_id',{
        width : '560px',
        //点击图片空间按钮时发送的请求
        fileManagerJson:"${path}/image",
        //展示图片空间按钮
        allowFileManager:true,
        //上传图片所对应的方法
        uploadJson:"${path}/article/upload",
        // 上传图片是图片的形参名称
        filePostName:"articleImg",
        //此步是为了与表单其它数据同步，避免传参失败
        afterBlur:function () {
            this.sync();
        }
    })
</script>





<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">所有文章</a></li>
    <li role="presentation"><a href="#" onclick="openModal('add','')">添加文章</a></li>
</ul>
<table id="article"></table>
<div id="article-page"></div>

<%--添加修改时公用的模态框--%>
<div class="modal fade" tabindex="-1" role="dialog" id="article-modal">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 683px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">文章操作</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="article-form">
                    <input type="hidden" name="id" id="article-id">
                    <div class="form-group">
                        <label for="article-title" class="col-sm-2 control-label">标题</label>
                       <div class="col-sm-10">
                           <input type="text" name="title" class="form-control" id="article-title" placeholder="请输入标题">
                       </div>
                    </div>
                    <div class="form-group">
                        <label for="article-author" class="col-sm-2 control-label">作者</label>
                       <div class="col-sm-10">
                           <input type="text" name="author" class="form-control" id="article-author" placeholder="请输入作者名称">
                       </div>
                    </div>
                    <div class="form-group">
                        <label for="article-brief" class="col-sm-2 control-label">简介</label>
                       <div class="col-sm-10">
                           <input type="text" name="brief" class="form-control" id="article-brief" placeholder="文章简介">
                       </div>
                    </div>
                    <textarea id="editor_id" name="content" style="width:700px;height:300px"></textarea>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="data-dismiss">关闭</button>
                <button type="button" class="btn btn-primary " data-dismiss="modal" onclick="save()">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

