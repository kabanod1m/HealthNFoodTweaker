package org.winglessbirds.healthnfoodtweaker;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.winglessbirds.healthnfoodtweaker.config.ModConfig;
import org.winglessbirds.healthnfoodtweaker.event.PlayerDamageTakenEvent;
import org.winglessbirds.healthnfoodtweaker.event.PlayerEatEvent;
import org.winglessbirds.healthnfoodtweaker.handlers.*;

public class HealthNFoodTweaker implements ModInitializer {

    public static final String MODID = "healthnfoodtweaker";
    public static final Logger LOG = LoggerFactory.getLogger(MODID);
    public static ModConfig CFG = new ModConfig();

    @Override
    public void onInitialize () {
        // config
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        CFG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        // register server-side event handlers
        ServerPlayConnectionEvents.JOIN.register(new PlayerJoinRespawnHandler());
        ServerPlayerEvents.AFTER_RESPAWN.register(new PlayerJoinRespawnHandler());
        ServerPlayConnectionEvents.DISCONNECT.register(new PlayerLeaveDeathHandler());
        ServerLivingEntityEvents.ALLOW_DEATH.register(new PlayerLeaveDeathHandler());
        ServerTickEvents.END_SERVER_TICK.register(new ServerTickHandler());
        PlayerDamageTakenEvent.AFTER.register(new PlayerDamageTakenHandler());
        PlayerEatEvent.AFTER.register(new PlayerEatHandler());
        ServerLifecycleEvents.SERVER_STARTED.register(new ServerStartHandler());
    }

}
