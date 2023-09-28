package committee.nova.persistentcooldowns.util;

import committee.nova.persistentcooldowns.api.IItemCooldowns;
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

    public static void saveCooldowns(CompoundTag tag, Player player) {
        final ListTag cooldowns = new ListTag();
        ((IItemCooldowns) player.getCooldowns()).getCooldownTicks().forEach((r, i) -> {
            final CompoundTag cooldown = new CompoundTag();
            cooldown.putString("item", r.toString());
            cooldown.putInt("cd", i);
            cooldowns.add(cooldown);
        });
        tag.put("persistent_cooldowns", cooldowns);
    }
}
