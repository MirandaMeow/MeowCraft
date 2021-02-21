package cn.miranda.MeowCraft.Manager;

import java.io.Serializable;

public class TradeObjectManager implements Serializable {
    private static final long serialVersionUID = -1059590494834987882L;
    public final String type;
    public final int price;

    public TradeObjectManager(String type, int price) {
        this.type = type;
        this.price = price;
    }
}
