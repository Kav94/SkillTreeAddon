package ogc.skilltreeTab.client;

import net.libz.api.InventoryTab;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public final class SkillTreeTab extends InventoryTab {
    private static final Logger LOGGER = LoggerFactory.getLogger("skill_tabs");
    private static final String SKILLS_CLIENT_CLASS = "net.puffish.skillsmod.client.SkillsClientMod";

    public SkillTreeTab(Class<?> skillsScreenClass) {
        super(
                Text.translatable("tab.skill_tabs.skilltree"),
                Identifier.of("skill_tabs", "textures/gui/skill_tab.png"),
                -1,
                skillsScreenClass
        );
    }

    @Override
    public void onClick(MinecraftClient client) {
        super.onClick(client);
        if (client.player == null) {
            return;
        }

        try {
            Class<?> skillsClientClass = Class.forName(SKILLS_CLIENT_CLASS);
            Object skillsClient = skillsClientClass.getMethod("getInstance").invoke(null);
            if (skillsClient == null) {
                return;
            }

            Method openScreen = skillsClientClass.getMethod("openScreen", Optional.class);
            openScreen.invoke(skillsClient, Optional.empty());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException exception) {
            LOGGER.error("Could not call Pufferfish's Skills client API.", exception);
        } catch (InvocationTargetException exception) {
            LOGGER.error("Pufferfish's Skills failed to open its skills screen.", exception.getCause());
        }
    }
}
