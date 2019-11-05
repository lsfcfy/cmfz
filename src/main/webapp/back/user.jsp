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
        $("#user").jqGrid({
            url:"${path}/user/queryAll",
            datatype:"json",
            styleUI:"Bootstrap",
            autowidth:true,
            rowNum:5,
            rowList:[5,10,15],
            pager:"#user-page",
            page:1,
            viewrecords:true,
            caption : "用户列表",
            editurl :"",
            colNames:["编号","用户名","昵称","性别","电话","头像","签名","创建时间"],
            colModel:[
                {name:"id",hidden:true,align:"center"},
                {name:"username",editable:true,align:"center"},
                {name:"nickname",editable:true,align:"center"},
                {name:"sex",editable:true,align:"center"},
                {name:"photo",editable:true,align:"center",edittype:"file",editoptions:{enctype:"Mutipart/form-data"},formatter:function (value,option,rows){
                        return "<img style='width:80px;height:70px' src='${path}/image/"+rows.photo+"'>"}},
                {name:"phone",editable:true,align:"center"},
                {name:"sign",editable:true,align:"center"},
                {name:"createDate",align:"center"},
            ]
        }).navGrid("#user-page",{edit:false,add:false,del:false,search:false});
    })



</script>


<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">所有用户</a></li>
    <li role="presentation"><a href="${path}/user/esp">导出用户数据</a></li>
</ul>
<table id="user"></table>
<div id="user-page"></div>


