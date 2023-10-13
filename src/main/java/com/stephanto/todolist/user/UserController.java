// Um controller é responsável por receber as requisições HTTP e retornar uma resposta HTTP. Atua como um intermediário entre o usuário e o sistema.

package com.stephanto.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private IUserRepository userRepository;
    
	@PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){
		var user = this.userRepository.findByUsername(userModel.getUsername());
		if(user != null){
			return ResponseEntity.badRequest().body("Username already exists");
		}

		var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
		userModel.setPassword(passwordHashed);
		var userCreated = this.userRepository.save(userModel);
		return ResponseEntity.ok(userCreated);
    }
}