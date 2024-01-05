package committee.nova.persistcd.mixin;

import com.mojang.authlib.GameProfile;
import committee.nova.persistcd.util.Utilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerMP.class)
public abstract class MixinEntityPlayerMP extends EntityPlayer {

    public MixinEntityPlayerMP(World worldIn, GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }

    @Inject(method = "writeEntityToNBT", at = @At("HEAD"))
    private void inject$writeEntityToNbt(NBTTagCompound tag, CallbackInfo ci) {
        Utilities.saveCooldowns(getEntityData(), this);
    }
}
