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
    <script src="${cp}/js/js/respond.min.js"></script>
</head>

<body>
    <!--导航栏部分-->
    <jsp:include page="include/header.jsp"/>

    <!-- 中间内容 -->
    <div class="container-fluid">
        <div class="row">
            <!-- 控制栏 -->
            <div class="col-sm-3 col-md-2 sidebar sidebar-1">
                <ul class="nav nav-sidebar">
                    <li class="list-group-item-diy"><a href="#productArea1">玄幻小说 <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="list-group-item-diy"><a href="#productArea2">奇幻小说</a></li>
                    <li class="list-group-item-diy"><a href="#productArea3">仙侠小说</a></li>

                </ul>
            </div>
            <!-- 控制内容 -->
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <div class="jumbotron">
                    <h1>欢迎来到嘿猪书屋</h1>
                    <p>嘿猪书屋是一个非常优秀的书站，你可以在这里找到您想要的书籍，虽然它只是一个大作业，233333</p>
                </div>
                <div name="productArea1" class="row pd-10" id="productArea1"></div>
                <div name="productArea2" class="row" id="productArea2"></div>
                <div name="productArea3" class="row" id="productArea3"></div>
            </div>
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2">
                <jsp:include page="include/foot.jsp"/>
            </div>
        </div>
    </div>


    <script type="text/javascript">

        var loading = layer.load(0);
        var productType = new Array;
        productType[1] = "玄幻小说";
        productType[2] = "奇幻小说";
        productType[3] = "仙侠小说";

        Array.prototype.contains = function ( needle )
        {
            for (i in this)
            {
                if (this[i] == needle) return true;
            }
            return false;
        };

        var PurchaseMark = "";
        var allPurchaseProducts = new Array();
        allPurchaseProducts = getAllPurchaseProducts();

        if ("${currentUser.id}" == null || "${currentUser.id}" == undefined || "${currentUser.id}" == "")
        {
            <%--window.location.href = "${cp}/login";--%>
        }else  if ("${currentUser.role}" == "2")
        {
            PurchaseMark = "(已售出：";

        }else if ("${currentUser.role}" == "1")
        {
            PurchaseMark = "(已购买过)";
        };


        listProducts();


        function listProducts()
        {
            for (var i = 1; i <= 3; i++)
            {
                var id = "productArea" + i;
                var productArea = document.getElementById(id);
                productArea.innerHTML = "";
            }

            unPurchase = "false";
            if (document.getElementsByName('lookup').length > 0)
            {
                unPurchase = document.getElementById("lookup").checked;
            }
            var allProduct = getAllProducts(unPurchase);

            var mark = new Array;
            mark[1] = 0;
            mark[2] = 0;
            mark[3] = 0;
            var flag = 0;
            for (var i = 0; i < allProduct.length; i++)
            {
                var html = "";
                var imgURL = "${cp}/img/" + allProduct[i].picPath + ".jpg";
                html += '<div class="col-sm-4 col-md-4">' +

                        '<div class="boxes pointer" onmouseover="show(' + allProduct[i].id + ')" onmouseout="hideThis(' + allProduct[i].id + ')"  style="float:left" >' +

                        '<div id="' + allProduct[i].id + '"  style="width:170px; height:20px;"  class="delBut">' +
                        '<input class="btn btn-danger col-sm-4 col-md-4 col-lg-4" type="button"  value="删除"  onclick="deleteProduct(' + allProduct[i].id + ')"/>' +
                        '</div>' +

                        '<div onclick="productDetail(' + allProduct[i].id + ')" class="big bigimg">' + '<img src="' + imgURL + '">' +
                        '</div>';

                if (allPurchaseProducts.contains(allProduct[i].id))
                {
                    if (PurchaseMark == "(已售出：")
                    {
                        PurchaseMark += getshoppingCounts(allProduct[i].id) + ")";
                        flag = 1;
                    }

                    html += '<p onclick="productDetail(' + allProduct[i].id + ')" class="product-name">' + allProduct[i].name
                            + '<span style="color:red;">' + PurchaseMark + '</span>'
                            + '</p>';

                    if (flag == 1)
                    {
                        PurchaseMark = "(已售出：";
                    }
                }
                else
                {
                    html += '<p onclick="productDetail(' + allProduct[i].id + ')" class="product-name">' + allProduct[i].name + '</p>';
                }

                html += '<p onclick="productDetail(' + allProduct[i].id + ')" class="product-price">¥' + allProduct[i].price + '</p>' +
                        '</div>' +
                        '</div>';

                var id = "productArea" + allProduct[i].type;
                var productArea = document.getElementById(id);
                if (mark[allProduct[i].type] == 0)
                {
                    html = '<hr/><h1>' + productType[allProduct[i].type] + '</h1><hr/>' + html;
                    mark[allProduct[i].type] = 1;
                }
                productArea.innerHTML += html;
            }

            hide();
            layer.close(loading);
        }


        function getshoppingCounts(productId)
        {
            var count = null;
            var nothing = {};
            nothing.productId = productId;
            $.ajax({
                async   : false, //设置同步
                type    : 'POST',
                url     : '${cp}/getshoppingCounts',
                data    : nothing,
                dataType: 'json',
                success : function (result)
                {
                    if (result != null)
                    {
                        count = result.result;
                    }
                    else
                    {
                        layer.alert('查询错误');
                    }
                },
                error   : function (resoult)
                {
                    layer.alert('查询错误');
                }
            });
            count = eval("(" + count + ")");
            return count;
        }

        function getAllPurchaseProducts()
        {
            var allProducts = null;
            var nothing = {};
            $.ajax({
                async   : false, //设置同步
                type    : 'POST',
                url     : '${cp}/getAllPurchaseProducts',
                data    : nothing,
                dataType: 'json',
                success : function (result)
                {
                    if (result != null)
                    {
                        allProducts = result.allProducts;
                    }
                    else
                    {
                        layer.alert('查询错误');
                    }
                },
                error   : function (resoult)
                {
                    layer.alert('查询错误');
                }
            });
            //划重点划重点，这里的eval方法不同于prase方法，外面加括号
            allProducts = eval("(" + allProducts + ")");
            return allProducts;
        }

        function getAllProducts(unPurchase)
        {
            var allProducts = null;
            var nothing = {};
            nothing.unPurchase = unPurchase;
            $.ajax({
                async   : false, //设置同步
                type    : 'POST',
                url     : '${cp}/getAllProducts',
                data    : nothing,
                dataType: 'json',
                success : function (result)
                {
                    if (result != null)
                    {
                        allProducts = result.allProducts;
                    }
                    else
                    {
                        layer.alert('查询错误');
                    }
                },
                error   : function (resoult)
                {
                    layer.alert('查询错误');
                }
            });
            //划重点划重点，这里的eval方法不同于prase方法，外面加括号
            allProducts = eval("(" + allProducts + ")");
            return allProducts;
        }


        function productDetail(id)
        {
            var product = {};
            var jumpResult = '';
            product.id = id;
            $.ajax({
                async   : false, //设置同步
                type    : 'POST',
                url     : '${cp}/productDetail',
                data    : product,
                dataType: 'json',
                success : function (result)
                {
                    jumpResult = result.result;
                },
                error   : function (resoult)
                {
                    layer.alert('查询错误');
                }
            });
            if (jumpResult == "success")
            {
                window.location.href = "${cp}/product_detail";
            }
        }


        function hide()
        {
            $("div.delBut").css("display","none");
        }

        function hideThis(productId)
        {
            $("#" + productId).css("display", "none");
        }

        function show(productId)
        {
            if ("${currentUser.role}" == "2" && !allPurchaseProducts.contains(productId))
            {
                $("#" + productId).css("display", "block");
            }
        }


        function deleteProduct(productId)
        {
            layer.confirm('确定要删除该商品 ？', {icon: 1, btn: ['Yes', '继续浏览']},
                    function ()
                    {
                        var product = {};
                        product.productId = productId;
                        var deleteResult = "";
                        $.ajax({
                            async : false,
                            type : 'POST',
                            url : '${cp}/deleteProduct',
                            data : product,
                            dataType : 'json',
                            success : function(result) {
                                deleteResult = result.result;
                            },
                            error : function(result) {
                                layer.alert('删除商品错误');
                            }
                        });

                        if(deleteResult != "success")
                            layer.alert("删除商品出错");
                        else
                        {
                            listProducts();
                            layer.closeAll('dialog');
                        }
                    },
                    function (index)
                    {
                        layer.close(index);
                    }
            );


        }

    </script>


</body>
</html>
