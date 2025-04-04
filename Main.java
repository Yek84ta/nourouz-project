import db.exception.EntityNotFoundException;
import example.Human;
import db.*;
public class Main {
    public static void main(String[] args) {

        Human ali = new Human("Ali");
        Database.add(ali);

        ali.name = "Ali Hosseini";

        Human aliFromTheDatabase = (Human) Database.get(ali.id);

        System.out.println("ali's name in the database: " + aliFromTheDatabase.name);
        System.out.println(ali.id);

    }
}