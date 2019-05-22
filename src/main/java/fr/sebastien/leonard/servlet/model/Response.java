package fr.sebastien.leonard.servlet.model;

public class Response {

    private String value;

    public Response(String value) {
        setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
