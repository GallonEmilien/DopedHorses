package fr.gallonemilien.config;

public class ClientModConfig {
    private int userUnit = 0;
    public void setUserUnit(int userUnit) {
        this.userUnit = (this.userUnit >= 0 && this.userUnit <= 2) ? this.userUnit : 0;
    }
    public int getUserUnit() {
        return this.userUnit;
    }
}
