package committee.nova.persistcd;

import com.mojang.logging.LogUtils;
import committee.nova.persistcd.util.Utilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(PersistentCooldowns.MODID)
public class PersistentCooldowns {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "persistcd";

    public PersistentCooldowns() {
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
