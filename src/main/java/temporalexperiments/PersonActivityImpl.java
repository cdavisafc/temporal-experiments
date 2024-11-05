package temporalexperiments;

public class PersonActivityImpl implements PersonActivity {
    // create a new person
    @Override
    public Person makePerson(String name, int age) {
        System.out.printf("\ncreating person: %s\n", name);
        System.out.flush();

        return new Person(name, age);

    }

}
