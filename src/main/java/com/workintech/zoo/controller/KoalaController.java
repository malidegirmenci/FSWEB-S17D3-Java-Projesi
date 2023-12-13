package com.workintech.zoo.controller;
import com.workintech.zoo.entity.Gender;
import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.validation.ZooValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/koalas")
public class KoalaController {
    Map<Integer, Koala> koalas;

    @PostConstruct
    public void init(){
        this.koalas = new HashMap<>();
        koalas.put(1,new Koala(1,"Koala Kum",65.6,3.5, Gender.MALE));
    }
    @GetMapping
    public List<Koala> getAll(){
        return koalas.values().stream().toList();
    }

    @GetMapping(path="/{id}")
    public Koala getById(@PathVariable("id") Integer id){
        ZooValidation.isIdValid(id);
        ZooValidation.checkKoalaExistence(koalas,id,true);
        return koalas.get(id);
    }
    @PostMapping
    public Koala create(@RequestBody Koala koala){
        ZooValidation.checkKoalaExistence(koalas, koala.getId(), false);
        ZooValidation.checkWeight(koala.getWeight());
        koalas.put(koala.getId(), koala);
        return koalas.get(koala.getId());
    }

    @DeleteMapping("/{id}")
    public Koala delete(@PathVariable("id") Integer id){
       ZooValidation.isIdValid(id);
       ZooValidation.checkKoalaExistence(koalas,id,true);
       return koalas.remove(id);
    }

    @PutMapping("/{id}")
    public Koala update(@PathVariable("id") Integer id, @RequestBody Koala koala){
        ZooValidation.isIdValid(id);
        ZooValidation.checkKoalaExistence(koalas,id,true);
        if(koalas.containsKey(id)){
            koalas.put(id,koala);
            return koalas.get(id);
        }else{
            return create(koala);
        }
    }
}
