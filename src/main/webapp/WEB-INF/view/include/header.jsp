<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${cp}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${cp}/css/style.css" rel="stylesheet">
    <script src="${cp}/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${cp}/js/layer.js" type="text/javascript"></script>
    <script src="${cp}/js/html5shiv.min.js"></script>
    <script src="${cp}/js/js/respond.min.js"></script>
</head>
<body>
<!--导航栏部分-->
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${cp}/main">嘿猪书屋</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${empty currentUser}">
                    <li><a href="${cp}/register" methods="post">注册</a></li>
                    <li><a href="${cp}/login" methods="post">登录</a></li>
                </c:if>

                <c:if test="${not empty currentUser}">

                    <%-- 1 为买家--%>
                    <c:if test="${currentUser.role == 1}">
                        <li><a href="javascript:goShoppingCar()">购物车</a></li>
                    </c:if>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${currentUser.nickName}
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${currentUser.role == 1}">
                                <li>
                                    <a>
                                        <input type="checkbox" name="lookup" id="lookup" onclick="reload()"/>
                                        <label id="lookupButton">只看未购买商品</label>
                                    </a>
                                </li>
                                <li><a href="${cp}/shopping_record">财务</a></li>
                            </c:if>

                            <c:if test="${currentUser.role == 2}">
                                <li><a href="${cp}/control">发布</a></li>
                            </c:if>

                            <li role="separator" class="divider"></li>
                            <li><a href="${cp}/doLogout">退出</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
            <div class="navbar-form navbar-right">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="真武世界" id="searchKeyWord" onkeypress="if (event.keyCode == 13) searchProduct();"/>
                </div>
                <button class="btn btn-default" onclick="searchProduct();">查找</button>
            </div>
        </div>
    </div>
</nav>


<script type="text/javascript">

    pageUrl = window.location.pathname;
    if (pageUrl != "/main")
    {
        $("#lookup").attr("style", "display:none");
        $("#lookupButton").attr("style", "display:none");
    }


    function goShoppingCar() {
        var pageUrl = {};
        var pageResult = "";
        pageUrl.lastUrl = window.location.href;
        $.ajax({
            async : false,
            type : 'POST',
            url : '${cp}/shopping_car_Page',
            data : pageUrl,
            dataType : 'json',
            success : function(result) {
                pageResult = result.result
            },
            error : function(result) {
                layer.alert('查询错误');
            }
        })

        if(pageResult == "success")
            window.location.href = "${cp}/shopping_car";

    }


    function searchProduct() {
        var search = {};
        search.searchKeyWord = document.getElementById("searchKeyWord").value;
        var searchResult = "";
        $.ajax({
            async : false,
            type : 'POST',
            url : '${cp}/searchPre',
            data : search,
            dataType : 'json',
            success : function(result) {
                searchResult = result.result;
            },
            error : function(result) {
                layer.alert('查询错误');
            }
        });
        if(searchResult == "success")
            window.location.href = "${cp}/search";
    }

    function reload() {
        listProducts();
    }


</script>

</body>
</html>
