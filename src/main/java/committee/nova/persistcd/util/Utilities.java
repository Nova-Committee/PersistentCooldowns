package committee.nova.persistcd.util;

import committee.nova.persistcd.api.IItemCooldowns;
import committee.nova.persistcd.cd.CoolDownRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Objects;

public class Utilities {
    public static void loadCooldowns(NBTTagCompound tag, EntityPlayer player) {
        if (!tag.hasKey("persist_cd")) return;
        final NBTTagList cooldowns = tag.getTagList("persist_cd", 10);
        cooldowns.forEach(t -> {
            if (!(t instanceof NBTTagCompound)) return;
            final NBTTagCompound c = (NBTTagCompound) t;
            final ResourceLocation rl = new ResourceLocation(c.getString("item"));
            final Item item = ForgeRegistries.ITEMS.getValue(rl);
            if (item == null || item.equals(Items.AIR)) return;
            ((IItemCooldowns) player.getCooldownTracker()).persistcd$addCoolDown(
                    new CoolDownRecord(
                            item,
                            c.getInteger("remain"),
                            c.getInteger("total")
                    )
            );
        });
    }

    public static void saveCooldowns(NBTTagCompound tag, EntityPlayer player) {
        final NBTTagList cooldowns = new NBTTagList();
        ((IItemCooldowns) player.getCooldownTracker()).persistcd$getCooldownTicks().forEach(r -> {
            final NBTTagCompound cooldown = new NBTTagCompound();
            cooldown.setString("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(r.item())).toString());
            cooldown.setInteger("remain", r.remain());
            cooldown.setInteger("total", r.total());
            cooldowns.appendTag(cooldown);
        });
        tag.setTag("persist_cd", cooldowns);
    }
}
