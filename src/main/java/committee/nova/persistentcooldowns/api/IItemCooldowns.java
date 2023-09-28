package committee.nova.persistentcooldowns.api;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public interface IItemCooldowns {
    Map<ResourceLocation, Integer> getCooldownTicks();
}
