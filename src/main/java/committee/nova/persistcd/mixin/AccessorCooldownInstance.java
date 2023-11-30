package committee.nova.persistcd.mixin;

import net.minecraft.world.item.ItemCooldowns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemCooldowns.CooldownInstance.class)
public interface AccessorCooldownInstance {
    @Accessor
    void setStartTime(int startTime);

    @Accessor
    void setEndTime(int endTime);

    @Accessor
    int getStartTime();

    @Accessor
    int getEndTime();
}
