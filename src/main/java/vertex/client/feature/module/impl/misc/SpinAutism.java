

package vertex.client.feature.module.impl.misc;

import vertex.client.VertexMain;
import vertex.client.feature.config.DoubleSetting;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.util.Rotations;
import vertex.client.helper.util.Utils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;

import java.util.Objects;

public class SpinAutism extends Module {

    final double r = 0;
    //    final SliderValue speed = (SliderValue) this.config.create("Timeout", 5, 0, 100, 0).description("How much to wait between rotations");
    final DoubleSetting speed = this.config.create(new DoubleSetting.Builder(5).name("Delay")
            .description("How much to wait when spinning")
            .min(0)
            .max(100)
            .precision(0)
            .get());
    int timeout = 0;

    public SpinAutism() {
        super("SpinAutism", "Spins around like a maniac and throws whatever you have", ModuleType.MISC);
    }

    @Override
    public void tick() {
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public String getContext() {
        return null;
    }

    @Override
    public void onWorldRender(MatrixStack matrices) {
    }

    @Override
    public void onFastTick() {
        timeout--; // decrease timeout
        if (timeout > 0) {
            return; // if timeout isn't expired, do nothing
        }
        timeout = (int) Math.floor(speed.getValue()); // timeout expired, set it back to full
        Rotations.setClientPitch((float) ((Math.random() * 60) - 30));
        Rotations.setClientYaw((float) (Math.random() * 360));
        PlayerInteractItemC2SPacket p = new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, Utils.increaseAndCloseUpdateManager(VertexMain.client.world));
        Objects.requireNonNull(VertexMain.client.getNetworkHandler()).sendPacket(p);
        PlayerMoveC2SPacket p1 = new PlayerMoveC2SPacket.LookAndOnGround(Rotations.getClientYaw(),
                Rotations.getClientPitch(),
                Objects.requireNonNull(VertexMain.client.player).isOnGround()
        );
        VertexMain.client.getNetworkHandler().sendPacket(p1);
    }

    @Override
    public void onHudRender() {

    }
}
