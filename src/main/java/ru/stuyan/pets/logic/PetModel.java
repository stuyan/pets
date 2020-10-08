package ru.stuyan.pets.logic;

import java.util.HashMap;
import java.util.Map;

public class PetModel {

    private static final PetModel INSTANCE = new PetModel();

    private final Map<Integer, Pet> model;

    public static PetModel getInstance() {
        return INSTANCE;
    }

    private PetModel() {
        model = new HashMap<>();
    }

    public void add(Pet pet, int id) {
        model.put(id, pet);
    }

    public Pet getFromList(int id) {
        return model.get(id);
    }

    public Map<Integer, Pet> getAll() {
        return model;
    }

}
