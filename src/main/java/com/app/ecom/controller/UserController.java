package com.app.ecom.controller;


//import com.app.ecom.User;
import com.app.ecom.User;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.apache.catalina.User;


import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;



   //private List<User> userList = new ArrayList<>();

   @GetMapping
  //  @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponse>>  getAllUsers() {
       return new ResponseEntity<>(userService.fetchAllusers(), HttpStatus.OK);
        //return ResponseEntity.ok(userService.fetchAllusers());
        //return userService.fetchAllusers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUsers(@PathVariable Long id) {

        return userService.fetchUser(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String>  createUsers(@RequestBody UserRequest userRequest) {
      userService.adduser(userRequest);
      return ResponseEntity.ok("User created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String>  updateUser(@RequestBody UserRequest updartedUserRequest, @PathVariable Long id) {
        boolean updated = userService.updateUser(id, updartedUserRequest);
        if (updated) {
            return ResponseEntity.ok("User updated");
        }
        return ResponseEntity.notFound().build();
    }

}
