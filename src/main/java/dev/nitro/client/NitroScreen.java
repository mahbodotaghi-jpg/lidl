package dev.nitro.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public final class NitroScreen extends Screen {
    private static final String[] CATEGORIES = {"Combat", "Misc", "Donut", "Basefinding", "Render", "Client"};
    private String selected = "Combat";
    private TextFieldWidget search;
    private int panelX, panelY, panelW = 720, panelH = 430;
    private static final int BG = 0xE9101422, PANEL = 0xD91A2032, EDGE = 0xFF5368D9;
    private static final int TEXT = 0xFFF2F5FF, MUTED = 0xFF9AA8C6, ACCENT = 0xFF4A9BFF;

    public NitroScreen() { super(Text.literal("Nitro Client")); }

    @Override
    protected void init() {
        panelX = (width - panelW) / 2;
        panelY = (height - panelH) / 2;
        search = new TextFieldWidget(textRenderer, panelX + 250, panelY + 64, 250, 22, Text.literal("Search modules"));
        search.setPlaceholder(Text.literal("Search modules..."));
        search.setMaxLength(64);
        addDrawableChild(search);
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        ctx.fill(0, 0, width, height, 0x66070B16);
        // Outer glass window, gradient-like edge and inner panels
        ctx.fill(panelX - 2, panelY - 2, panelX + panelW + 2, panelY + panelH + 2, EDGE);
        ctx.fill(panelX, panelY, panelX + panelW, panelY + panelH, BG);
        ctx.fill(panelX, panelY, panelX + 205, panelY + panelH, PANEL);
        ctx.fill(panelX + 520, panelY + 54, panelX + panelW - 14, panelY + panelH - 14, PANEL);
        ctx.drawText(textRenderer, "N", panelX + 18, panelY + 13, ACCENT, true);
        ctx.drawText(textRenderer, "NITRO CLIENT", panelX + 48, panelY + 15, TEXT, true);
        ctx.drawText(textRenderer, "FABRIC EDITION  •  1.21.4", panelX + 48, panelY + 31, MUTED, false);
        ctx.drawText(textRenderer, "×", panelX + panelW - 24, panelY + 14, TEXT, true);
        ctx.drawText(textRenderer, "MODULES", panelX + 18, panelY + 57, MUTED, false);

        for (int i = 0; i < CATEGORIES.length; i++) {
            int y = panelY + 83 + i * 42;
            boolean active = CATEGORIES[i].equals(selected);
            if (active) ctx.fill(panelX + 10, y - 5, panelX + 194, y + 27, 0xFF204B91);
            ctx.drawText(textRenderer, categoryIcon(CATEGORIES[i]) + "  " + CATEGORIES[i],
                panelX + 20, y + 4, active ? TEXT : MUTED, active);
        }
        ctx.drawText(textRenderer, "ONLINE", panelX + 20, panelY + panelH - 34, 0xFF63E6A2, false);

        ctx.drawText(textRenderer, selected, panelX + 224, panelY + 62, TEXT, true);
        ctx.drawText(textRenderer, "Client modules and configuration", panelX + 224, panelY + 79, MUTED, false);
        List<ClientModule> modules = NitroClient.MODULES.inCategory(selected).stream()
            .filter(m -> search == null || search.getText().isBlank()
                || m.name().toLowerCase().contains(search.getText().toLowerCase()))
            .toList();

        int y = panelY + 112;
        for (ClientModule module : modules) {
            if (y > panelY + panelH - 20) break;
            ctx.fill(panelX + 218, y, panelX + 505, y + 48, 0xC9232B40);
            ctx.drawText(textRenderer, module.name(), panelX + 230, y + 8, TEXT, false);
            ctx.drawText(textRenderer, trim(module.description(), 37), panelX + 230, y + 24, MUTED, false);
            int toggleColor = module.enabled() ? ACCENT : 0xFF46516B;
            ctx.fill(panelX + 457, y + 15, panelX + 489, y + 31, toggleColor);
            ctx.fill(module.enabled() ? panelX + 473 : panelX + 459, y + 17,
                module.enabled() ? panelX + 487 : panelX + 471, y + 29, 0xFFF4F7FF);
            y += 55;
        }
        if (modules.isEmpty()) ctx.drawText(textRenderer, "No modules found", panelX + 230, panelY + 125, MUTED, false);

        ctx.drawText(textRenderer, "SETTINGS", panelX + 540, panelY + 70, TEXT, true);
        ctx.drawText(textRenderer, "Selected category", panelX + 540, panelY + 101, MUTED, false);
        ctx.drawText(textRenderer, selected, panelX + 540, panelY + 117, ACCENT, true);
        ctx.drawText(textRenderer, "Right Shift", panelX + 540, panelY + 157, TEXT, false);
        ctx.drawText(textRenderer, "Open / close menu", panelX + 540, panelY + 173, MUTED, false);
        ctx.drawText(textRenderer, "NITRO  /  BE BETTER", panelX + 540, panelY + panelH - 39, ACCENT, true);
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (int i = 0; i < CATEGORIES.length; i++) {
                int y = panelY + 78 + i * 42;
                if (mouseX >= panelX + 10 && mouseX <= panelX + 194 && mouseY >= y && mouseY <= y + 34) {
                    selected = CATEGORIES[i];
                    return true;
                }
            }
            int y = panelY + 112;
            for (ClientModule module : NitroClient.MODULES.inCategory(selected)) {
                if (y > panelY + panelH - 20) break;
                if (mouseX >= panelX + 218 && mouseX <= panelX + 505 && mouseY >= y && mouseY <= y + 48) {
                    module.toggle();
                    return true;
                }
                y += 55;
            }
            if (mouseX >= panelX + panelW - 34 && mouseY >= panelY && mouseY <= panelY + 42) {
                close();
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private static String categoryIcon(String c) {
        return switch (c) {
            case "Combat" -> "⚔";
            case "Misc" -> "⚙";
            case "Donut" -> "◆";
            case "Basefinding" -> "⌖";
            case "Render" -> "◉";
            default -> "▦";
        };
    }
    private static String trim(String s, int max) { return s.length() <= max ? s : s.substring(0, max - 1) + "…"; }
    @Override public boolean shouldPause() { return false; }
}
