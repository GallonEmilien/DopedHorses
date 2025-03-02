package fr.gallonemilien.persistence;

public interface HorseDataHandler {
    void writeHorseNbt(String uuidString, Double value);
    double readHorseNbt(String uuidString);
}