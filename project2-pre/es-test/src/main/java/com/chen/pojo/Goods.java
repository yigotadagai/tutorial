package com.chen.pojo;

import lombok.Data;

import javax.print.event.PrintJobAttributeEvent;
import java.util.Date;
import java.util.Map;

/**
 * @author Robert V
 * @create 2022-09-25-上午11:30
 */
@Data
public class Goods {
    /**
     * •	title:商品标题
     * •	price:商品价格
     * •	createTime:创建时间
     * •	categoryName:分类名称。如：家电，手机
     * •	brandName:品牌名称。如：华为，小米
     * •	spec: 商品规格。如： spec:{“屏幕尺寸”,“5寸”，“内存大小”,“128G”}
     * •	saleNum:销量
     * •	stock:库存量
     */

    private Integer id;
    private String title;
    private Double price;
    private Integer stock;
    private Integer saleNum;
    private Date createTime;
    private String categoryName;
    private String brandName;
    private Map spec;

    // @JSONField(serialize = false)//在转换JSON时，忽略该字段
    //接收数据库的信息 "{}"
    private String specStr;
}
