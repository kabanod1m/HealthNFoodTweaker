package org.winglessbirds.healthnfoodtweaker.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = org.winglessbirds.healthnfoodtweaker.HealthNFoodTweaker.MODID)
@Config.Gui.Background("minecraft:textures/block/dirt.png")
public class ModConfig implements ConfigData {

    @Comment("Configurable using settings below")
    public boolean enablePassiveHeal = true;

    @Comment("Whether to reset waiting time until the next passive healing tick on taking damage to \"Heal every (ticks)\"")
    public boolean resetHealOnHit = true;

    @Comment("Whether to reset player's exhaustion upon eating something.\n1 food level (or saturation if you have some) is spent when Exhaustion reaches 4.0.\n\nMight help a little if you for some reason prefer to have \"Also disable Vanilla saturation\" on true.")
    public boolean resetExhaustionOnEat = false;

    @Comment("Whether to disable health replenishment from the Vanilla hunger system (that is fast and costs a lot of food)")
    public boolean disableVanillaHeal = true;

    @Comment("Value \"true\" not recommended. Reason: you will get really hungry very fast, actions' hunger costs are tweaked to account for saturation system in Vanilla.\n\nSaturation in Vanilla is a second, invisible (made visible by some mods) hunger bar that regenerates you faster if it's not empty.\nDefault settings of this mod burn through saturation faster using passive hunger than through food levels but you can also disable it completely using this option.\n(Disabling it will make the game always assume saturation is 0)")
    public boolean disableVanillaSaturation = false;

    @Comment("In Vanilla Minecraft actions such as walking and standing still don't use up any food at all,\nmeaning it's possible to stay AFK and never die from hunger.\nThis adds passive food drain even when doing nothing, but only when food isn't drained in other ways currently.")
    public boolean enablePassiveHunger = true;

    @Comment("Ticks until you will be passively healed 1 time after taking damage.\n\nThere are 20 ticks in 1 second, so 600 ticks = 30 seconds.")
    public int ticksUntilHeal = 600;

    @Comment("Minimum food level to be able to heal. Set to 0 to always be able to passively heal.\n\nMaximum food level is 20, so 8 = 4 shanks (8 half-shanks).")
    public int minFoodHeal = 8;

    @Comment("How much health must be healed every time you passively heal up.\n\nMaximum health is 20, so 1 = half a heart.")
    public float healthToHeal = 1.0f;

    @Comment("How much food passive healing should cost.\n1 food level (or saturation if you have some) is spent when Exhaustion reaches 4.0.\nFor comparison, healing half a heart in Vanilla costs 6.0 Exhaustion. Jumping 1 time costs 0.05.")
    public float healExhaustion = 0.0f;

    @Comment("How much exhaustion should be added each tick (1/20th of a second) if passive hunger is enabled above.\n1 food level (or saturation if you have some) is spent when Exhaustion reaches 4.0.\nFor comparison, when under the Hunger effect, each tick costs 0.005 Exhaustion. That is 10.24 times more than the default value here. (0.00048828125)")
    public float passiveExhaustion = 0.00048828125f;

    @Comment("By how much should Passive hunger exhaustion amount be multiplied when some saturation is present.")
    public float passiveExhaustionSatMul = 4.0f;

}
