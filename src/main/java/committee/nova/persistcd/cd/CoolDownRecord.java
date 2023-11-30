package committee.nova.persistcd.cd;

import net.minecraft.world.item.Item;

public record CoolDownRecord(Item item, int remain, int total) {

}
