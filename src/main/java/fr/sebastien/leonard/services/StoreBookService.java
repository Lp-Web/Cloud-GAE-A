package fr.sebastien.leonard.services;

import com.googlecode.objectify.Key;
import fr.sebastien.leonard.model.Book;
import static com.googlecode.objectify.ObjectifyService.ofy;
import java.util.List;

public class StoreBookService {

    public boolean store(Book book) {
        ofy().save().entity(book).now();
        return book.getId() != null;

    }

    public boolean removeUnitToBook(long id, Long unit) {
        if(ofy().load().type(Book.class).filterKey(Key.create(Book.class, id)).list().stream().findFirst().isPresent()) {
           Book book = ofy().load().type(Book.class).filterKey(Key.create(Book.class, id)).list().stream().findFirst().get();
           book.setNumberOfBook(book.getNumberOfBooks() - unit);
           ofy().save().entity(book).now();
           return true;
        }
        return false;
    }

    public boolean addUnitToBook(long id, Long unit) {
        if(ofy().load().type(Book.class).filterKey(Key.create(Book.class, id)).list().stream().findFirst().isPresent()) {
            Book book = ofy().load().type(Book.class).filterKey(Key.create(Book.class, id)).list().stream().findFirst().get();
            book.setNumberOfBook(book.getNumberOfBooks() + unit);
            ofy().save().entity(book).now();
            return true;
        }
        return false;
    }

    public List<Book> findAll() {
        return ofy().load().type(Book.class).list();
    }

}
