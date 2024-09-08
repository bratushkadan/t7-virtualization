package org.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElementController {

    @Autowired
    private ElementRepository elementRepository;

    @PostMapping("/addElement")
    public String addElement(@RequestParam String name, @RequestParam String description) {
        Element element = new Element(name, description);
        elementRepository.save(element);
        return "Element added successfully.";
    }

    @GetMapping("/elements")
    public List<Element> getAllElements() {
        return elementRepository.findAll();
    }
}