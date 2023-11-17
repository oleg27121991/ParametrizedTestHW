package by.veremei.data;

public enum Language {
    RU("Русский"),
    BY("Беларуская"),
    MNG("Монгол");

    public final String description;

    Language(String description) {
        this.description = description;
    }
}
