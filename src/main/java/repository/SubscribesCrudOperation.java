package repository;

import model.User;
import repository.interfacegenerique.CrudOperations;

import java.util.List;

public class SubscribesCrudOperation implements CrudOperations<User> {
    private List<User> userList;
    @Override
    public List<User> findAll() {
        return userList;
    }

    @Override
    public List<User> saveAll(List<User> toSave) {
        userList.addAll(toSave);
        return userList;
    }

    @Override
    public User save(User toSave) {
        userList.add(toSave);
        return toSave;
    }

    @Override
    public User delete(User toDelete) {
        userList.remove(toDelete);
        return toDelete;
    }
}
