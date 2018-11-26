package services;

import entitites.Location;
import entitites.Plant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class FileService {

    private static ArrayList<Location> locations = new ArrayList<>();
    private static ArrayList<Plant> plants = new ArrayList<>();
    private static int count;

    public static ArrayList<Location> getLocations() {
        return locations;
    }

    public static ArrayList<Plant> getPlants() {
        return plants;
    }

    public static int getCount() {
        return count;
    }

    public static void readDataFromFile(String path) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));

            String nextStr = in.readLine().trim();

            count = Integer.parseInt(nextStr);

            //Read Locations
            for (int i = 0; i < count; i++) {
                String str = in.readLine().trim();
                String[] rawSplitted = str.split(" ");
                ArrayList<Integer> splitted = new ArrayList<>();
                for (int s = 0; s < rawSplitted.length; s++) {
                    try {
                        splitted.add(Integer.parseInt(rawSplitted[s]));
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
                Location newLoc = new Location(i + 1);
                Map<Integer, Integer> distance = new LinkedHashMap<>();
                for (int j = 0; j < count; j++) {
                    int dist = splitted.get(j);
                    distance.put(j + 1, dist);
                }
                newLoc.setDistance(distance);
                locations.add(newLoc);


            }
            //Read Plants
            String empty = in.readLine();

            for (int i = 0; i < count; i++) {
                String str = in.readLine().trim();
                String[] rawSplitted = str.split(" ");
                ArrayList<Integer> splitted = new ArrayList<>();
                for (int s = 0; s < rawSplitted.length; s++) {
                    try {
                        splitted.add(Integer.parseInt(rawSplitted[s]));
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }

                Plant newPlant = new Plant(i + 1);
                Map<Integer, Integer> flows = new LinkedHashMap<>();
                for (int j = 0; j < count; j++) {
                    int flow = splitted.get(j);
                    flows.put(j + 1, flow);
                }
                newPlant.setFlow(flows);
                plants.add(newPlant);


            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // return null;
        }
    }

    public static void writeToFile(String path, ArrayList<Plant> plants){
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));
            StringBuilder result = new StringBuilder();
            for (Plant plant : plants){
                result.append(plant.getId()).append(" ");
            }
            writer.write(result.toString());
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
