package com.example.springjwt.controller;

import com.example.springjwt.entity.Role;
import com.example.springjwt.service.RoleService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> saveRole(@Valid @RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/role/save").toUriString());

        return ResponseEntity.created(uri).body(roleService.saveRole(role));
    }

    @PostMapping("/roles/addtouser")
    public ResponseEntity<?> addRoleToUser(@Valid @RequestBody RoleToUserForm form) {

        roleService.addRoleToUser(form.getUserName(), form.getRoleName());

        return ResponseEntity.ok().build();
    }

}

@Data
class RoleToUserForm {
    @NotEmpty
    @Size(max = 64)
    private String userName;
    @NotEmpty
    @Size(max = 24)
    private String roleName;
}
