package pt.iceman.comsinstrumentcluster.dashboard;

public enum Gear {
    Neutral("/gear0.jpg"),
    First("/gear1.jpg"),
    Second("/gear2.jpg"),
    Third("/gear3.jpg"),
    Forth("/gear4.jpg"),
    Fifth("/gear5.jpg"),
    Reverse("/gearr.jpg");

    private String fieldValue;

    Gear(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}