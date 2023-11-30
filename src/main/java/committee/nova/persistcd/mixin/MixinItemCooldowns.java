package committee.nova.persistcd.mixin;

import committee.nova.persistcd.api.IItemCooldowns;
import committee.nova.persistcd.cd.CoolDownRecord;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;

@Mixin(ItemCooldowns.class)
public abstract class MixinItemCooldowns implements IItemCooldowns {
    @Shadow
    @Final
    private Map<Item, ItemCooldowns.CooldownInstance> cooldowns;

    @Shadow
    public abstract void addCooldown(Item item, int cd);

    @Shadow
    private int tickCount;

    @Override
    public List<CoolDownRecord> persistcd$getCooldownTicks() {
        return cooldowns.entrySet().stream().map(e -> {
            final AccessorCooldownInstance instance = (AccessorCooldownInstance) e.getValue();
            return new CoolDownRecord(
                    e.getKey(),
                    instance.getEndTime() - tickCount,
                    instance.getEndTime() - instance.getStartTime()
            );
        }).toList();
    }

    @Override
    public void persistcd$addCoolDown(CoolDownRecord cd) {
        addCooldown(cd.item(), 50000);
        final AccessorCooldownInstance instance = (AccessorCooldownInstance) cooldowns.get(cd.item());
        final int end = tickCount + cd.remain();
        final int start = end - cd.total();
        instance.setStartTime(start);
        instance.setEndTime(end);
    }
}
