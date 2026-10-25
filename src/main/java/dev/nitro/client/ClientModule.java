package dev.nitro.client;

public final class ClientModule {
    private final String name;
    private final String description;
    private final String category;
    private boolean enabled;

    public ClientModule(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
    public String name() { return name; }
    public String description() { return description; }
    public String category() { return category; }
    public boolean enabled() { return enabled; }
    public void toggle() { enabled = !enabled; }
    public void setEnabled(boolean value) { enabled = value; }
}
