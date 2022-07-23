

package vertex.client.mixin.screen;

import vertex.client.feature.command.impl.SelfDestruct;
import vertex.client.feature.gui.HasSpecialCursor;
import vertex.client.helper.render.Cursor;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin extends AbstractParentElement {
    @Shadow
    public int height;

    @Shadow
    public int width;

    @Shadow
    @Final
    private List<Element> children;


    @Override
    public List<? extends Element> children() { // have to do this because java will shit itself when i dont overwrite this
        return this.children;
    }

    void handleCursor(double y) {
        long c = Cursor.STANDARD;
        if (!SelfDestruct.shouldSelfDestruct()) {
            for (Element child : this.children) {
                if (child instanceof HasSpecialCursor specialCursor) {
                    if (specialCursor.shouldApplyCustomCursor()) {
                        c = specialCursor.getCursor();
                    }
                }
            }
        }
        Cursor.setGlfwCursor(c);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        handleCursor(mouseY);
        super.mouseMoved(mouseX, mouseY);
    }
}
