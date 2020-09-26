package cn.miranda.MeowCraft.Manager;

import java.io.Serializable;

public class TradeObjectManager implements Serializable {
    public String type;
    public int price;

    public TradeObjectManager(String type, int price) {
        this.type = type;
        this.price = price;
    }

}
