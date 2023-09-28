package committee.nova.persistentcooldowns.util;

import net.minecraft.ResourceLocationException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

public class Utilities {
    public static void loadCooldowns(CompoundTag tag, Player player) {
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
