package committee.nova.persistentcooldowns.mixin;

import com.mojang.authlib.GameProfile;
import committee.nova.persistentcooldowns.api.IItemCooldowns;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayer extends Player {
    public MixinServerPlayer(Level l, BlockPos p, float f, GameProfile g) {
        super(l, p, f, g);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void inject$addAdditionalSaveData(CompoundTag tag0, CallbackInfo ci) {
        final CompoundTag tag = getPersistentData();
        final ListTag cooldowns = new ListTag();
        ((IItemCooldowns) getCooldowns()).getCooldownTicks().forEach((r, i) -> {
            final CompoundTag cooldown = new CompoundTag();
            cooldown.putString("item", r.toString());
            cooldown.putInt("cd", i);
            cooldowns.add(cooldown);
        });
        tag.put("persistent_cooldowns", cooldowns);
    }
}
