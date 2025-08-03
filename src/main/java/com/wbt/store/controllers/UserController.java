package com.wbt.store.controllers;

import com.wbt.store.dtos.ChangePasswordRequest;
import com.wbt.store.dtos.UserDto;
import com.wbt.store.dtos.UserRequestDto;
import com.wbt.store.dtos.UserUpdateRequest;
import com.wbt.store.entities.User;
import com.wbt.store.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/v1/users"})
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return this.userRepository.findAll().stream().map(UserController::toUserDto).toList();
    }

    @PostMapping
    public ResponseEntity<UserDto> create(final @RequestBody UserRequestDto request, final UriComponentsBuilder uriBuilder) {
        final var user = this.userRepository.save(buildUserEntity(request));
        final var uri = uriBuilder.path("/api/v1/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(toUserDto(user));
    }

    @PutMapping(path = {"/{id}"})
    public ResponseEntity<UserDto> update(final @PathVariable(name = "id") Long id, final @RequestBody UserUpdateRequest request) {
        return this.userRepository.findById(id)
                .map(user -> {
                    user.setName(request.name());
                    user.setEmail(request.email()); // TODO: must check for unique email
                    return ResponseEntity.ok(UserController.toUserDto(this.userRepository.save(user)));
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Void> delete(final @PathVariable(name = "id") Long id) {
        return this.userRepository.findById(id)
                .map(user -> {
                    this.userRepository.delete(user);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = {"/{id}/change-password"})
    public ResponseEntity<Void> changePassword(final @PathVariable(name = "id") Long id, final @RequestBody ChangePasswordRequest request) {
        final var optionalUser = this.userRepository.findById(id);

        if (optionalUser.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        final var user = optionalUser.get();

        if (!user.getPassword().equals(request.oldPassword())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        user.setPassword(request.newPassword());
        this.userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    private static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    private static User buildUserEntity(final UserRequestDto request) {
        return User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .build();
    }
}
