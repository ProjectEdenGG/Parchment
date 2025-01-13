package gg.projecteden.parchment.entity;

import gg.projecteden.parchment.sidebar.SidebarBufferUtil;
import gg.projecteden.parchment.sidebar.SidebarBufferUtilSpec;

public class EntityDataServices {

    private static boolean initialized;

    public static void init() {
        if (initialized) {
            throw new RuntimeException("EntityData Services already initialized");
        }
        initialized = true;

        // Initialize Services Here
        SidebarBufferUtilSpec.IMPL_KEY.set(new SidebarBufferUtil());
    }

}