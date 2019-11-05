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
        $("#star").jqGrid({
            caption:"明星表格",
            styleUI:"Bootstrap",
            autowidth:true,
            height:360,
            datatype:"json",
            pager:"#star-page",
            page:1,
            rowNum:3,
            rowList:[3,6,9,12],
            viewrecords:true,
            sortname:'id',
            url:"${path}/star/queryAllStar",
           editurl:"${path}/star/edit",
            colNames:["id","昵称","真名","照片","性别","生日"],
            colModel:[
                {name:"id",hidden:true},
                {name:"nickname",align:"center",editable:true},
                {name:"realname",align:"center",editable:true},
                {name:"photo",align:"center",editable:true,edittype:"file",editoptions:{enctype:"Mutipart/form-data"},formatter:function (value,option,rows){
                    return "<img style='width:80px;height:70px' src='${path}/image/"+rows.photo+"'>"
                    }},
                {name:"sex",align:"center",editable:true,edittype:"select",editoptions:{
                    value:"男:男;女:女"
                    }},
                {name:"bir",align:"center",editable:true,edittype:"date"}

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
                    url:"${path}/user/queryAll?starId="+id,
                    datatype:"json",
                    colNames : [ '编号', '用户名', '昵称', '头像','电话', '性别','地址','签名' ],
                    colModel : [
                        {name : "id"},
                        {name : "username"},
                        {name : "nickname"},
                        {name : "photo"},
                        {name : "phone"},
                        {name : "sex"},
                        {name : "address"},
                        {name : "sign"}
                    ],
                    styleUI:"Bootstrap",
                    rowNum : 2,
                    pager : pager_id,
                    autowidth:true,
                    height : '100%'
                });
                jQuery("#" + subgrid_table_id).jqGrid(
                    'navGrid',"#" + pager_id, {
                        edit : false,
                        add : false,
                        del : false,
                        search:false
                    });
            },
        }).navGrid(
            "#star-page",
            {search:false},
            { closeAfterEdit:true,
                beforeShowForm:function (fmt) {
                    fmt.find("#photo").attr("disabled",true);
                }},{
                //控制添加
                closeAfterAdd:true,
                afterSubmit:function (data) {
                    console.log(data);
                    var status = data.responseJSON.status;
                    var id = data.responseJSON.message;
                    if(status){
                        $.ajaxFileUpload({
                           url:"${path}/star/upload",
                            type:"post",
                            fileElementId:"photo",
                            data:{id:id},
                            success:function (response) {
                                //自动刷新jqgrid表格
                                $("#star").trigger("reloadGrid");
                            }
                        });
                    }
                    return "1";
                }
            });
    })
</script>
<table id="star"></table>
<div id="star-page" style="height: 50px"></div>