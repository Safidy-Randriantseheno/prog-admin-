package repository;

import model.Author;
import net.bytebuddy.agent.builder.AgentBuilder;
import repository.interfacegenerique.CrudOperations;

import java.util.List;

public class AuthorCrudOperation implements CrudOperations<Author> {
    private List<Author> authorList;
    @Override
    public List<Author> findAll(){
        return authorList;
    }

    @Override
    public List<Author> saveAll(List<Author> toSave) {
        authorList.addAll(toSave);
        return authorList;
    }

    @Override
    public Author save(Author toSave) {
        authorList.add(toSave);
        return toSave;
    }

    @Override
    public Author delete(Author toDelete) {
        authorList.remove(toDelete);
        return toDelete;
    }
}
