package cn.miranda.MeowCraft.Enum;

import org.bukkit.potion.PotionEffectType;

public enum PotionEffect {
    speed("速度", PotionEffectType.SPEED),
    slowness("缓慢", PotionEffectType.SLOW),
    haste("急迫", PotionEffectType.FAST_DIGGING),
    mining_fatigue("挖掘疲劳", PotionEffectType.SLOW_DIGGING),
    strength("力量", PotionEffectType.INCREASE_DAMAGE),
    instant_health("瞬间治疗", PotionEffectType.HEAL),
    instant_damage("瞬间伤害", PotionEffectType.HARM),
    jump_boost("跳跃提升", PotionEffectType.JUMP),
    nausea("反胃", PotionEffectType.CONFUSION),
    regeneration("生命恢复", PotionEffectType.REGENERATION),
    resistance("抗性提升", PotionEffectType.DAMAGE_RESISTANCE),
    fire_resistance("防火", PotionEffectType.FIRE_RESISTANCE),
    water_breathing("水下呼吸", PotionEffectType.WATER_BREATHING),
    invisibility("隐身", PotionEffectType.INVISIBILITY),
    blindness("失明", PotionEffectType.BLINDNESS),
    night_vision("夜视", PotionEffectType.NIGHT_VISION),
    hunger("饥饿", PotionEffectType.HUNGER),
    weakness("虚弱", PotionEffectType.WEAKNESS),
    poison("中毒", PotionEffectType.POISON),
    wither("凋零", PotionEffectType.WITHER),
    health_boost("生命提升", PotionEffectType.HEALTH_BOOST),
    absorption("伤害吸收", PotionEffectType.ABSORPTION),
    saturation("饱和", PotionEffectType.SATURATION),
    glowing("发光", PotionEffectType.GLOWING),
    levitation("飘浮", PotionEffectType.LEVITATION),
    luck("幸运", PotionEffectType.LUCK),
    unluck("霉运", PotionEffectType.UNLUCK),
    slow_falling("缓降", PotionEffectType.SLOW_FALLING),
    conduit_power("潮涌能量", PotionEffectType.CONDUIT_POWER),
    dolphins_grace("海豚的恩惠", PotionEffectType.DOLPHINS_GRACE),
    bad_omen("不祥之兆", PotionEffectType.BAD_OMEN),
    hero_of_the_village("村庄英雄", PotionEffectType.HERO_OF_THE_VILLAGE);

    private final String name;
    private final PotionEffectType potionEffectType;

    PotionEffect(String name, PotionEffectType potionEffectType) {
        this.name = name;
        this.potionEffectType = potionEffectType;
    }

    public String getName() {
        return this.name;
    }
}
