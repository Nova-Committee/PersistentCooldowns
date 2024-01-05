package committee.nova.persistcd;

import committee.nova.persistcd.util.Utilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@Mod(modid = "persistcd", useMetadata = true, acceptableRemoteVersions = "*")
public class PersistentCooldowns {
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onJoin(PlayerLoggedInEvent event) {
        final EntityPlayer player = event.player;
        Utilities.loadCooldowns(player.getEntityData(), player);
    }

    @SubscribeEvent
    public void onClone(Clone event) {
        final NBTTagCompound temp = new NBTTagCompound();
        Utilities.saveCooldowns(temp, event.getOriginal());
        Utilities.loadCooldowns(temp, event.getEntityPlayer());
    }
}
