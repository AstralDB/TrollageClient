

package vertex.client.feature.module.impl.world;

import vertex.client.VertexMain;
import vertex.client.feature.config.DoubleSetting;
import vertex.client.feature.config.StringSetting;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.event.EventType;
import vertex.client.helper.event.Events;
import vertex.client.helper.event.events.MouseEvent;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class Annihilator extends Module {

    final DoubleSetting range = this.config.create(new DoubleSetting.Builder(5).name("Range")
            .description("Range of the nuke")
            .min(1)
            .max(14)
            .precision(0)
            .get());
    final StringSetting block = this.config.create(new StringSetting.Builder("air").name("Block").description("The block to fill with").get());


    public Annihilator() {
        super("Annihilator", "Nukes whatever you click at, requires /fill permissions", ModuleType.WORLD);
        Events.registerEventHandler(EventType.MOUSE_EVENT, event -> {
            if (!this.isEnabled()) {
                return;
            }
            if (client.player == null) {
                return;
            }
            MouseEvent event1 = (MouseEvent) event;
            if (event1.getButton() == 0 && event1.getAction() == 1) {
                mousePressed();
            }
        });
    }

    void mousePressed() {
        if (client.currentScreen != null) {
            return;
        }
        HitResult hr = Objects.requireNonNull(client.player).raycast(200d, 0f, true);
        Vec3d pos1 = hr.getPos();
        BlockPos pos = new BlockPos(pos1);
        int startY = MathHelper.clamp(r(pos.getY() - range.getValue()),
                Objects.requireNonNull(VertexMain.client.world).getBottomY(),
                VertexMain.client.world.getTopY()
        );
        int endY = MathHelper.clamp(r(pos.getY() + range.getValue()), VertexMain.client.world.getBottomY(), VertexMain.client.world.getTopY());
        String cmd = "/fill " + r(pos.getX() - range.getValue()) + " " + startY + " " + r(pos.getZ() - range.getValue()) + " " + r(pos.getX() + range.getValue()) + " " + endY + " " + r(
                pos.getZ() + range.getValue()) + " " + "minecraft:" + block.getValue();
        //        System.out.println(cmd);
        client.player.sendChatMessage(cmd);
    }

    int r(double v) {
        return (int) Math.round(v);
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
    public void onHudRender() {

    }
}
