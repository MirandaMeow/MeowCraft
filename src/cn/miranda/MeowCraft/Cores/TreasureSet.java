package cn.miranda.MeowCraft.Cores;

import cn.miranda.MeowCraft.Manager.ConfigManager;
import cn.miranda.MeowCraft.Utils.IO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.miranda.MeowCraft.Manager.ConfigManager.treasure;

public class TreasureSet {
    private HashMap<String, Treasure> treasures;

    public TreasureSet() throws IOException, ClassNotFoundException {
        this.treasures = (HashMap<String, Treasure>) IO.decodeData(treasure.getString("treasures"));
        if (this.treasures == null) {
            this.treasures = new HashMap<>();
            treasure.set("treasures", IO.encodeData(this.treasures));
            ConfigManager.saveConfigs();
        }
    }

    public void addTreasure(String displayName, Treasure addTreasure) throws IOException {
        this.treasures.put(displayName, addTreasure);
        treasure.set("treasures", IO.encodeData(this.treasures));
        ConfigManager.saveConfigs();
    }

    public void removeTreasure(String displayName) throws IOException {
        this.treasures.remove(displayName);
        treasure.set("treasures", IO.encodeData(this.treasures));
        ConfigManager.saveConfigs();
    }

    public Treasure getTreasure(String displayName) {
        return this.treasures.get(displayName);
    }

    public ArrayList<String> getList() {
        return new ArrayList<>(this.treasures.keySet());
    }

    public Treasure getByName(String title) {
        Pattern pattern = Pattern.compile("奖励箱 §9(.+)");
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()) {
            String displayName = matcher.group(1);
            return this.treasures.get(displayName);
        } else {
            return null;
        }
    }
}
