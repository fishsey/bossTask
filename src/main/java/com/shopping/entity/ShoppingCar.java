package com.shopping.entity;

/**
 * Created by 14437 on 2017/3/3.
 */
public class ShoppingCar
{
    private int userId;
    private int productId;
    private int productPrice;
    private int counts;

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public int getProductPrice()
    {
        return productPrice;
    }

    public void setProductPrice(int productPrice)
    {
        this.productPrice = productPrice;
    }

    public int getCounts()
    {
        return counts;
    }

    public void setCounts(int counts)
    {
        this.counts = counts;
    }

    @Override
    public String toString()
    {
        return "ShoppingCar{" +
                "userId=" + userId +
                ", productId=" + productId +
                ", productPrice=" + productPrice +
                ", counts=" + counts +
                '}';
    }
}
