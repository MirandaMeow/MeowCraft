package cn.miranda.MeowCraft.Enum;

public enum Notify {
    No_Console("§c该命令不能在控制台运行"),
    Not_For_Console("§c不能对控制台使用"),
    No_Permission("§c你没有权限"),
    No_Player("§c指定玩家不在线"),
    No_Occ("§c你没有职业"),
    Invalid_Input("§c参数不正确"),
    Invalid_Input_Length("参数长度不正确");
    private final String string;

    Notify(String string) {
        this.string = string;
    }

    public String getString() {
        return this.string;
    }
}
