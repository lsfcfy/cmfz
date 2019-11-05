<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8"%>
<c:set value="${pageContext.request.contextPath}" var="path"/>
<html lang="en">
<head>
    <title>持明法州登陆页面</title>
    <%--为了当前页面更好的支持移动端--%>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%--bootstrapcss核心样式--%>
    <link rel="stylesheet" href="${path}/bootstrapgrid/bootstrap/css/bootstrap.min.css">
    <%--引入jquery基础核心样式--%>
    <link  rel="stylesheet" href="${path}/bootstrapgrid/jqgrid/css/ui.jqgrid.css">
    <%--引入jqgrid对bootstrap支持皮肤--%>
    <link rel="stylesheet" href="${path}/bootstrapgrid/jqgrid/css/ui.jqgrid-bootstrap.css">
    <%--jquery核心js--%>
    <script src="${path}/bootstrapgrid/jquery-3.4.1.min.js"></script>
    <%--引入boot核心js--%>
    <script src="${path}/bootstrapgrid/bootstrap/js/bootstrap.min.js"></script>
    <%--引入jqgrid核心js--%>
    <script src="${path}/bootstrapgrid/jqgrid/js/jquery.jqGrid.min.js"></script>
    <%--引入语言il8n--%>
    <script src="${path}/bootstrapgrid/jqgrid/js/i18n/grid.locale-cn.js"></script>
<%--文件上传--%>
    <script src="${path}/bootstrapgrid/jqgrid/js/ajaxfileupload.js"></script>

    <%--文本编辑--%>
    <script charset="utf-8" src="${path}/kindeditor/kindeditor-all-min.js"></script>
    <%--简体中文--%>
    <script charset="utf-8" src="${path}/kindeditor/lang/zh-CN.js"></script>

    <%--引入echats--%>
<script charset="UTF-8" src="${path}/echarts/echarts.min.js"></script>
    <%--<script>
        $(function () {
            $("#btn".click(function () {
                //修改中心布局内容 ,引入一张页面到当前页面中
                $("#centerLayout").load('${path}/back/banner.jsp');
            }))
        })
    </script>--%>
</head>
<body>
    <%--导航条--%>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!--导航标题-->
            <div class="navbar-header">
                <a class="navbar-brand" href="#">持明法州管理系统</a>
            </div>

            <!--导航条内容-->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">欢迎:<font color="#00bfff">${sessionScope.admin.name}</font></a></li>
                    <li><a href="${path}/admin/exit">安全退出 <span class="glyphicon glyphicon-log-out"></span> </a></li>
                </ul>
            </div>
        </div>
    </nav>


    <!--页面主体内容-->
    <div class="container-fluid">
        <!--row-->
        <div class="row">

            <!--菜单-->
            <div class="col-sm-2">

                <!--手风琴控件-->
                <div class="panel-group" id="accordion" >

                    <!--面板-->
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="bannerPanel">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#bannerLists" aria-expanded="true" aria-controls="collapseOne">
                                    轮播图管理
                                </a>
                            </h4>
                        </div>
                        <div id="bannerLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <ul class="list-group">                                                        <%----%><%----%>
                                    <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${path}/back/banner.jsp');" id="btn">所有轮播图</a></li>
                                </ul>                                                                           <%----%><%----%>
                            </div>
                        </div>
                    </div>

                    <!--专辑面板-->
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="albumPanel">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#albumLists" aria-expanded="true" aria-controls="collapseOne">
                                    专辑管理
                                </a>
                            </h4>
                        </div>
                        <div id="albumLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <ul class="list-group">
                                    <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${path}/back/album.jsp')">专辑列表</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!--文章面板-->
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="chapterPanel">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#chapterLists" aria-expanded="true" aria-controls="collapseOne">
                                    文章管理
                                </a>
                            </h4>
                        </div>
                        <div id="chapterLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <ul class="list-group">
                                    <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${path}/back/article.jsp')">文章列表</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>


                    <!--面板-->
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="userPanel">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#userLists" aria-expanded="true" aria-controls="collapseOne">
                                    用户管理
                                </a>
                            </h4>
                        </div>
                        <div id="userLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <ul class="list-group">
                                    <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${path}/back/user.jsp')">用户列表</a></li>
                                    <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${path}/back/user-map.jsp')">注册数据</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="starPanel">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#starLists" aria-expanded="true" aria-controls="collapseOne">
                                    明星管理
                                </a>
                            </h4>
                        </div>
                        <div id="starLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <ul class="list-group">
                                    <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${path}/back/star.jsp')">所有明星</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%--中心布局--%>
            <div class="col-sm-10" id="centerLayout">

                <%--巨幕--%>
                <div>
                    <div class="jumbotron" style="height: 50px">
                        <h4 style="margin-top: auto">欢迎来到持明法州后台管理系统!</h4>
                    </div>
                    <div>
                        <img src="${path}/image/fenmian.jpg" alt="..." width="100%" height="400px">
                    </div>
                </div>
            </div>

            <%--页脚--%>
            <div class="panel panel-footer text-center">
                持明法州@百知教育 2019.10.28
            </div>
</body>
</html>