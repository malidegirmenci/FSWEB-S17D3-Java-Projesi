package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Gender;
import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.validation.ZooValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {
    Map<Integer, Kangaroo> kangaroos;
    @PostConstruct
    public void init(){
        this.kangaroos = new HashMap<>();
        this.kangaroos.put(1,new Kangaroo(1,"Kangaroo",175d,85d,Gender.FEMALE,true));
    }

    @GetMapping
    public List<Kangaroo> getAll(){
        return kangaroos.values().stream().toList();
    }

    @GetMapping(path="/{id}")
    public Kangaroo getById(@PathVariable("id") Integer id){
        ZooValidation.isIdValid(id);
        ZooValidation.checkKangarooExistence(kangaroos,id,true);
        return kangaroos.get(id);
    }
    @PostMapping
    public Kangaroo create(@RequestBody Kangaroo kangaroo){
        ZooValidation.checkKangarooExistence(kangaroos,kangaroo.getId(),false);
        ZooValidation.checkWeight(kangaroo.getWeight());
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroos.get(kangaroo.getId());
    }

    @DeleteMapping("/{id}")
    public Kangaroo delete(@PathVariable("id") Integer id){
        ZooValidation.isIdValid(id);
        ZooValidation.checkKangarooExistence(kangaroos,id,true);
        return kangaroos.remove(id);
    }

    @PutMapping("/{id}")
    public Kangaroo update(@PathVariable("id") Integer id, @RequestBody Kangaroo kangaroo){
        ZooValidation.isIdValid(id);
        ZooValidation.checkKangarooExistence(kangaroos,id,true);
        if(kangaroos.containsKey(id)){
            kangaroos.put(id,kangaroo);
            return kangaroos.get(id);
        }else{
            return create(kangaroo);
        }
    }
}
