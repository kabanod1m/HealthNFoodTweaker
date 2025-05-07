package org.winglessbirds.healthnfoodtweaker.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = org.winglessbirds.healthnfoodtweaker.HealthNFoodTweaker.MODID)
@Config.Gui.Background("minecraft:textures/block/dirt.png")
public class ModConfig implements ConfigData {

    @Comment("")
    public boolean enablePassiveHeal = true;

    @Comment("")
    public boolean resetHealOnHit = true;

    @Comment("")
    public boolean disableVanillaHeal = true;

    @Comment("")
    public boolean enablePassiveHunger = true;

    @Comment("Ticks until")
    public int ticksUntilHeal = 600;

    @Comment("")
    public int minFoodHeal = 8;

    @Comment("")
    public float healthToHeal = 1.0f;

    @Comment("")
    public float healExhaustion = 0.0f;

    @Comment("")
    public float passiveExhaustion = 0.001953125f;

}
