package guru.springframework.spring6webapp.boostrap;

import guru.springframework.spring6webapp.domain.Author;
import guru.springframework.spring6webapp.domain.Book;
import guru.springframework.spring6webapp.domain.Publisher;
import guru.springframework.spring6webapp.repositories.AuthorRepository;
import guru.springframework.spring6webapp.repositories.BookRepository;
import guru.springframework.spring6webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    CommandLineRunner runner = (s) -> System.out.println(s);
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author eric = new Author();
        eric.setFirstName("Eric");
        eric.setLastName("Evans");

        Book ddd = new Book();
        ddd.setTitle("Domain Driven Design");
        ddd.setIsbn("123456");

        Author ericSaved = authorRepository.save(eric);
        Book dddSaved = bookRepository.save(ddd);

        Author rod = new Author();
        rod.setFirstName("Rod");
        rod.setLastName("Johnson");

        Book noEJB = new Book();
        noEJB.setTitle("J2EE Development without EJB");
        noEJB.setIsbn("987654321");

        Author rodSaved = authorRepository.save(rod);
        Book noEJBSaved = bookRepository.save(noEJB);

        ericSaved.getBooks().add(dddSaved);
        dddSaved.getAuthors().add(ericSaved);
        rodSaved.getBooks().add(noEJBSaved);
        noEJBSaved.getAuthors().add(rodSaved);


        //Persist the Author | Book association in H2 DB
        Author ericSavedWithBookRelation = authorRepository.save(ericSaved);
        Author rodSavedWithBookRelation = authorRepository.save(rodSaved);

        Publisher publisher = new Publisher();
        publisher.setPublisherName("ENI");
        publisher.setAddress("7 Hamant Street");
        publisher.setCity("Calgary");
        publisher.setZip("T2P");
        Publisher savedPublisher = publisherRepository.save(publisher);

        dddSaved.setPublisher(savedPublisher);
        noEJBSaved.setPublisher(savedPublisher);



        //Persist the Book | Publisher association in H2 DB
        bookRepository.save(dddSaved);
        bookRepository.save(noEJBSaved);

        System.out.println("In Boostrap : ");
        System.out.println("Author count : "+authorRepository.count());
        System.out.println("Book count : "+bookRepository.count());
        System.out.println("Publisher count : "+publisherRepository.count());
    }
}
