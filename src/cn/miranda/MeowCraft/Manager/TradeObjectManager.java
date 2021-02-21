package cn.miranda.MeowCraft.Manager;

import java.io.Serializable;

public class TradeObjectManager implements Serializable {
    public final String type;
    public final int price;

    public TradeObjectManager(String type, int price) {
        this.type = type;
        this.price = price;
    }

}
