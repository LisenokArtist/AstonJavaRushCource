package com.example;

import java.util.Arrays;
import java.util.Scanner;

import com.example.Core.Entities.User.User;
import com.example.Core.Services.UserService;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final UserService userService = new UserService();

    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        
        while (true) { 
            System.out.print("> ");
            String inputLine = scanner.nextLine();
            String[] parts = inputLine.trim().split("\\s");

            String command = parts[0];
            String[] arguments = Arrays.copyOfRange(parts, 1, parts.length);

            switch (command) {
                case "help":
                    displayHelp();
                    break;
                case "listUsers":
                    System.out.println(listUsers(arguments));
                    break;
                case "findUser":
                    System.out.println(findUser(arguments).toString());
                    break;
                case "saveUser":
                    System.out.println(saveUser(arguments));
                    break;
                case "updateUserName":
                    System.out.println(updateUserName(arguments));
                    break;
                case "deleteUser":
                    System.out.println(deleteUser(arguments));
                    break;
                default:
                    System.out.println("Unknown command: " + command + ". Type 'help' for assistance.");
            }
        }
    }

    private static void displayHelp() {
        System.out.println("Available Commands:");
        System.out.println("  help - Display this help message.");
        System.out.println("  listUsers [limit?] - Display user records.");
        System.out.println("  findUser [id] - Find user by id.");
        System.out.println("  saveUser [name] [age] [email] - Save user.");
        System.out.println("  updateUserName [id] [name] - Updates user name.");
        System.out.println("  deleteUser [id] - Delete user by id.");
    }

    /**
     * Выводит последние записи в БД
     * @param args
     * @return
     */
    private static String listUsers(String[] args){
        String result = UNABLE_DO_OPERATION;

        if (args.length > 0){
            try {
                int limit = Integer.parseInt(args[0]);
                if (limit > 0) {
                    result = userService.users(limit).toString();
                }
            } catch (NumberFormatException e) {
                result = e.toString();
            }
        }
        else{
            result = userService.users().toString();
        }

        return result;
    }

    /**
     * Выполняет поиск пользователя по id
     * @param args
     * @return
     */
    private static String findUser(String[] args){
        String value = args[0];
        String result = UNCORRECT_ARGUMENTS_VALUES + ": " + value;

        if (!value.isEmpty()){
            try{
                int id = Integer.parseInt(value);
                User user = userService.findUserById(id);
                if (user != null)
                    result = user.toString();
                else
                    result = NOT_FOUND;
            } catch (NumberFormatException e){
                result = e.toString();
            }
        }

        return result;
    }

    /**
     * Сохраняет нового пользователя
     * @param args
     * @return
     */
    private static String saveUser(String[] args){
        if (args.length < 3)
            return MISSING_ARGUMENTS;
        
        String result = NOT_FOUND;
        try {
            String name = args[0];
            int age = Integer.parseInt(args[1]);
            String email = args[2];

            if (!name.isEmpty() && age > 0 && !email.isEmpty()){
                userService.saveUser(name, age, email);
                result = "User has been saved";
            } else{
                result = UNCORRECT_ARGUMENTS_VALUES;
            }
        } catch (NumberFormatException e) {
            result = e.toString();
        }

        return result;
    }

    /**
     * Обновляет имя пользователя
     * @param args
     * @return
     */
    private static String updateUserName(String[] args){
        if (args.length < 2)
            return MISSING_ARGUMENTS;
        
        String result = UNABLE_DO_OPERATION;
        try {
            int id = Integer.parseInt(args[0]);
            String name = args[1];

            if (!name.isEmpty()){
                userService.updateUser(id, name);
                result = "User has been updated";
            } else{
                result = UNCORRECT_ARGUMENTS_VALUES;
            }
        } catch (NumberFormatException e) {
            result = e.toString();
        }

        return result;
    }

    /**
     * Удаляет пользователя по id
     * @param args
     * @return
     */
    private static String deleteUser(String[] args){
        String result = MISSING_ARGUMENTS;

        if (args.length < 1)
            return result;
        
        try {
            int id = Integer.parseInt(args[0]);
            boolean passed = userService.deleteUserById(id);
            if (passed)
                result = "User has been deleted";
            else
                result = "User with id " + id + "not found"; 
        } catch (NumberFormatException e) {
            result = e.toString();
        }

        return result;
    }

    private static final String MISSING_ARGUMENTS = "Not enought or missing arguments";
    private static final String UNCORRECT_ARGUMENTS_VALUES = "Uncorrect arguments values";
    private static final String NOT_FOUND = "Searchable record not found";
    private static final String UNABLE_DO_OPERATION = "Unable do operation";
}
