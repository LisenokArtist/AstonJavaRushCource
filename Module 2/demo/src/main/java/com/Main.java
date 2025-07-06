package com;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.Core.Collections.GenericArrayList;
import com.Core.Models.BookModel;
import com.Core.Models.PersonModel;
import com.Core.Models.StudentModel;

public class Main {
    record KeyValue(int key, BookModel value) {}
    public static void main(String[] args) {
        System.out.println();

        List<BookModel> bookCollection = new ArrayList<>(Arrays.asList(CreateBookCollection()));
        var studentCollection = new GenericArrayList<StudentModel>(CreateStudents());
        
        

        studentCollection.stream()
        .map(x -> {
            //Вывести в консоль каждого студента (переопределите toString)
            System.out.println(x.toString());

            //Получить для каждого студента список книг 
            //  C учетом пункта 2 ограничиваю до 5 книг на каждого
            Collections.shuffle(bookCollection);
            x.Books = bookCollection.stream().limit(5).collect(Collectors.toList());
            return x;
        })
        //Получить книги
        .map(x -> x.Books)
        .flatMap(List::stream)
        //Отсортировать книги по количеству страниц (Не забывайте про условия для сравнения объектов)
        .sorted(Comparator.comparing(BookModel::getPages))
        //Оставить только уникальные книги
        .distinct()
        //Отфильтровать книги, оставив только те, которые были выпущены после 2000 года
        .filter(x -> x.Year >= 2000)
        //Ограничить стрим на 3 элементах
        .limit(3)
        //Получить из книг годы выпуска
        .map(x -> {
            return new AbstractMap.SimpleEntry<Integer, BookModel>(x.Year, x);
        })
        //При помощи методов короткого замыкания (почитайте самостоятельно что это такое) вернуть Optional от книги
        .map(x -> x.getKey() > 2010 && x.getKey() < 2012 ? Optional.ofNullable(x.getValue()) : Optional.empty())
        //При помощи методов получения значения из Optional вывести в консоль год выпуска найденной книги, либо запись о том, что такая книга отсутствует
        .findAny()
        .ifPresent(
            x -> {
                if (x.isPresent()) {
                    BookModel keyValue = (BookModel)x.get();
                    System.out.println("Книга найдена: " + keyValue.Name + " " + keyValue.Year);
                } else {
                    System.out.println("Книжок немае :(");
                };
            });

        return;
    }

    private static StudentModel[] CreateStudents(){
        return new StudentModel[]
        {
            new StudentModel("Айрат", "Арифуллин"),
            new StudentModel("Вадим", "Шум"),
            new StudentModel("Алина", "Виноградова"),
            new StudentModel("Марина", "Радова"),
            new StudentModel("Людмила", "Беррб"),
            new StudentModel("Никита", "Мусатов"),
        };
    }

    private static BookModel[] CreateBookCollection(){
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