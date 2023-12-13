package com.workintech.zoo.controller;
import com.workintech.zoo.entity.Gender;
import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.entity.Koala;
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
        koalas.put(1,new Koala(1,"Koala Kumpur",65.6,3.5, Gender.MALE));
    }
    @GetMapping
    public List<Koala> getAll(){
        return koalas.values().stream().toList();
    }

    @GetMapping(path="/{id}")
    public Koala getById(@PathVariable("id") Integer id){
        return koalas.get(id);
    }
    @PostMapping
    public Koala create(@RequestBody Koala koala){
        koalas.put(koala.getId(), koala);
        return koalas.get(koala.getId());
    }

    @DeleteMapping("/{id}")
    public Koala delete(@PathVariable("id") Integer id){
        Koala removedKoala = koalas.get(id);
        koalas.remove(id);
        return removedKoala;
    }

    @PutMapping("/{id}")
    public Koala update(@PathVariable("id") Integer id, @RequestBody Koala koala){
        koalas.put(id,koala);
        return koalas.get(id);
    }

}
