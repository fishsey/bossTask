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
    <script src="${cp}/js/ajaxfileupload.js" type="text/javascript"></script>
    <script src="${cp}/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${cp}/js/layer.js" type="text/javascript"></script>
    <script src="${cp}/js/html5shiv.min.js"></script>
    <script src="${cp}/js/respond.min.js"></script>
</head>

<body>
    <!--导航栏部分-->
    <jsp:include page="include/header.jsp"/>

    <!-- 中间内容 -->
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-1 col-md-1"></div>
            <div class="col-sm-10 col-md-10">
                <h1>${productDetail.name}</h1>
                <hr/>
            </div>
        </div>
        <div class="row">

            <div class="col-sm-1 col-md-1"></div>

            <%--<div class="form-group">--%>
                <%--<label for="productImgUpload" class="col-sm-2 col-md-2 control-label" accept="image/jpg">商品图片</label>--%>
                <%--<div class="col-sm-6 col-md-6">--%>
                    <%--<input name="productImgUpload" type="file"  id="productImgUpload"/>--%>
                    <%--<p class="help-block">上传的图片大小应为280*160大小</p>--%>
                <%--</div>--%>
            <%--</div>--%>

            <div id="picUpload" class="col-sm-3 col-md-3" class="form-group">
                <img class="detail-img" src="${cp}/img/${productDetail.picPath}.jpg" />
            </div>

            <div class="col-sm-7 col-md-7 detail-x">
                <table class="table table-striped" contenteditable="false">
                    <tr>
                        <th nowrap=nowrap>商品名称</th>
                        <td id="productName" class="tableConetxt">${productDetail.name}</td>
                    </tr>
                    <tr>
                        <th nowrap=nowrap>摘要(类型)</th>
                        <td id="keyword">${productDetail.keyWord}</td>
                    </tr>
                    <tr>
                        <th nowrap=nowrap id="priceName">${productDetail.id}</th>
                        <td id="price" class="tableConetxt">${productDetail.price}</td>
                    </tr>
                    <tr>
                        <th nowrap=nowrap>商品描述</th>
                        <td id="descripe" class="tableConetxt">${productDetail.description}</td>
                    </tr>
                    <tr>
                        <th nowrap=nowrap>商品库存</th>
                        <td id="allCounts" class="tableConetxt">${productDetail.counts}</td>
                    </tr>
                <c:if test="${currentUser.role != 2}">
                    <tr>
                        <th nowrap=nowrap>购买数量</th>
                        <td>
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-default" onclick="subCounts()">-</button>
                                <button id="productCounts" type="button" class="btn btn-default">1</button>
                                <button type="button" class="btn btn-default" onclick="addCounts(1)">+</button>
                            </div>
                        </td>
                    </tr>
                </c:if>
                </table>
                <div class="row">
                    <c:if test="${currentUser.role == 2}">
                        <div class="col-sm-1 col-md-1 col-lg-1"></div>
                        <button class="btn btn-danger btn-lg col-sm-4 col-md-4 col-lg-4"
                                onclick="editProduct(${productDetail.id})">编辑
                        </button>
                        <div class="col-sm-1 col-md-1 col-lg-1"></div>
                        <button id="pubButton" class="btn btn-danger btn-lg col-sm-4 col-md-4 col-lg-4"
                                onclick="publishProduct(${productDetail.id})" disabled="disabled">发布
                        </button>
                    </c:if>

                    <c:if test="${currentUser.role != 2}">
                        <div class="col-sm-2 col-md-2 col-lg-2"></div>
                        <button class="btn btn-danger btn-lg col-sm-4 col-md-4 col-lg-4"
                                onclick="buyConfirm(${productDetail.id})">购买
                        </button>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-1 col-md-1 col-lg-1"></div>
            <div class="col-sm-10 col-md-10 col-lg-10">
                <hr class="division"/>
                <table class="table evaluationTable" border="0" id="evaluation">
                </table>
                <hr/>
                <div id="inputArea"></div>
            </div>
        </div>
    </div>
    <!-- 尾部 -->
    <jsp:include page="include/foot.jsp"/>


    <script type="text/javascript">
        var productId = parseInt(document.getElementById("priceName").innerHTML);
        var productPrice = document.getElementById("price").innerHTML;
        document.getElementById("priceName").innerHTML = "当前的价格";

        var isPur = isPurchase(productId);
        if(isPur != "no")
        {
            document.getElementById("priceName").innerHTML = "购买时的价格";
            htmlTemp = "";
            for (var i = 0; i < isPur.length; i++)
            {
                htmlTemp +=  "订单" + i + ":" + isPur[i].productPrice + "(共" + isPur[i].counts + "本)\t";
            }
            document.getElementById("price").innerHTML = htmlTemp;
        }

        function isPurchase(productId)
        {
            var productResult = "";
            var product = {};
            product.productId = productId;
            $.ajax({
                async   : false, //设置同步
                type    : 'POST',
                url     : '${cp}/isPurchase',
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

        function judgeIsLogin()
        {
            if ("${currentUser.id}" == null || "${currentUser.id}" == undefined || "${currentUser.id}" == "")
            {
                window.location.href = "${cp}/login";
            }
        }

        function subCounts()
        {
            var productCounts = document.getElementById("productCounts");
            var counts = parseInt(productCounts.innerHTML);
            if (counts >= 2)
                counts--;
            productCounts.innerHTML = counts;
        }

        function addCounts()
        {
            var productCounts = document.getElementById("productCounts");
            var counts = parseInt(productCounts.innerHTML);
            if (counts <${productDetail.counts})
                counts++;
            else {
                layer.alert("库存不足")
            }
            productCounts.innerHTML = counts;
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

        function buyConfirm(productId)
        {
            judgeIsLogin();

            var productCounts = document.getElementById("productCounts");
            var counts = parseInt(productCounts.innerHTML);
            var product = getProductById(productId);
            var html = '<div class="col-sm-1 col-md-1 col-lg-1"></div>' +
                       '<div class="col-sm-10 col-md-10 col-lg-10">' +
                       '<table class="table confirm-margin">' +
                       '<tr>' +
                       '<th>商品名称：</th>' +
                       '<td>' + product.name + '</td>' +
                       '</tr>' +
                       '<tr>' +
                       '<th>商品单价：</th>' +
                       '<td>' + product.price + '</td>' +
                       '</tr>' +
                       '<tr>' +
                       '<th>购买数量：</th>' +
                       '<td>' + counts + '</td>' +
                       '</tr>' +
                       '<tr>' +
                       '<th>总金额：</th>' +
                       '<td>' + counts * product.price + '</td>' +
                       '</tr>' +
                       '</table>' +
                       '<div class="row">' +
                       '<div class="col-sm-4 col-md-4 col-lg-4"></div>' +
                       '<button class="btn btn-danger col-sm-4 col-md-4 col-lg-4" onclick="addToShoppingCar(' + productId + ')">确认购买</button>' +
                       '</div>' +
                       '</div>';

            layer.open({
                type   : 1,
                title  : '请确认订单信息：',
                content: html,
                area   : ['650px', '350px'],
            });
        }

        function addToShoppingCar(productId)
        {
            judgeIsLogin();

            var productCounts = document.getElementById("productCounts");
            var counts = parseInt(productCounts.innerHTML);
            var shoppingCar = {};
            shoppingCar.productId = productId;
            shoppingCar.counts = counts;
            var addResult = "";
            $.ajax({
                async   : false,
                type    : 'POST',
                url     : '${cp}/addShoppingCar',
                data    : shoppingCar,
                dataType: 'json',
                success : function (result)
                {
                    addResult = result.result;
                },
                error   : function (result)
                {
                    layer.alert('查询用户错误');
                }
            });
            if (addResult == "success")
            {
                layer.confirm('前往购物车？', {icon: 1, title: '已添加到购物车', btn: ['前往购物车', '继续浏览']},
                        function ()
                        {
                            <%--window.location.href = "${cp}/shopping_car";--%>
                            goShoppingCarPage();
                        },
                        function (index)
                        {
                            layer.close(index);
                        }
                );
            }else if (buyResult == "unEnough")
            {
                layer.alert("库存不足，购买失败")
            }
        }


        function goShoppingCarPage() {
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

        function editProduct(productId)
        {
            var t1 = '<input name="productImgUpload" type="file"  id="productImgUpload"/><br>';
            $("div#picUpload").prepend(t1);

            $("td.tableConetxt").attr("contenteditable", "true");
            $("#pubButton").attr('disabled',false);
            document.getElementById("keyword").innerHTML = '<div class="col-sm-6 col-md-6">' +
                '<select name="productType" class="form-control" id="productType">' +
                    '<option value="1">玄幻小说</option>'+
                    '<option value="2">奇幻小说</option>'+
                    '<option value="3">仙侠小说</option>'+
                '</select>'
                 '</div>';

            $("input[type='file']").change(function()
            {
                var file = this.files[0];
                if (window.FileReader)
                {
                    var reader = new FileReader();
                    reader.readAsDataURL(file);
                    reader.onloadend = function (e)
                    {
                        $(".detail-img").attr("src",e.target.result);    //上传显示
                    };
                }
            });
        }


        function publishProduct(productId)
        {
            addProducts();
        }

        function addProducts() {

            var productInfos = {};
            productInfos.name = document.getElementById("productName").innerHTML;
            productInfos.type = document.getElementById("productType").value;
            productInfos.price = parseInt(document.getElementById("price").innerHTML);
            productInfos.description = document.getElementById("descripe").innerHTML;
            productInfos.counts = parseInt(document.getElementById("allCounts").innerHTML);

            var addResult="";
            $.ajax({
                async : false,
                type : 'POST',
                url : '${cp}/editProduct',
                data : productInfos,
                dataType : 'json',
                success : function(result) {
                    addResult = result.result;
                },
                error : function(result) {
                    layer.alert('修改商品错误');
                }
            });
            if(addResult == "success") {
                fileUploads();
//                layer.msg('修改商品成功', {icon: 1});
//                layer.close(loadings);
                <%--window.location.href = "${cp}/product_detail";--%>
            }
        }

        function fileUploads() {
            var loadings = layer.load(0);

            var results = "";
            var name = document.getElementById("productName").innerHTML;
            var fileName= document.getElementById("productImgUpload").value;
//            alert(fileName);
            if(fileName != null && fileName != "" && fileName != undefined)
            {
                $.ajaxFileUpload({
                    async : false,
                    url:'${cp}/uploadFileEdit?name='+name,
                    secureuri:false ,
                    fileElementId:'productImgUpload',
                    type:'POST',
                    dataType : 'text',
                    success: function (result){
                        result = result.replace(/<pre.*?>/g, '');  //ajaxFileUpload会对服务器响应回来的text内容加上<pre style="....">text</pre>前后缀
                        result = result.replace(/<PRE.*?>/g, '');
                        result = result.replace("<PRE>", '');
                        result = result.replace("</PRE>", '');
                        result = result.replace("<pre>", '');
                        result = result.replace("</pre>", '');
                        result = JSON.parse(result);
                        results = result.result;
                        if(results == "success") {
                            layer.msg('修改商品成功', {icon: 1, time:1000});
                            window.location.href = "${cp}/product_detail";
                            //var imgPreSee = document.getElementById("imgPreSee");
                            //var imgSrc = '${cp}/img/'+name+'.jpg';
                            //imgPreSee.innerHTML +='<img src="'+imgSrc+')" class="col-sm-12 col-md-12 col-lg-12"/>';
                        }
                        else {
                            layer.msg("图片上传失败", {icon: 0});
                        }
                    },
                    error: function ()
                    {
                        layer.alert("上传错误");
                    }}
                );
            }else {
                layer.msg('修改商品成功', {icon: 1, time:1000});
                window.location.href = "${cp}/product_detail";
            }

            layer.close(loadings);
        }

    </script>
</body>
</html>
