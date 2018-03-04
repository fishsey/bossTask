<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>



<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${cp}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${cp}/css/style.css" rel="stylesheet">
    <script src="${cp}/js/bootstrap.min.js"></script>
    <script src="${cp}/js/html5shiv.min.js"></script>
    <script src="${cp}/js/respond.min.js"></script>
</head>
<body>
    <hr/>
    <div class="foot-style">
        本项目采用了Spring+SpringMVC+mybatis开发框架，前端使用了Bootstrap开发框架
        <br/>
        数据库使用了MySQL
        <br/>
        本项目的实现主要参考了：
        <a href="http://blog.csdn.net/qq_33171970/article/details/74435789"><i>Shopping+在线购物商城的实现</i></a>
    </div>
</body>
</html>
