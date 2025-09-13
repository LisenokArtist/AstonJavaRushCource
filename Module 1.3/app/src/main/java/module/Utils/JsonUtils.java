package module.Utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/** Класс для работы с json файлами */
public class JsonUtils {
    //#region WriteDataToFile
    /**
     * Записывает данные в файл и возвращает путь
     * к файлу типа "С:\Directory\FileName.json"
     * или пустую строку, если произошла ошибка
     * @param fileName Имя файла в локальной директории
     * @param data Данные для записи
     * @return Путь к файлу или пустую строку, если произошла ошибка
     */
    public static <T> String writeDataToFile(String fileName, T data){
        File file = new File(fileName);
        return writeDataToFile(file, data);
    }

    /**
     * Записывает данные в файл и возвращает путь
     * к файлу типа "С:\Directory\FileName.json"
     * или пустую строку, если произошла ошибка
     * @param file Объект типа File
     * @param data Данные для записи
     * @return Путь к файлу или пустую строку, если произошла ошибка
     */
    public static <T> String writeDataToFile(File file, T data){
        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(data, writer);
            return file.getAbsolutePath();
        } catch (Exception e){
            printCatchedException(e);
        }
        return "";
    }
    //#endregion

    //#region readDataFromFile
    /**
     * Считывает данные из файла и возвращает
     * результат типа T или null
     * @param <T> Тип данных
     * @param fileName Имя файла в локальной директории
     * @param typeToken Токен приведения к типу
     * @return Результат типа T или null
     */
    public static <T> T readDataFromFile(String fileName, TypeToken<T> typeToken){
        File file = new File(fileName);
        return readDataFromFile(file, typeToken);
    }

    /**
     * Считывает данные из файла и возвращает
     * результат типа T или null
     * @param <T> Тип данных
     * @param file Объект типа File
     * @param typeToken Токен приведения к типу
     * @return Результат типа T или null
     */
    public static <T> T readDataFromFile(File file, TypeToken<T> typeToken){
        try (FileReader reader = new FileReader(file)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            T result = gson.fromJson(reader, typeToken.getType());
            return result;
        } catch (Exception e){
            printCatchedException(e);
        }
        return null;
    }
    //#endregion

    /**
     * Метод определяет тип ошибки и отправляет
     * соответствующее сообщение в консоль
     * @param e Объект ошибки
     */
    private static void printCatchedException(Exception e){
        switch (e) {
            case SecurityException s:
                System.err.println("Возникла ошибка безопасности: " + s.getMessage());
                break;
            case IOException io:
                System.err.println("Возникла ошибка ввода/вывода: " + io.getMessage());
                break;
            default:
                System.err.println("Возникла неопределенная ошибка: " + e.getMessage());
                break;
        }
    }
}
