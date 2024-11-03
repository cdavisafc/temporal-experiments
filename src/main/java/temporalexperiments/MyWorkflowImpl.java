package temporalexperiments;

import java.util.ArrayList;

public class MyWorkflowImpl implements MyWorkflow {

    @Override
    public ArrayList<Person> getPeople() {
        ArrayList<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 30));
        people.add(new Person("Bob", 25));
        people.add(new Person("Charlie", 35));
        return people;
    }
}
