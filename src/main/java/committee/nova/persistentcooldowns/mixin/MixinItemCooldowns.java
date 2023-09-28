package committee.nova.persistentcooldowns.mixin;

import committee.nova.persistentcooldowns.api.ICooldownInstance;
import committee.nova.persistentcooldowns.api.IItemCooldowns;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;

@Mixin(ItemCooldowns.class)
public abstract class MixinItemCooldowns implements IItemCooldowns {
    @Shadow
    @Final
    private Map<Item, ItemCooldowns.CooldownInstance> cooldowns;

    @Override
    public Map<ResourceLocation, Integer> getCooldownTicks() {
        final Map<ResourceLocation, Integer> map = new HashMap<>();
        cooldowns.forEach((i, c) -> map.put(ForgeRegistries.ITEMS.getKey(i), ((ICooldownInstance) c).getTime()));
        return map;
    }
}
