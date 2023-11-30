package committee.nova.persistcd.util;

import committee.nova.persistcd.PersistentCooldowns;
import committee.nova.persistcd.api.IItemCooldowns;
import committee.nova.persistcd.cd.CoolDownRecord;
import net.minecraft.ResourceLocationException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class Utilities {
    public static void loadCooldowns(CompoundTag tag, Player player) {
        if (!tag.contains("persist_cd")) return;
        final ListTag cooldowns = tag.getList("persist_cd", 10);
        cooldowns.forEach(t -> {
            if (!(t instanceof CompoundTag c)) return;
            try {
                final ResourceLocation rl = new ResourceLocation(c.getString("item"));
                final Item item = ForgeRegistries.ITEMS.getValue(rl);
                if (item == null || item.equals(Items.AIR)) return;
                ((IItemCooldowns) player.getCooldowns()).persistcd$addCoolDown(
                        new CoolDownRecord(
                                item,
                                c.getInt("remain"),
                                c.getInt("total")
                        )
                );
            } catch (ResourceLocationException e) {
                PersistentCooldowns.LOGGER.error("Failed to parse item, that's weird.", e);
            }
        });
    }

    public static void saveCooldowns(CompoundTag tag, Player player) {
        final ListTag cooldowns = new ListTag();
        ((IItemCooldowns) player.getCooldowns()).persistcd$getCooldownTicks().forEach(r -> {
            final CompoundTag cooldown = new CompoundTag();
            cooldown.putString("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(r.item())).toString());
            cooldown.putInt("remain", r.remain());
            cooldown.putInt("total", r.total());
            cooldowns.add(cooldown);
        });
        tag.put("persist_cd", cooldowns);
    }
}
