package com.answerdigital.colourstest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;


import com.answerdigital.colourstest.model.Person;
import com.answerdigital.colourstest.dto.PersonUpdateDTO;
import com.answerdigital.colourstest.repository.PeopleRepository;
import com.answerdigital.colourstest.exception.NotImplementedException;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleRepository peopleRepository;
// ___________________________________________________________________________________________________________________________________________

// TODO STEP 1
//
// Implement a JSON endpoint that returns the full list
// of people from the PeopleRepository. If there are zero
// people returned from PeopleRepository then an empty
// JSON array should be returned.

    @GetMapping
    public ResponseEntity<List<Person>> getPeople() {
      List<Person> people = peopleRepository.findAll();
      if (people == null) {
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
      } else {
        return new ResponseEntity<List<Person>>(people, HttpStatus.OK);
      }
    }
// ___________________________________________________________________________________________________________________________________________

// TODO: Step 2
//
// Implement a JSON endpoint that returns a single person
// from the PeopleRepository based on the id parameter.
// If null is returned from the PeopleRepository with
// the supplied id then a NotFound should be returned.

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {

      Optional<Person> person = peopleRepository.findById(id);

      if (person == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } else {
        return new ResponseEntity<Person>(person.get(), HttpStatus.OK);
      }
    }

// ___________________________________________________________________________________________________________________________________________

// TODO STEP 3
//
// Implement an endpoint that recieves a JSON object to
// update a person using the PeopleRepository based on
// the id parameter. Once the person has been sucessfullly
// updated, the person should be returned from the endpoint.
// If null is returned from the PeopleRepository then a
// NotFound should be returned.

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @RequestBody PersonUpdateDTO personUpdate) {

      Optional<Person> person = peopleRepository.findById(id);

      if (person == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } else {
        Person editPerson = person.get();
        editPerson.setColours(personUpdate.getColours());
        editPerson.setAuthorised(personUpdate.isAuthorised());
        editPerson.setEnabled(personUpdate.isEnabled());
        Person updatedPerson = peopleRepository.save(editPerson);
        return new ResponseEntity<Person>(updatedPerson, HttpStatus.OK);
      }
    }
  }
