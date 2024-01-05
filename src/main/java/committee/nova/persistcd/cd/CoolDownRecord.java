package committee.nova.persistcd.cd;

import net.minecraft.item.Item;

public class CoolDownRecord {
    private final Item item;
    private final int remain;
    private final int total;

    public CoolDownRecord(Item item, int remain, int total) {
        this.item = item;
        this.remain = remain;
        this.total = total;
    }

    public Item item() {
        return item;
    }

    public int remain() {
        return remain;
    }

    public int total() {
        return total;
    }
}
