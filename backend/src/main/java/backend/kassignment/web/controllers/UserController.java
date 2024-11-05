package backend.kassignment.web.controllers;

import backend.kassignment.domain.User;
import backend.kassignment.service.UserService;
import backend.kassignment.web.requests.ProductSearchFilter;
import backend.kassignment.web.resources.ProductResource;
import backend.kassignment.web.resources.UserResource;
import jakarta.jws.soap.SOAPBinding;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("app/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping
//    public ResponseEntity<Page<UserResource>> getAllUsers(@ModelAttribute ProductSearchFilter productSearchFilterList,
//                                                          @RequestParam(defaultValue = "0") int page,
//                                                          @RequestParam(defaultValue = "10") int size) {
//        Page<UserResource> userResourcePage = userService.getAllUsers(productSearchFilterList, page, size);
//        return ResponseEntity.ok(userResourcePage);
//    }


//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public UserResource createUser(@RequestBody UserRequest userRequest) {
//        return userService.createUser(userRequest);
//    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResource getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }

}
