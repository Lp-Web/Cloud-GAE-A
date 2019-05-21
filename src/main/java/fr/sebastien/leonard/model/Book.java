package fr.sebastien.leonard.model;

import com.google.appengine.repackaged.com.google.api.client.util.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Book {

    @Id @Key
    private Long id;

    private long numberOfBook;

    private String author;
    private String title;

    public Book(String title, String author, Long numberOfBook) {
        setAuthor(author);
        setTitle(title);
        setNumberOfBook(numberOfBook);
    }

    public Book() {}

    private void setAuthor(String author) { this.author = author; }
    public String getAuthor() { return author; }

    private void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }

    public void setNumberOfBook(Long numberOfBook) { this.numberOfBook = numberOfBook; }
    public Long getNumberOfBooks(){ return numberOfBook; }

    public Long getId() { return id; }

}
