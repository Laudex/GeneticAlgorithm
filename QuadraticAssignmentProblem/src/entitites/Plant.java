package entitites;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Plant {

    private int id;
    private Location assignedLoc;

    Map<Integer, Integer> flow = new LinkedHashMap<>();

    public Plant() {
    }

    public Plant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getAssignedLoc() {
        return assignedLoc;
    }

    public void setAssignedLoc(Location assignedLoc) {
        this.assignedLoc = assignedLoc;
    }

    public Map<Integer, Integer> getFlow() {
        return flow;
    }

    public void setFlow(Map<Integer, Integer> flow) {
        this.flow = flow;
    }
}
