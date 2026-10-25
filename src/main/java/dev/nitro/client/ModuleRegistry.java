package dev.nitro.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModuleRegistry {
    private final List<ClientModule> modules = new ArrayList<>();
    public void add(String name, String description, String category) {
        modules.add(new ClientModule(name, description, category));
    }
    public List<ClientModule> all() { return Collections.unmodifiableList(modules); }
    public List<ClientModule> inCategory(String category) {
        return modules.stream().filter(m -> m.category().equals(category)).toList();
    }
    public void registerDefaults() {
        // Working framework/UI toggles; gameplay modules are intentionally added incrementally.
        add("Auto Clicker", "Configurable click helper placeholder", "Combat");
        add("Reach", "Range settings placeholder", "Combat");
        add("Velocity", "Knockback options placeholder", "Combat");
        add("Sprint", "Sprint helper placeholder", "Misc");
        add("Auto Tool", "Tool selection placeholder", "Misc");
        add("Fast Place", "Placement settings placeholder", "Misc");
        add("Chunk Finder", "Chunk analysis placeholder", "Donut");
        add("Auto Sell", "Server-specific utility placeholder", "Donut");
        add("Suspicious ESP", "Mark manually selected areas", "Basefinding");
        add("Hole ESP", "Highlight selected block patterns", "Basefinding");
        add("Fullbright", "Brightness option placeholder", "Render");
        add("Mob ESP", "Entity overlay placeholder", "Render");
        add("Storage ESP", "Storage overlay placeholder", "Render");
        add("HUD", "Client HUD settings", "Client");
        add("Friends", "Local friend-list placeholder", "Client");
        add("Waypoints", "Local waypoint placeholder", "Client");
    }
}
