// Um controller é responsável por receber as requisições HTTP e retornar uma resposta HTTP. Atua como um intermediário entre o usuário e o sistema.

package com.stephanto.todolist.user;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    
    public void create(@RequestBody UserModel userModel){

    }
}
