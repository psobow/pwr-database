package com.pwr.pwrdatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@Slf4j
public class PwrDatabaseApplicationTests
{

    private Set<String> set = new HashSet<>();

    public void test(@NotNull final String element)
    {
        this.set.add(element);
    }

    @Test
    public void someTest()
    {
        log.info("Hello Tests!");
        Set<String> exampleSet = new HashSet<>();
        exampleSet.add("Hello");
        exampleSet.add(null); // Dodało nulla do kolekcji
    }

    @Test
    public void executeTestWithNull()
    { // ta adnotacja @NotNull nic nie daje w tej funkcji...
        test(null);
        this.set.size();
    }

    @Test
    public void testForComparingStrings()
    {
        String first = "Jestem raperem";
        String second = "Jeżdże rowerem";
        String third = "Jeżdże rowerem";

        boolean result = first == second;
        boolean result2 = second == third;

        System.out.println(result);
        System.out.println(result2);
        System.out.println( "Jeżdże rowerem" == third );
        System.out.println( new String("Jeżdże rowerem") == third );
    }

    class Example
    {
        Integer x = 0;

        public void incrementX()
        {
            this.x++;
        }
    }

    @Test
    public void collectionTest()
    {
        List<Example> list = new ArrayList<>();
        Example example = new Example();

        list.add(example); // dodałem do wskaźnik do obiektu example do środka kolekcji list
        // w programie istnieje tylko jeden obiekt example. można się do niego odowłać na dwa sposoby.


        System.out.println("Initial value: " + example.x);
        System.out.println("Initial value: " +list.get(0).x);


        example.incrementX(); // ta linijka zmodyfikuje także obiekt wewnątrz kolekcji list

        System.out.println("Terminal value: " + example.x);
        System.out.println("Terminal value: " + list.get(0).x);
    }

}
