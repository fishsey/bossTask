<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>嘿猪书屋</title>
    <link href="${cp}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${cp}/css/style.css" rel="stylesheet">
    <script src="${cp}/js/jquery.min.js" type="text/javascript"></script>
    <script src="${cp}/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${cp}/js/layer.js" type="text/javascript"></script>
    <script src="${cp}/js/html5shiv.min.js"></script>
    <script src="${cp}/js/respond.min.js"></script>
</head>
<body>
    <!--导航栏部分-->
    <jsp:include page="include/header.jsp"/>

    <!-- 中间内容 -->
    <div class="container-fluid bigHead">
        <div class="row">
            <div class="col-sm-10  col-md-10 col-sm-offset-1 col-md-offset-1">
                <div class="jumbotron">
                    <h1>欢迎来到订单页</h1>
                    <p>您的购买清单为</p>
                </div>
            </div>
            <div class="col-sm-10  col-md-10 col-sm-offset-1 col-md-offset-1">
                <div class="row">
                    <ul class="nav nav-tabs list-group-diy" role="tablist">
                        <li role="presentation" class="list-group-item-diy">
                            <a  href="#all" aria-controls="all" role="tab" data-toggle="tab">全部订单&nbsp;
                                <span class="badge" id="allCount">0 </span>
                            </a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane" id="all">
                            <table class="table table-hover center" id="allTable">
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 尾部 -->
    <jsp:include page="include/foot.jsp"/>


    <script type="text/javascript">
        var loading = layer.load(0);

        updateShoppingRecords();

        function judgeIsLogin()
        {
            if ("${currentUser.id}" == null || "${currentUser.id}" == undefined || "${currentUser.id}" == "")
            {
                window.location.href = "${cp}/login";
            }
        }

        function updateShoppingRecords()
        {
            var allMoneys = 0;
            var allTable = document.getElementById("allTable");
            var allCount = document.getElementById("allCount");
            var allCounts = parseInt(allCount.innerHTML);
            var allShoppingRecords = getShoppingRecords();
            allTable.innerHTML = "";
            var allHTML = '<tr>' +
                          '<th>商品名称</th>' +
                          '<th>图片</th>' +
                          '<th>购买时间</th>' +
                          '<th>付款金额（总共）</th>' +
                          '<th>购买数量</th>' +
                          '</tr>';
            var allHTMLTemp = "";
            for (var i = 0; i < allShoppingRecords.length; i++)
            {
                var product = getProductById(allShoppingRecords[i].productId);
                allHTMLTemp += '<tr>' +
                               '<td>' + product.name + '</td>' +
                               '<td> <img  width="20%" height="20%" src="${cp}/img/' + product.picPath + '.jpg"/>' + '</td>' +
                               '<td>' + allShoppingRecords[i].time + '</td>' +
                               '<td>' + allShoppingRecords[i].productPrice + '</td>' +
                               '<td>' + allShoppingRecords[i].counts + '</td>' +
                               '</tr>';
                allCounts++;
                allMoneys += allShoppingRecords[i].productPrice;
            }
            if (allHTMLTemp == "")
            {
                allHTML = '<div class="row">' +
                          '<div class="col-sm-3 col-md-3 col-lg-3"></div> ' +
                          '<div class="col-sm-6 col-md-6 col-lg-6">' +
                          '<h2>没有相关订单</h2>' +
                          '</div>' +
                          '</div>';
            }
            else
                allHTML += allHTMLTemp;

            allHTML += '<tr>' +
                       '<td style="text-align:center;font-weight:bold">' + "总计消费金额: " + '</td>' +
                       '<td colspan="4" style="text-align:center;font-weight:bold">' + allMoneys + '</td>' +
                       '</tr>';


            allCount.innerHTML = allCounts;
            allTable.innerHTML += allHTML;
            layer.close(loading);
        }
        function getShoppingRecords()
        {
            judgeIsLogin();
            var shoppingRecordProducts = "";
            var user = {};
            user.userId = ${currentUser.id};
            $.ajax({
                async   : false, //设置同步
                type    : 'POST',
                url     : '${cp}/getShoppingRecords',
                data    : user,
                dataType: 'json',
                success : function (result)
                {
                    shoppingRecordProducts = result.result;
                },
                error   : function (result)
                {
                    layer.alert('查询错误');
                }
            });
            shoppingRecordProducts = eval("(" + shoppingRecordProducts + ")");
            return shoppingRecordProducts;
        }


        function getProductById(id)
        {
            var productResult = "";
            var product = {};
            product.id = id;
            $.ajax({
                async   : false, //设置同步
                type    : 'POST',
                url     : '${cp}/getProductById',
                data    : product,
                dataType: 'json',
                success : function (result)
                {
                    productResult = result.result;
                },
                error   : function (result)
                {
                    layer.alert('查询错误');
                }
            });
            productResult = JSON.parse(productResult);
            return productResult;
        }
    </script>
</body>
</html>
