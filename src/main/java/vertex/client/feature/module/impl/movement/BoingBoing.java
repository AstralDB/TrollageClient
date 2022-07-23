

package vertex.client.feature.module.impl.movement;

import vertex.client.feature.config.annotation.Setting;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import net.minecraft.client.util.math.MatrixStack;

public class BoingBoing extends Module {
    @Setting(name = "Bounce mul", description = "How much to multiply your velocity by when bouncing", min = 0, max = 3, precision = 1)
    public double bounceMul = .8;

    @Setting(name = "Allow bounce back", description = "Allows you to bounce back when you hit your head.\n§cCAN LEAD TO FAST BOUNCING AND CRASH WHEN MUL > 1.0")
    public boolean bounceBack = false;

    public BoingBoing() {
        super("BoingBoing", "Makes every block a slime block", ModuleType.MOVEMENT);
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
