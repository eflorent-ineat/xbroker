package upyfx.xbroker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.*;
import upyfx.xbroker.jsonView.View;
import upyfx.xbroker.model.User;
import upyfx.xbroker.repository.UserRepository;
import upyfx.xbroker.security.CurrentUser;
import upyfx.xbroker.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/v2/userinfo")
    @PreAuthorize("hasRole('USER')")
    @JsonView(View.Summary.class)
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) throws Exception {
        return userRepository.findById(String.valueOf(userPrincipal.getId()))
                .orElseThrow(() -> new Exception("User not found " + userPrincipal.getId()));

    }

    @PutMapping("/api/v2/userinfo")
    @PreAuthorize("hasRole('USER')")
    @JsonView(View.Summary.class)
    public User updateCurrentUser(@CurrentUser UserPrincipal userPrincipal,
                                  @RequestBody User pUser) throws Exception {
        User user = userRepository.findById(String.valueOf(userPrincipal.getId()))
                .orElseThrow(() -> new Exception("User not found" + userPrincipal.getId()));
        user.setFullName(pUser.getFullName());
        System.out.println(pUser.getAdvanced());
        user.setAdvanced(pUser.getAdvanced());
        user.setDarkTheme(pUser.getDarkTheme());
        userRepository.save(user);
        return user;
    }



}
