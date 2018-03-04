package com.shopping.entity;

/**
 * Created by 14437 on 2017/3/1.
 */
public class Product
{
    private int id;
    private String picPath;
    private String name;
    private String description;
    private String keyWord;
    private int price;
    private int counts;
    private int type;

    public String getPicPath()
    {
        return picPath;
    }

    public void setPicPath(String picPath)
    {
        this.picPath = picPath;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getKeyWord()
    {
        return keyWord;
    }

    public void setKeyWord(String keyWord)
    {
        this.keyWord = keyWord;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public int getCounts()
    {
        return counts;
    }

    public void setCounts(int counts)
    {
        this.counts = counts;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }


    @Override
    public String toString()
    {
        return "Product{" +
                "id=" + id +
                ", picPath='" + picPath + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", keyWord='" + keyWord + '\'' +
                ", price=" + price +
                ", counts=" + counts +
                ", type=" + type +
                '}';
    }
}
