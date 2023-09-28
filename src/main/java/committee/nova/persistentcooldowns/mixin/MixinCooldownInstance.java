package committee.nova.persistentcooldowns.mixin;

import committee.nova.persistentcooldowns.api.ICooldownInstance;
import net.minecraft.world.item.ItemCooldowns;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemCooldowns.CooldownInstance.class)
public abstract class MixinCooldownInstance implements ICooldownInstance {
    @Shadow
    @Final
    int endTime;

    @Shadow
    @Final
    int startTime;

    @Override
    public int getTime() {
        return endTime - startTime;
    }
}
