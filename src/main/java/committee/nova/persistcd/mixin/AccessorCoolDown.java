package committee.nova.persistcd.mixin;

import net.minecraft.util.CooldownTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CooldownTracker.Cooldown.class)
public interface AccessorCoolDown {
    @Accessor
    void setCreateTicks(int startTime);

    @Accessor
    void setExpireTicks(int endTime);

    @Accessor
    int getCreateTicks();

    @Accessor
    int getExpireTicks();
}
