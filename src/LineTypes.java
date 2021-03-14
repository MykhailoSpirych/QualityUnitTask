public enum LineTypes {
    C("C"),
    D("D");

    private String type;

    LineTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
