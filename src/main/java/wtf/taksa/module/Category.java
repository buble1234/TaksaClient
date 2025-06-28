package wtf.taksa.module;
/**
 * @author Kenny1337
 * @since 28.06.2025
 */
public enum Category {
    FIGHT("Fight"),
    MOVEMENT("Movement"),
    PLAYER("Player"),
    WORLD("World"),
    VISUALS("Visuals"),
    MISCELLANEOUS("Miscellaneous"),
    CLIENT("Client");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
} 