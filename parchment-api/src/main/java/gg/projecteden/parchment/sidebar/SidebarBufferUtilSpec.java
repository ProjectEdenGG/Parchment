package gg.projecteden.parchment.sidebar;

import gg.projecteden.parchment.entity.EntityDataServiceKey;
import org.bukkit.entity.Player;

public interface SidebarBufferUtilSpec {
    EntityDataServiceKey<SidebarBufferUtilSpec> IMPL_KEY = new EntityDataServiceKey<>(SidebarBufferUtilSpec.class);

    SidebarBuffer create(String bufferName);

    void hideSidebar(Player player);
}