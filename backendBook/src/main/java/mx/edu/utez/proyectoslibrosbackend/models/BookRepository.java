package mx.edu.utez.proyectoslibrosbackend.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByNameBookContainingIgnoreCase(String keyword);
    List<Book> findByAuthor(String author);
    List<Book> findByPublicationDateBetween(Date startDate, Date endDate);
    List<Book> findByGener(String genre);
    List<Book> findByOrderByPublicationDateDesc();
}
