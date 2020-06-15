package lab9.lab;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import lab9.lab.ui.Console;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "lab9.lab"
                );

        Console console = context.getBean(Console.class);
        console.runConsole();
    }
}