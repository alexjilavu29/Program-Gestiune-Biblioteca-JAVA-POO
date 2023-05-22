package Classes;

public class Person {
    protected int id;
    protected String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Person() {
        this.id = -1;
        this.name = "N/A";
    }

    @Override
    public String toString() {
        return "Classes.Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
