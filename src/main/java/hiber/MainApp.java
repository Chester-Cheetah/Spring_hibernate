package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }

      userService.add(new User("Евгений", "Дорджиев", "stifler_punk@outlook.com", new Car("Верблюд", 2837)));
      userService.add(new User("Семён", "Фомичём", "unknown", new Car("Ковер-самолёт", 34679)));
      userService.add(new User("Герман", "Севастьянов", "info@java-mentor.com", new Car("Невидимка", 76487)));
      userService.add(new User("Ильдар", "Яфаров", "unknown", new Car("Анаконда", 6754877)));
      userService.add(new User("Михаил", "Клянскис", "unknown", new Car("Метла", 12434677)));

      userService.listUsers().forEach(System.out ::println);

      System.out.println(userService.getUser(new Car("Анаконда", 6754877)));


      context.close();
   }
}
