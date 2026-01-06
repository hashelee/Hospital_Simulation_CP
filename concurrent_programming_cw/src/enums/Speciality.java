package enums;

public enum Speciality {
    PAEDIATRICIAN, SURGEON, CARDIOLOGIST;

    public static Speciality getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
