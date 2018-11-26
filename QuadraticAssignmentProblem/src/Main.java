import com.rits.cloning.Cloner;
import entitites.Location;
import entitites.Plant;
import services.FileService;
import services.GeneticAlg;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String instance = "Tai20a";
        String path = "E:\\vns\\GeneticAlgorithm\\in\\" + instance;
        String out = "E:\\vns\\GeneticAlgorithm\\out\\" + instance + ".sol";
        FileService.readDataFromFile(path);
        ArrayList<Location> locs = FileService.getLocations();
        ArrayList<Plant> plants = FileService.getPlants();
        int count = FileService.getCount();

        ArrayList<Plant> secondPlants = getClone(plants);
        ArrayList<Location> secondLocations = getClone(locs);

        //First initial solution

        for (int i = 0; i < locs.size(); i++){
            Location loc = locs.get(i);
            Plant plant = plants.get(i);
            loc.setAssignedPlant(plant);
            plant.setAssignedLoc(loc);
        }
        GeneticAlg.setCount(count);
        int objective = GeneticAlg.objectiveFunc(plants);

        //Second initial solution
        for (int i = 0; i < locs.size(); i++){
            Location loc = secondLocations.get(locs.size() - i - 1);
            Plant plant = secondPlants.get(i);
            loc.setAssignedPlant(plant);
            plant.setAssignedLoc(loc);
        }

        ArrayList<ArrayList<Plant>> solutions = new ArrayList<>();
        solutions.add(plants);
        solutions.add(secondPlants);

        ArrayList<Plant> bestPlants = GeneticAlg.execute(solutions);
        for (Plant plant : bestPlants){
            System.out.print(plant.getAssignedLoc().getId() + " ");
        }
        ArrayList<Plant> bestPlantsRightPerm = getRightPermutation(bestPlants);
        FileService.writeToFile(out, bestPlantsRightPerm);

    }

    public static ArrayList<Plant> getRightPermutation(ArrayList<Plant> bestPlants){
        int i = 1;
        ArrayList<Plant> newBestPlants = new ArrayList<>();
        while (i <= 20) {
            for (Plant plant : bestPlants) {
                if (plant.getAssignedLoc().getId() == i){
                    newBestPlants.add(plant);
                    i++;
                    break;
                }
            }
        }
        return newBestPlants;
    }

    private static <T> T getClone(T o) {
        Cloner cloner = new Cloner();
        return cloner.deepClone(o);
    }
}
