<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 2019/10/27
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" var="path"/>

<%--jqgrid--%>
<script>
    //创建jqgrid
    $(function(){
    $("#banner").jqGrid({
        caption:"轮播图表",
        styleUI:"Bootstrap", //使用bootstrap样式
        autowidth:true,//自适应父容器
        height:300,//指定表格高度
        url:"${path}/banner/queryAll",      //请求数据
        datatype:"json",//指定请求数据格式
        colNames:["id","名称","图片","描述","状态","创建时间"],//指定显示列表
        pager:"#pager",//指定分页工具栏
        page:1, //指定初始化页面
        rowNum:2, //每页展示2条
        rowList:[2,4,10,50], //下拉列表
        viewrecords:true, //显示总条数
        sortname:'id',
        editurl:"${path}/banner/edit", //编辑url(增删改)
        colModel:[
            {name:"id",hidden:true},
            {name:"name",align:"center",editable:true},
            {name:"cover",align:"center",editable:true,
                edittype:"file",
                editoptions:{enctype:"Mutipart/form-data"},
                formatter:function(value,option,rows){
                                    //value:在远程匹配到的数据
                                    //option：当前单元格操作的属性对象
                                    //rows :当前行的数据对象
                    //函数的返回值显示在当前单元格
                  return "<img style='width:60px;height:50px' src='${path}/banner/image/"+rows.createDate+'/'+rows.cover+"'>"
                }
            },
            {name:"description",align:"center",editable:true},
            {name:"status",align:"center",editable:true,edittype:"select",editoptions:{
                    value:"正常:正常;失效:失效"
                }},
            {name:"createDate"}
        ],
    }).navGrid("#pager",
        {search:false},//关闭搜索项
        //控制修改
        {   closeAfterEdit:true,
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
                        url:"${path}/banner/upload",
                        type:"post",
                        fileElementId:"cover",
                        data:{id:id},
                        success:function (response) {
                            //自动刷新jqgrid表格
                            $("#banner").trigger("reloadGrid");
                        }
                    });
                }
                //此处修改必须有返回值，随意即可
                return "22222";
            }
        });
    });

</script>
<%--创建表格--%>
<table id="banner"></table>
<%--分页--%>
<div id="pager" style="height: 39px"></div>




