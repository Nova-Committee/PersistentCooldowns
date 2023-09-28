package committee.nova.persistentcooldowns;

import committee.nova.persistentcooldowns.util.Utilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(PersistentCooldowns.MODID)
public class PersistentCooldowns {
    public static final String MODID = "persistentcooldowns";

    public PersistentCooldowns() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
                () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onJoin(PlayerEvent.PlayerLoggedInEvent event) {
        final Player player = event.getEntity();
        Utilities.loadCooldowns(player.getPersistentData(), player);
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        final CompoundTag temp = new CompoundTag();
        Utilities.saveCooldowns(temp, event.getOriginal());
        Utilities.loadCooldowns(temp, event.getEntity());
    }
}
