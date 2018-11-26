package entitites;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Location {

    private int id;
    private Plant assignedPlant;

    Map<Integer, Integer> distance = new LinkedHashMap<>();

    public Location() {
    }

    public Location(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plant getAssignedPlant() {
        return assignedPlant;
    }

    public void setAssignedPlant(Plant assignedPlant) {
        this.assignedPlant = assignedPlant;
    }

    public Map<Integer, Integer> getDistance() {
        return distance;
    }

    public void setDistance(Map<Integer, Integer> distance) {
        this.distance = distance;
    }
}
