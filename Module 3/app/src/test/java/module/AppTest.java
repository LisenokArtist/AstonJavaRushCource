package module;

import org.junit.jupiter.api.Test;

import com.google.gson.reflect.TypeToken;

import module.Models.BookModel;
import module.Models.PersonModel;
import module.Utils.JsonUtils;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;


class AppTest {
    private static final String FILE_NAME = "Data.json";
    
    @Test void writeToFile(){
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        String result = JsonUtils.writeDataToFile(FILE_NAME, createBooksArray());

        System.out.println("Данные записаны в файл: " + result);
    }

    @Test void readFromFile(){
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        List<BookModel> result = JsonUtils.readDataFromFile(FILE_NAME, new TypeToken<List<BookModel>>() {});

        System.out.println(
            String.join("\n", 
                result.stream()
                    .map(x -> x.toString())
                    .collect(Collectors.toList())));
    }

    /**
     * Создает массив данных с книгами
     * @return Массив типа BookModel[]
     */
    private static BookModel[] createBooksArray() {
        return new BookModel[]
        {
            new BookModel("Непобедимый", new PersonModel[]{ new PersonModel("Станислав", "Лем") },  224, 1964 ),
            new BookModel("Солярис", new PersonModel[]{ new PersonModel("Станислав", "Лем") },  288, 1961 ),
            new BookModel("Марсианин", new PersonModel[]{ new PersonModel("Энди", "Вейер") },  480, 2011 ),
            new BookModel("Пикник на обочине", new PersonModel[]
                { 
                    new PersonModel("Борис", "Стругацкий"),
                    new PersonModel("Аркадий", "Стругацкий"),
                }, 256, 1972 ),
            new BookModel("Игра Эндера", new PersonModel[]{ new PersonModel("Орсон", "Скотт", "Кард") },  416,1985 ),
            new BookModel("Звездный десант", new PersonModel[]{ new PersonModel("Роберт", "Хайнлайн") },  352,1959 ),
            new BookModel("Война миров", new PersonModel[]{ new PersonModel("Герберт", "Уеллс") },  288, 1898 ),
        };
    }
}
