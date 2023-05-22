package Classes;

public class Author extends Person{
    private int birthYear;
    private int numberOfPublications;
    private int titlesInStore;

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getNumberOfPublications() {
        return numberOfPublications;
    }

    public void setNumberOfPublications(int numberOfPublications) {
        this.numberOfPublications = numberOfPublications;
    }

    public int getTitlesInStore() {
        return titlesInStore;
    }

    public void setTitlesInStore(int titlesInStore) {
        this.titlesInStore = titlesInStore;
    }

    public Author(int id, String name, int birthYear, int numberOfPublications, int titlesInStore) {
        super(id, name);
        this.birthYear = birthYear;
        this.numberOfPublications = numberOfPublications;
        this.titlesInStore = titlesInStore;
    }

    public Author() {
        super();
        this.birthYear = 0;
        this.numberOfPublications = 0;
        this.titlesInStore = 0;
    }

    @Override
    public String toString() {
        return "Classes.Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", numberOfPublications=" + numberOfPublications +
                ", titlesInStore=" + titlesInStore +
                '}';
    }
}
