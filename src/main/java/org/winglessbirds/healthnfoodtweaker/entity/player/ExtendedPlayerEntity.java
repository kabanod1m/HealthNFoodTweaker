package org.winglessbirds.healthnfoodtweaker.entity.player;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.MinecraftServer;
import org.winglessbirds.healthnfoodtweaker.HealthNFoodTweaker;
import org.winglessbirds.healthnfoodtweaker.PlayerWatcher;
import org.winglessbirds.healthnfoodtweaker.util.WorldExtSaveHandler;

import java.util.Objects;

public class ExtendedPlayerEntity {

    public enum CreateDestroyReason {
        JOINLEAVE, RESPAWNDEATH
    }

    public final PlayerEntity player;
    private final HungerManager playerHunger;

    // variables for runtime logic
    private float prevFoodExhaustion;

    // variables that need to be saved to disk
    private int healTimer; private static final String healTimerAName = "HealTimer";

    public ExtendedPlayerEntity (PlayerEntity player, CreateDestroyReason reason) {
        this.player = player;
        this.playerHunger = player.getHungerManager();
        prevFoodExhaustion = playerHunger.getExhaustion();

        switch (reason) {
            case JOINLEAVE: {
                try {
                    if (!player.getServer().isSingleplayer()) {
                        WorldExtSaveHandler.loadPlayerData(this);
                    } else {
                        WorldExtSaveHandler.loadSPPlayerData(this);
                    }
                } catch (Exception e) { // if anything goes wrong, assume values specified in config
                    healTimer = HealthNFoodTweaker.CFG.ticksUntilHeal;
                }
            }
            break;
            case RESPAWNDEATH: {
                healTimer = HealthNFoodTweaker.CFG.ticksUntilHeal;
            }
        }
    }

    public void DestroyExtendedPlayerEntity (CreateDestroyReason reason) { // must be called manually
        switch (reason) {
            case JOINLEAVE: {
                WorldExtSaveHandler.savePlayerData(this);

                // the following code is to save singleplayer data (host data)
                MinecraftServer server = this.player.getServer();
                assert server != null; // the method this is in is NEVER called on the client side
                if (!server.isSingleplayer()) { return; } // if current server is not singleplayer, we are not interested (multiplayer servers store player data in /playerdata/ instead of level.dat)
                /*
                 NullPointerException in the following line is ignored because it only appears if the playerdata was constructed
                manually and wasn't fully filled. It never appears for real players. It is only needed to find the host player,
                and the host player must always be real.
                */
                PlayerEntity hostPlayer = server.getPlayerManager().getPlayer(Objects.requireNonNull(server.getHostProfile().getName()));

                if (!this.player.equals(hostPlayer)) return; // if it wasn't the host player who has left the server, we are not interested

                PlayerWatcher watcher;
                try {
                    watcher = PlayerWatcher.getWatcher(hostPlayer);
                } catch (NullPointerException e) {
                    HealthNFoodTweaker.LOG.warn("Something is going wrong! DestroyExtendedPlayerEntity tried getting a player that doesn't exist:\n" + e.getMessage());
                    return;
                }
                WorldExtSaveHandler.saveSPPlayerData(watcher.extplayer);
            }
            break;
            case RESPAWNDEATH: {
                return;
            }
        }

    }

    @Override
    public boolean equals (Object other) {
        if (other == null || this.getClass() != other.getClass()) return false;

        final ExtendedPlayerEntity otherObject = (ExtendedPlayerEntity)other;

        return this.player.equals(otherObject.player);
    }

    public void readNbt (NbtCompound nbt) throws NoSuchFieldException {
        if (nbt.contains(healTimerAName, NbtElement.INT_TYPE)) {
            this.healTimer = nbt.getInt(healTimerAName);

            return;
        }
        throw new NoSuchFieldException();
    }

    public NbtCompound writeNbt (NbtCompound nbt) {
        nbt.putInt(healTimerAName, this.healTimer);

        return nbt;
    }

    private void tickHealing () {
        if (player.getHealth() == player.getMaxHealth()) return;

        if (healTimer-- > 0) return;

        if (playerHunger.getFoodLevel() < HealthNFoodTweaker.CFG.minFoodHeal) return;

        playerHunger.addExhaustion(HealthNFoodTweaker.CFG.healExhaustion);
        player.heal(HealthNFoodTweaker.CFG.healthToHeal);
        healTimer = HealthNFoodTweaker.CFG.ticksUntilHeal;
    }

    public void onPlayerDamageTaken () {
        if (HealthNFoodTweaker.CFG.resetHealOnHit) healTimer = HealthNFoodTweaker.CFG.ticksUntilHeal;
    }

    private void tickHunger () {
        if (playerHunger.getExhaustion() == prevFoodExhaustion) playerHunger.addExhaustion(HealthNFoodTweaker.CFG.passiveExhaustion * ((!HealthNFoodTweaker.CFG.disableVanillaSaturation && playerHunger.getSaturationLevel() > 0.0f) ? HealthNFoodTweaker.CFG.passiveExhaustionSatMul : 1.0f));
        prevFoodExhaustion = playerHunger.getExhaustion();
    }

    public void tick () {
        if (HealthNFoodTweaker.CFG.enablePassiveHunger) tickHunger();
        if (HealthNFoodTweaker.CFG.enablePassiveHeal) tickHealing();
    }

}
