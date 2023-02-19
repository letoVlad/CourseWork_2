package Tasks;

public enum Type {
    WORK("Рабочая"), PERSONAL("Личная");
    private String translation;

    Type(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
