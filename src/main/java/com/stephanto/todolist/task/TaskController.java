package com.stephanto.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stephanto.todolist.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var userId = request.getAttribute("userId");
        taskModel.setUserId((UUID) userId);

        var currentDate = LocalDateTime.now();
        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())){
            return ResponseEntity.badRequest().body("A data de início / data de término não pode ser menor que a data atual");
        }

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.badRequest().body("A data de início não pode ser maior que a data de término");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.ok().body(task);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var userId = request.getAttribute("userId");
        var tasks = this.taskRepository.findByUserId((UUID) userId);
        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){

        var task = this.taskRepository.findById(id).orElse(null);

        if(task == null){
            return ResponseEntity.badRequest().body("Tarefa não encontrada");
        }

        var userId = request.getAttribute("userId");
        if(!task.getUserId().equals(userId)){
            return ResponseEntity.badRequest().body("Você não tem permissão para alterar essa tarefa");
        }

        Utils.copyNonNullProperties(taskModel, task);

        return ResponseEntity.ok().body(this.taskRepository.save(task));

    }
}
