package services;

import entitites.Location;
import entitites.Plant;
import com.rits.cloning.Cloner;

import java.util.ArrayList;

public class GeneticAlg {

    private static ArrayList<ArrayList<Plant>> solutions;

    private static int count;

    public static void setCount(int count) {
        GeneticAlg.count = count;
    }

    //Evaluation
    public static int objectiveFunc(ArrayList<Plant> plants){
        int objValue = 0;
        for (int i = 0; i < count; i++){
            for (int j = i + 2; j <= count; j++){
                Plant firstPlant = plants.get(i);
                Plant secondPlant = plants.get(j-1);
                int flow = firstPlant.getFlow().get(j);
                Location firsrLoc = firstPlant.getAssignedLoc();
                Location secondLoc = secondPlant.getAssignedLoc();
                int distance = firsrLoc.getDistance().get(secondLoc.getId());
                int mult = flow * distance;
                objValue += mult;
            }
        }
        return objValue;
    }

    public static ArrayList<Plant> execute(ArrayList<ArrayList<Plant>> sol){
        solutions = sol;
        while (solutions.size() <= 2000) {
            //Unreachable case
            if (solutions.size() == 1) {
                ArrayList<Plant> plants = getClone(solutions.get(0));
                mutate(plants);
            }
            if (solutions.size() == 2) {
                ArrayList<Plant> firstParent = getClone(solutions.get(0));
                ArrayList<Plant> secondParent = getClone(solutions.get(1));
                reproduction(firstParent, secondParent);
            }
            selectParents();
        }
        int bestIndex = 0;
        int bestObjective = objectiveFunc(solutions.get(0));

        for (int i = 1; i < solutions.size(); i++){
            int newObjective = objectiveFunc(solutions.get(i));
            if (newObjective < bestObjective){
                bestObjective = newObjective;
                bestIndex = i;
            }
        }

        System.out.println("Best: " + bestObjective);

        return solutions.get(bestIndex);
    }

    //Random select three solutions and take two best parents from them
    public static void selectParents(){
        int size = solutions.size() - 1;
        int firstIndex = (int)(Math.random() * size);
        int secondIndex = (int)(Math.random() * size);
        while (firstIndex == secondIndex){
            secondIndex = (int)(Math.random() * size);
        }
        int thirdIndex = (int)(Math.random() * size);
        while (firstIndex == thirdIndex){
            thirdIndex = (int)(Math.random() * size);
        }
        ArrayList<Plant> firstPlants = getClone(solutions.get(firstIndex));
        ArrayList<Plant> secondPlants = getClone(solutions.get(secondIndex));
        ArrayList<Plant> thirdPlants = getClone(solutions.get(thirdIndex));

        int firstObjective = objectiveFunc(firstPlants);
        int secondObjective = objectiveFunc(secondPlants);
        int thirdObjective = objectiveFunc(thirdPlants);

        if (firstObjective >= secondObjective && firstObjective >= thirdObjective){
            reproduction(secondPlants, thirdPlants);
        } else if (secondObjective >= firstObjective && secondObjective >= thirdObjective){
            reproduction(firstPlants, thirdPlants);
        } else if (thirdObjective >= firstObjective && thirdObjective >= secondObjective){
            reproduction(firstPlants, secondPlants);
        }


    }

    //Two-point crossover
    public static void reproduction(ArrayList<Plant> firstParent, ArrayList<Plant> secondParent){
        int size = firstParent.size() - 1;
        int firstPointIndex = (int)(Math.random() * size);
        int secondPointIndex = (int)(Math.random() * size);
        while (firstPointIndex == secondPointIndex){
            firstPointIndex = (int)(Math.random() * size);
            secondPointIndex = (int)(Math.random() * size);
        }
        int small;
        int big;
        if (firstPointIndex > secondPointIndex){
            big = firstPointIndex;
            small = secondPointIndex;
        } else {
            big = secondPointIndex;
            small = firstPointIndex;
        }
        for (int i = small; i <= big; i++){
            Plant firstPlant = firstParent.get(i);
            Plant secondPlant = secondParent.get(i);

            Location firstLoc = firstPlant.getAssignedLoc();
            Location secondLoc = secondPlant.getAssignedLoc();

            for (Plant plant : firstParent){
                if (plant.getAssignedLoc().getId() == secondLoc.getId()){
                    plant.setAssignedLoc(firstLoc);
                }
            }
            for (Plant plant : secondParent){
                if (plant.getAssignedLoc().getId() == firstLoc.getId()){
                    plant.setAssignedLoc(secondLoc);
                }
            }

            firstPlant.setAssignedLoc(secondLoc);
            secondPlant.setAssignedLoc(firstLoc);
        }

        //solutions.add(firstParent);
       // solutions.add(secondParent);
        mutate(firstParent);
        mutate(secondParent);
    }

    //Swap mutation
    public static void mutate(ArrayList<Plant> plants){
        int size = plants.size() - 1;
        int firstPlantIndex = (int)(Math.random() * size);
        int secondPlantIndex = (int)(Math.random() * size);
        while (firstPlantIndex == secondPlantIndex){
            firstPlantIndex = (int)(Math.random() * size);
            secondPlantIndex = (int)(Math.random() * size);
        }
        Plant firstPlant = plants.get(firstPlantIndex);
        Plant secondPlant = plants.get(secondPlantIndex);

        Location firstLoc = firstPlant.getAssignedLoc();
        Location secondLoc = secondPlant.getAssignedLoc();

        firstPlant.setAssignedLoc(secondLoc);
        secondPlant.setAssignedLoc(firstLoc);
        solutions.add(plants);
    }


    private static <T> T getClone(T o) {
        Cloner cloner = new Cloner();
        return cloner.deepClone(o);
    }
}
