package com.example.greeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetingRespository extends JpaRepository<Greeting,String> {
  //created the method findByid to add int id ..by default method given by respository has camel case findBYId
    //if we prefix it with findBy with variable, it auto map with entity class
    Greeting findByid(int id);
    void deleteByid(int id);
}
