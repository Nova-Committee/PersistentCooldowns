package committee.nova.persistentcooldowns;

import net.minecraft.ResourceLocationException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

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
        final CompoundTag tag = player.getPersistentData();
        if (!tag.contains("persistent_cooldowns")) return;
        final ListTag cooldowns = tag.getList("persistent_cooldowns", 10);
        cooldowns.forEach(t -> {
            if (!(t instanceof CompoundTag c)) return;
            try {
                final ResourceLocation rl = new ResourceLocation(c.getString("item"));
                final Item item = ForgeRegistries.ITEMS.getValue(rl);
                if (item == null || item.equals(Items.AIR)) return;
                player.getCooldowns().addCooldown(item, c.getInt("cd"));
            } catch (ResourceLocationException r) {
                r.printStackTrace();
            }
        });
    }
}
