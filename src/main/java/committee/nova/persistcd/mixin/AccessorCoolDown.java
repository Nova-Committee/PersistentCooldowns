package committee.nova.persistcd.mixin;

import net.minecraft.util.CooldownTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CooldownTracker.Cooldown.class)
public interface AccessorCoolDown {
    @Mutable
    @Accessor
    void setCreateTicks(int startTime);

    @Mutable
    @Accessor
    void setExpireTicks(int endTime);

    @Accessor
    int getCreateTicks();

    @Accessor
    int getExpireTicks();
}
