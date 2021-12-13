package com.example.greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3001")
@RestController
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class GreetingController {
//    ArrayList<Quote> quotes = new ArrayList<Quote>();
ArrayList<Greeting> greetings = new ArrayList<Greeting>();

//I have used @autowired here instead of constructor and new greetingRepository();
    @Autowired(required=false)
    GreetingRespository respository;
    // 1. path variable
    // add additional variable to endpoint ({name})
    // pass annotation to method (can be multiple... in which case just comma separate
    // add parenthesis after annotation if the url variable doesn't match the method argument
    @GetMapping("/greeting/{firstname}/{lastname}")
    public String hello(@PathVariable String firstname, @PathVariable String lastname) {
        return "hello"+firstname+lastname;
    }

//    @GetMapping("/greetings")
//    public ArrayList<Quote> greetings() {
//        //return names.stream().filter(name -> name.length() > 3).collect(Collectors.toList());
//        // {"hello","welcome"};	}
////		return new ArrayList<String>() {{
////			add("Geeks");
////
////			add("for");
////
////			add("Geeks");
////		}
////		};
//        return quotes;
//    }

    /** 2. request param
     no need to add a variable to the endpoint
     pass annotation(s) to method
     format for hitting this endpoint is
     http://localhost:8080/greeting?name=Sam
     for multiple... add another method argument and separate with '&'
     **/
    @GetMapping("/greeting")
    public String greetings(@RequestParam String name, @RequestParam String lastname) {
        return "hello" +name +lastname;
    };

//
//    @PostMapping("/greeting")
//    public String createGreeting(@RequestBody Quote quote){
//        quotes.add(quote);
//        return "added";
//    }

    @GetMapping("/greetings")
    public ResponseEntity<List<Greeting>> getGreetings() {
        // return ResponseEntity.ok().body(greetings);
        return ResponseEntity.status(HttpStatus.OK).body(respository.findAll());
    }

//    @PostMapping("/quote")
//    public ResponseEntity<String> createGreeting(@RequestBody Quote quote){
//        quotes.add(quote);
//        System.out.println(quote.toString());
//        return ResponseEntity.status(HttpStatus.CREATED).body("quote added"+quote.getQuote());
//    }

//    @PostMapping("/greeting")
//    public ResponseEntity<String> createGreeting(@RequestBody Quote quote) {
//        // we can add our own validation... then configure the HTTP response code, along with the body, for that specific error
//        if (quote.getQuote().length() < 2) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Greeting too short");
//        } else {
//            quotes.add(quote);
//            return ResponseEntity
//                    .status(HttpStatus.CREATED)
//                    .header("custom header", "sam")
//                    .body("Greeting added: " + quote.getQuote());
//        }
//    }

    //@after adding repository
    @PostMapping("/greeting")
    public ResponseEntity<String> createGreeting(@RequestBody Greeting greeting) {
        // we can add our own validation... then configure the HTTP response code, along with the body, for that specific error
        if (greeting.getGreeting().length() < 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Greeting too short");
        } else {
            // quotes.add(quote);
            respository.save(greeting);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("custom header", "sam")
                    .body("Greeting added: " + greeting.getGreeting());
        }
    }
    //get greeting by id
    @GetMapping("/greeting/{id}")
    public ResponseEntity<Greeting> getCustomGreeting(@PathVariable int id) {
  //     return respository.findByid(id);
//        return
//        greetings.stream()
//                .filter(greeting -> greeting.getId() == id)
//                .findFirst()
//                .orElse(null);

        return ResponseEntity.status(HttpStatus.OK).body(respository.findByid(id));
    }

    //delete greeting with id
    @DeleteMapping("/greetings/{id}")
    @Transactional
    public ResponseEntity<String> deleteGreeting(@PathVariable int id){

        respository.deleteByid(id);
//        for (Greeting greeting: greetings) {
//            if (greeting.getId()== id) {
//                greetings.remove(greeting);
//                return ResponseEntity.status(HttpStatus.OK).body(greeting.getGreeting() + " has been deleted");
//            }
//        }
       // return ResponseEntity.status(HttpStatus.OK).body("None to delete");
    return  ResponseEntity.status(HttpStatus.OK).body("Delete successfully");
    }

    //find random greeting
    @GetMapping("/greeting/random")
    public ResponseEntity<Greeting> getRandomGreeting(){
        List<Greeting> shuffledGreeting =respository.findAll();
                Collections.shuffle(shuffledGreeting);
      return ResponseEntity.status(HttpStatus.OK).body(shuffledGreeting.get(0));
    }
}
