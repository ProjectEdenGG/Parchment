package gg.projecteden.parchment.sidebar;

import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.world.scores.DisplaySlot;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SidebarBufferUtil implements SidebarBufferUtilSpec {
    @Override
    public SidebarBuffer create(String name) {
        return new SidebarBufferImpl(name);
    }

    @Override
    public void hideSidebar(Player player) {
        ClientboundSetDisplayObjectivePacket packet = new ClientboundSetDisplayObjectivePacket(DisplaySlot.SIDEBAR, null);
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }
}