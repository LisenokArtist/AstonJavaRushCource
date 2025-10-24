package com.example.controllers.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.datamodels.models.user.UserCreate;
import com.example.datamodels.models.user.UserShort;
import com.example.datamodels.models.user.UserUpdate;
import com.example.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

// http://localhost:8082/swagger-ui/index.html
// http://localhost:8082/v3/api-docs
@RestController
@RequestMapping("/api/users")
@Tag(
    name = "User Controller", 
    description = "Operations related to user manipulation")
public class UserRestController {
    @Autowired
    private final UserService service;

    
    public UserRestController(UserService service){
        this.service = service;
    }


    /**
     * Выполняет поиск пользователя
     * по его идентификатору
     * @param id Идентификатор пользователя
     * @return Пользователь, полученный с сервера
     */
    @GetMapping("/get/{id}")
    @Operation(
        summary = "Find user",
        description="Searches for a user by user ID"
    )
    public ResponseEntity<EntityModel<UserShort>> getUserById(
        @Parameter(description="Unique identifier of the user", example="123")
        @PathVariable(required=true) int id){
        UserShort result = service.findById(id);

        EntityModel<UserShort> model = EntityModel.of(result,
                                            WebMvcLinkBuilder.linkTo(
                                                WebMvcLinkBuilder
                                                .methodOn(UserRestController.class)
                                                .getUserById(id))
                                            .withSelfRel(),
                                            WebMvcLinkBuilder.linkTo(
                                                WebMvcLinkBuilder
                                                .methodOn(UserRestController.class)
                                                .getUsers())
                                            .withRel("get/all"));

        return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
    }
    
    /**
     * Выводит список пользователей
     * @return Список пользователей, полученные с сервера
     */
    @Operation(
        summary = "Find users",
        description="Searches for all users"
    )
    @GetMapping("/get/all")
    public CollectionModel<EntityModel<UserShort>> getUsers(){
        List<UserShort> result = service.findAll();

        List<EntityModel<UserShort>> model = result
                                            .stream()
                                            .map(e ->
                                                EntityModel.of(e,
                                                    WebMvcLinkBuilder.linkTo(
                                                        WebMvcLinkBuilder
                                                        .methodOn(UserRestController.class)
                                                        .getUsers()
                                                    ).withSelfRel()))
                                                .collect(Collectors.toList());

        return CollectionModel.of(model,
                    WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder
                        .methodOn(UserRestController.class)
                        .getUsers())
                    .withSelfRel());
    }

    /**
     * Создает нового пользователя
     * @param user Пользователь
     * @return Пользователь, полученный с сервера
     */
    @Operation(
        summary = "Crate user",
        description="Creates a new user",
        requestBody=@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Create user model", required = true)
    )
    @PostMapping("/create")
    public ResponseEntity<EntityModel<UserShort>> createUser(
        @RequestBody UserCreate userCreate){
        UserShort result = service.save(userCreate);

        EntityModel<UserShort> model = EntityModel.of(result,
                                        WebMvcLinkBuilder.linkTo(
                                            WebMvcLinkBuilder
                                            .methodOn(UserRestController.class)
                                            .createUser(userCreate))
                                        .withSelfRel());

        return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
    }

    /**
     * Обновляет пользователя
     * @param user Пользователь
     * @return Пользователь, полученный с сервера
     */
    @Operation(
        summary = "Update user",
        description="Updates fields of a user",
        requestBody=@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Update user model", required = true)
    )
    @PutMapping("/update")
    public ResponseEntity<EntityModel<UserShort>> updateUser(@RequestBody UserUpdate user){
        UserShort result = service.update(user.getId(), user.getName(), null, null);

        EntityModel<UserShort> model = EntityModel.of(result,
                                        WebMvcLinkBuilder.linkTo(
                                            WebMvcLinkBuilder
                                            .methodOn(UserRestController.class)
                                            .updateUser(user))
                                        .withSelfRel());

        return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
    }

    /**
     * Удаляет пользователя
     * @param id Идентификатор пользователя
     * @return Пользователь, полученный сервера
     */
    @Operation(
        summary = "Delete user",
        description="Deletes user by id"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<EntityModel<UserShort>> deleteUserById(
        @Parameter(description="Unique identifier of the user", example="123")
        @RequestBody int id){
        UserShort result = service.delete(id);

        EntityModel<UserShort> model = EntityModel.of(result,
                                WebMvcLinkBuilder.linkTo(
                                    WebMvcLinkBuilder
                                    .methodOn(UserRestController.class)
                                    .deleteUserById(id))
                                .withSelfRel());

        return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
    }

    /**
     * Выполняет подсчет пользователей
     * @return Число пользователей
     */
    @Operation(
        summary = "Count records",
        description="Performs a count of users stored in the DB"
    )
    @GetMapping("/count")
    public ResponseEntity<Long> count(){
        long result = service.count();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}