<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 2019/10/29
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" var="path"/>

<script>
    $(function () {
        $("#album").jqGrid({
            caption:"专辑列表",
            styleUI:"Bootstrap",
            autowidth:true,
            height:360,
            datatype:"json",
            pager:"#album-page",
            page:1,
            rowNum:3,
            rowList:[3,6,9,12],
            viewrecords:true,
            sortname:'id',
            url:"${path}/album/queryAll",
           editurl:"${path}/album/edit",
            colNames:["id","标题","封面","章节数","得分","歌手","简介","创建时间"],
            colModel:[
                {name:"id",hidden:true},
                {name:"title",align:"center",editable:true},
                {name:"cover",align:"center",editable:true,edittype:"file",editoptions:{enctype:"Mutipart/form-data"},formatter:function (value,option,rows){
                    return "<img style='width:80px;height:70px' src='${path}/image/"+rows.cover+"'>"
                    }},
                {name:"count",align:"center"},
                {name:"score",align:"center",editable:true,edittype:"select",editoptions:{
                    value:"5:*****;4:****;3:***;2:**;1:*"
                    //value:"******:*****;****:****;***:***;**:**;*:*"
                    }},
                {name:"starId",align:"center",editable:true,edittype:"select",editoptions:{
                   dataUrl:"${path}/star/queryAll"},formatter:function (value,option,rows){
                   return rows.star.nickname;
                }},
                {name:"brief",align:"center",editable:true},
                {name:"createDate",align:"center"}
            ],

            subGrid : true,
            subGridRowExpanded : function(subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id  +"' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid({
                    url:"${path}/chapter/queryAll?albumId="+id,
                    datatype:"json",
                    colNames : [ '编号','章节名称','歌手','大小', '时长','创建时间','在线播放'],
                    colModel : [
                        {name : "id",hidden:true},
                        {name : "title",edittype:"file",editable:true,align:"center"},
                        {name:"singer",editable:true},
                        {name : "size",align:"center",align:"center"},
                        {name : "duration",align:"center"},
                        {name : "createDate",align:"center"},
                        {name : "player",width:300,formatter:function (value,option,rows){
                                return  "<audio controls>\n"+
                                         " <source src='${path}/player/"+rows.title+"' type='audio/mp3'>\n"+
                                        "</audio>";
                            }}
                    ],
                    styleUI:"Bootstrap",
                    rowNum : 2,
                    pager : pager_id,
                    autowidth:true,
                    height : '100%',
                    editurl:"${path}/chapter/edit?albumId="+id
                });
                jQuery("#" + subgrid_table_id).jqGrid(
                    'navGrid',"#" + pager_id, {
                        edit : false,
                        add : true,
                        del : false,
                        search:false
                    },{},{
                        //    控制添加
                        closeAfterAdd:true,
                        afterSubmit:function (response) {
                            var status = response.responseJSON.status;
                            if(status){
                                var cid = response.responseJSON.message;
                                $.ajaxFileUpload({
                                    url:"${path}/chapter/upload",
                                    type:"post",
                                    fileElementId:"title",
                                    data:{id:cid,albumId:id},
                                    success:function () {
                                        $("#"+subgrid_table_id).trigger("reloadGrid");
                                    }
                                })
                            }
                            return "123";
                        }
                    });
            }

        }).navGrid(
            "#album-page",
            {search:false},
            { closeAfterEdit:true,
                beforeShowForm:function (fmt) {
                    fmt.find("#cover").attr("disabled",true);
                }},{
                //控制添加
                closeAfterAdd:true,
                afterSubmit:function (data) {
                    console.log(data);
                    var status = data.responseJSON.status;
                    var id = data.responseJSON.message;
                    if(status){
                        $.ajaxFileUpload({
                           url:"${path}/album/upload",
                            type:"post",
                            fileElementId:"cover",
                            data:{id:id},
                            success:function (response) {
                                //自动刷新jqgrid表格
                                $("#album").trigger("reloadGrid");
                            }
                        });
                    }
                    return "2322";
                }
            });
    })
</script>
<table id="album"></table>
<div id="album-page" style="height: 50px"></div>