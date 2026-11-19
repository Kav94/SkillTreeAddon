package ogc.skilltreeTab.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.libz.registry.TabRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkilltreeTabClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("skill_tabs");

    @Override
    public void onInitializeClient() {
        if (!FabricLoader.getInstance().isModLoaded("puffish_skills")) {
            LOGGER.warn("Pufferfish's Skills is not installed; SkillTree tab was not registered.");
            return;
        }

        try {
            Class<?> skillsScreen = Class.forName(
                    "net.puffish.skillsmod.client.gui.SkillsScreen",
                    false,
                    getClass().getClassLoader()
            );
            TabRegistry.registerInventoryTab(new SkillTreeTab(skillsScreen));
        } catch (ClassNotFoundException exception) {
            LOGGER.error("Pufferfish's Skills was found, but SkillsScreen could not be loaded.", exception);
        }
    }
}
