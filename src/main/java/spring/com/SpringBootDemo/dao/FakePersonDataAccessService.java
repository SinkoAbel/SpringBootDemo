package spring.com.SpringBootDemo.dao;

import org.springframework.stereotype.Repository;
import spring.com.SpringBootDemo.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();
    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                 .filter(person -> person.getId().equals(id))
                 .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
       Optional<Person> personToDelete = selectPersonById(id);
       if(personToDelete.isEmpty())
           return 0;
       DB.remove(personToDelete.get());
       return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person updatePerson) {
        return selectPersonById(id)
                .map(person -> {
                    int indexOfPersonToUpdate = DB.indexOf(person);
                    if (indexOfPersonToUpdate >= 0) {
                        DB.set(indexOfPersonToUpdate, new Person(id, updatePerson.getName()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }


}
