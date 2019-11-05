<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 2019/11/1
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" var="path"/>

<script>
    //基于准备好的dom,初始化echarts实例
    var myChart = echarts.init(document.getElementById("#echarts"));
    $.ajax({
        url:"${path}/user/echarts?sex="+sex,
        type:"post",
        datatype:"json",
        success:function(data){
            //使用指定的配置项和数据显示图表
            myChart.setOption({
                //指定图表的配置项和数据
                title:{
                    text:'用户注册趋势'
                },
                tooltip:{},
                legend:{
                    data:['男','女']
                },
                xAxis: {
                    data: data.sex
                },
                series: [{
                    name: '男',
                    type: 'line',//bar:柱状图
                    data: data.男
                },{
                    name: '女',
                    type: 'line',//bar:柱状图
                    data: data.女
                }]
            });
        }
    })
</script>
<%--为Echarts 准备一个具体大小的dom--%>
<div id="echarts" style="width: 600px;height: 400px"></div>