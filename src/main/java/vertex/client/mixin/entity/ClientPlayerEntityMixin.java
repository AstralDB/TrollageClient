

package vertex.client.mixin.entity;

import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleRegistry;
import vertex.client.feature.module.impl.exploit.PortalGUI;
import vertex.client.feature.module.impl.movement.NoPush;
import vertex.client.feature.module.impl.movement.Phase;
import vertex.client.feature.module.impl.render.Freecam;
import vertex.client.helper.manager.ConfigManager;
import vertex.client.helper.util.Rotations;
import vertex.client.helper.util.Utils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    public void coffee_preTick(CallbackInfo ci) {
        Utils.TickManager.tick();
        if (!ConfigManager.enabled) {
            ConfigManager.enableModules();
        }
        for (Module module : ModuleRegistry.getModules()) {
            if (module.isEnabled()) {
                module.tick();
            }
        }
    }

    @Redirect(method = "updateNausea", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;shouldPause()Z"))
    public boolean coffee_overwritePauseScreen(Screen screen) {
        return Objects.requireNonNull(ModuleRegistry.getByClass(PortalGUI.class)).isEnabled() || screen.shouldPause();
    }

    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    public void coffee_preventPush(double x, double z, CallbackInfo ci) {
        if (Objects.requireNonNull(ModuleRegistry.getByClass(Freecam.class)).isEnabled() || Objects.requireNonNull(ModuleRegistry.getByClass(NoPush.class))
                .isEnabled() || Objects.requireNonNull(ModuleRegistry.getByClass(Phase.class)).isEnabled()) {
            ci.cancel();
        }
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"))
    float coffee_replaceMovementPacketYaw(ClientPlayerEntity instance) {
        if (Rotations.isEnabled()) {
            return Rotations.getClientYaw();
        } else {
            return instance.getYaw();
        }
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"))
    float coffee_replaceMovementPacketPitch(ClientPlayerEntity instance) {
        if (Rotations.isEnabled()) {
            return Rotations.getClientPitch();
        } else {
            return instance.getPitch();
        }
    }

}
