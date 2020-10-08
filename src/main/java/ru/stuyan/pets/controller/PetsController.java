package ru.stuyan.pets.controller;

import org.springframework.web.bind.annotation.*;
import ru.stuyan.pets.logic.PetModel;
import ru.stuyan.pets.logic.Pet;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/pets")
public class PetsController {

    private final PetModel model = PetModel.getInstance();
    private final AtomicInteger newId = new AtomicInteger(1);

    @PostMapping(value = "/createPet", consumes = "application/json")
    public String createPet(@RequestBody Pet pet) {
        String result = "Поздравляем, вы создали своего " +
                (model.getAll().isEmpty() ? "первого " : "") +
                "домашнего питомца!";
        int id = newId.getAndIncrement();
        pet.setId(id);
        model.add(pet, id);
        return result;
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll() {
        return model.getAll();
    }

    @GetMapping(value = "/getPet",
            consumes = "application/json",
            produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id) {
        return model.getFromList(id.get("id"));
    }

    @DeleteMapping(value = "/deletePet", produces = "application/json")
    public String deletePet(@RequestBody Map<String, Integer> map) {
        Integer id = map.get("id");
        if (model.getAll().containsKey(id)) {
            model.getAll().remove(id);
            return "Вы удалили " + (model.getAll().isEmpty() ? "всех своих питомцев " : "своего питомца");
        }
        return "Питомец с id " + id + " не найден";
    }

    @PutMapping(value = "/updatePet", produces = "application/json")
    public String updatePet(@RequestBody Pet pet) {
        Integer id = pet.getId();
        if (model.getAll().containsKey(id)) {
            model.add(pet, pet.getId());
            return "Питомец успешно обновлён";
        }
        return "Питомец с id " + id + " не найден";
    }

}
