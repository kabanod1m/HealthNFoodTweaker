package org.winglessbirds.healthnfoodtweaker.util;

import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.datafixer.Schemas;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.Util;
import net.minecraft.util.WorldSavePath;
import org.jetbrains.annotations.Nullable;
import org.winglessbirds.healthnfoodtweaker.HealthNFoodTweaker;
import org.winglessbirds.healthnfoodtweaker.entity.player.ExtendedPlayerEntity;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class WorldExtSaveHandler {

    public static File initializeModFolder (Path dirPlayerdata) {
        File dirMod = new File(dirPlayerdata.toFile(), HealthNFoodTweaker.MODID);
        dirMod.mkdir();
        return dirMod;
    }

    public static void savePlayerData (ExtendedPlayerEntity extplayer) {
        try {
            File dirMod = initializeModFolder(extplayer.player.getServer().getSavePath(WorldSavePath.PLAYERDATA));

            NbtCompound nbtCompound = extplayer.writeNbt(new NbtCompound());
            File file = File.createTempFile(extplayer.player.getUuidAsString() + "-", ".dat", dirMod);
            NbtIo.writeCompressed(nbtCompound, file);
            File file2 = new File(dirMod, extplayer.player.getUuidAsString() + ".dat");
            File file3 = new File(dirMod, extplayer.player.getUuidAsString() + ".dat_old");
            Util.backupAndReplace(file2, file, file3);
        } catch (Exception e) {
            HealthNFoodTweaker.LOG.warn("Failed to save additional player data for {}", extplayer.player.getName().getString());
        }
    }

    public static void saveSPPlayerData (ExtendedPlayerEntity extplayer) {
        try {
            File dirMod = initializeModFolder(extplayer.player.getServer().getSavePath(WorldSavePath.PLAYERDATA));

            NbtCompound tagData = new NbtCompound();
            NbtCompound tagPlayer = new NbtCompound();
            NbtCompound nbtCompound = extplayer.writeNbt(new NbtCompound());
            tagPlayer.put("Player", nbtCompound);
            tagData.put("Data", tagPlayer);
            File file = File.createTempFile("level" + "-", ".dat", dirMod);
            NbtIo.writeCompressed(tagData, file);
            File file2 = new File(dirMod, "level" + ".dat");
            File file3 = new File(dirMod, "level" + ".dat_old");
            Util.backupAndReplace(file2, file, file3);
        } catch (Exception e) {
            HealthNFoodTweaker.LOG.warn("Failed to save additional player data for host player {}", extplayer.player.getName().getString());
        }
    }

    @Nullable
    public static NbtCompound loadPlayerData (ExtendedPlayerEntity extplayer) throws NoSuchFileException, NoSuchFieldException {
        NbtCompound nbtCompound = null;

        try {
            File dirMod = initializeModFolder(extplayer.player.getServer().getSavePath(WorldSavePath.PLAYERDATA));

            File file = new File(dirMod, extplayer.player.getUuidAsString() + ".dat");
            if (file.exists() && file.isFile()) {
                nbtCompound = NbtIo.readCompressed(file);
            }
        } catch (Exception e) {
            throw new NoSuchFileException("Failed to load additional player data for " + extplayer.player.getName().getString());
        }

        if (nbtCompound != null) {
            int i = NbtHelper.getDataVersion(nbtCompound, -1);
            extplayer.readNbt(DataFixTypes.PLAYER.update(Schemas.getFixer(), nbtCompound, i));
        }

        return nbtCompound;
    }

    @Nullable
    public static NbtCompound loadSPPlayerData (ExtendedPlayerEntity extplayer) throws NoSuchFileException, NoSuchFieldException {
        NbtCompound nbtCompound = null;

        try {
            File dirMod = initializeModFolder(extplayer.player.getServer().getSavePath(WorldSavePath.PLAYERDATA));

            File file = new File(dirMod, "level" + ".dat");
            if (file.exists() && file.isFile()) {
                nbtCompound = NbtIo.readCompressed(file);
            }
        } catch (Exception e) {
            throw new NoSuchFileException("Failed to load additional player data for host player " + extplayer.player.getName().getString());
        }

        if (nbtCompound != null) {
            int i = NbtHelper.getDataVersion(nbtCompound, -1);
            if (!nbtCompound.contains("Data", NbtElement.COMPOUND_TYPE)) return nbtCompound;
            if (!nbtCompound.getCompound("Data").contains("Player", NbtElement.COMPOUND_TYPE)) return nbtCompound;
            extplayer.readNbt(DataFixTypes.PLAYER.update(Schemas.getFixer(), nbtCompound.getCompound("Data").getCompound("Player"), i));
        }

        return nbtCompound;
    }

}
