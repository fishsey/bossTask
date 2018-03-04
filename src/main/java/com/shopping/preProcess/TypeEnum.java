package com.shopping.preProcess;

/**
 * Created by fishsey on 2018/3/1.
 */
public enum TypeEnum
{
    XH(1, "玄幻小说"),
    QH(2, "奇幻小说"),
    XX(3, "仙侠小说");

    private final int type;
    private final String typeName;
    
    TypeEnum(int type, String typeName)
    {
        this.type = type;
        this.typeName = typeName;
    }

    public int getType()
    {
        return type;
    }

    public String getTypeName()
    {
        return typeName;
    }


}
