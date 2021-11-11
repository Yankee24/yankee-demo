package com.yankee.kafka.bean;

/**
 * @author Yankee
 * @version 1.0
 * @description TODO
 * @date 2021/11/11 15:46
 */
public class Order {
    /**
     * 订单编号
     */
    private String id;

    /**
     * 订单名称
     */
    private String name;

    /**
     * 订单金额
     */
    private Double money;

    public Order(String id, String name, Double money) {
        this.id = id;
        this.name = name;
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
