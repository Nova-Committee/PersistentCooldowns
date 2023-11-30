package committee.nova.persistcd.api;

import committee.nova.persistcd.cd.CoolDownRecord;

import java.util.List;

public interface IItemCooldowns {
    List<CoolDownRecord> persistcd$getCooldownTicks();

    void persistcd$addCoolDown(CoolDownRecord cd);
}
