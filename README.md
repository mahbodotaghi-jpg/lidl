# Nitro Client — fresh Fabric starter

A new Minecraft Java Edition 1.21.4 Fabric client-side mod project with a Purxiy-inspired dark glass GUI and six categories:
Combat, Misc, Donut, Basefinding, Render, Client.

## Current status
- Implemented: project structure, client entry point, Right Shift menu keybind, category navigation, module search, toggle UI, basic module registry.
- Placeholders only: gameplay/server-specific module behavior. The GUI toggles currently change local module state; they do not magically implement the listed game actions.
- Not build-tested in this environment. Fabric/Minecraft API and Gradle dependency versions may need adjustment when building.

## Build
1. Install JDK 21 and a current Gradle-compatible environment.
2. Open this folder in IntelliJ IDEA (recommended) or run `gradle wrapper` with Gradle installed.
3. Run `gradlew build` on Windows (or `./gradlew build` on Linux/macOS).
4. Find the resulting mod JAR in `build/libs/`.
5. Put the JAR in your Minecraft 1.21.4 Fabric `mods` folder together with Fabric API.

## In game
Press **Right Shift** to open the menu. Click categories in the sidebar and click module rows to toggle their local state.

Use only on worlds/servers where your client modifications are permitted.
