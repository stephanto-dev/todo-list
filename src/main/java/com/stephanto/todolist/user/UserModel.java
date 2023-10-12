// Um model é responsável por representar uma entidade do sistema. Nesse caso, o usuário. Um modelo de como o usuário deve ser.

package com.stephanto.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
// Lombok é uma biblioteca que permite a criação de getters e setters, construtores, equals, hashcode e toString de forma automática.
// @Data é uma anotação do Lombok que cria os getters e setters, construtores, equals, hashcode e toString de forma automática.
@Data
@Entity(name = "tb_users")
public class UserModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
