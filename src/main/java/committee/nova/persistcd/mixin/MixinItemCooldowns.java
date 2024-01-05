package committee.nova.persistcd.mixin;

import committee.nova.persistcd.api.IItemCooldowns;
import committee.nova.persistcd.cd.CoolDownRecord;
import net.minecraft.item.Item;
import net.minecraft.util.CooldownTracker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mixin(CooldownTracker.class)
public abstract class MixinItemCooldowns implements IItemCooldowns {
    @Shadow
    private int ticks;

    @Shadow
    @Final
    private Map<Item, CooldownTracker.Cooldown> cooldowns;

    @Shadow
    public abstract void setCooldown(Item item, int cd);

    @Override
    public List<CoolDownRecord> persistcd$getCooldownTicks() {
        return cooldowns.entrySet().stream().map(e -> {
            final AccessorCoolDown instance = (AccessorCoolDown) e.getValue();
            return new CoolDownRecord(
                    e.getKey(),
                    instance.getExpireTicks() - ticks,
                    instance.getExpireTicks() - instance.getCreateTicks()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public void persistcd$addCoolDown(CoolDownRecord cd) {
        setCooldown(cd.item(), 50000);
        final AccessorCoolDown instance = (AccessorCoolDown) cooldowns.get(cd.item());
        final int end = ticks + cd.remain();
        final int start = end - cd.total();
        instance.setCreateTicks(start);
        instance.setExpireTicks(end);
    }
}
