package mx.edu.utez.proyectoslibrosbackend.services;

import mx.edu.utez.proyectoslibrosbackend.models.Book;
import mx.edu.utez.proyectoslibrosbackend.models.BookRepository;
import mx.edu.utez.proyectoslibrosbackend.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
public class BookServices {
    @Autowired
    BookRepository repository;

    @Transactional(readOnly = true)
    public CustomResponse<List<Book>> getAll() {
        try {
            return new CustomResponse<>(this.repository.findAll(), false, 200, "OK");
        } catch (Exception e) {
            return new CustomResponse<>(null,true, 500, "Error de libros");
        }
    }

    @Transactional(readOnly = true)
    public CustomResponse<Book> getOne(Long id) {
        try {
            return new CustomResponse<>(this.repository.findById(id).get(), false, 200,"OK");
        } catch (Exception e) {
            return new CustomResponse<>(null, true, 500, "Error de servidor");
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Book> insert(Book libro) {
        System.out.println(libro);
        try {
            return new CustomResponse<>(this.repository.saveAndFlush(libro),false, 200, "Libro guardado correctamente");
        } catch (Exception e) {
            return new CustomResponse<>(null,true, 500, "Error de servidor");
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Book> update(Book libros) {
        try {
            if (this.repository.existsById(libros.getId()))
                return new CustomResponse<>(this.repository.saveAndFlush(libros),false, 200, "Actualizado correctamente");
            return new CustomResponse<>(null, true, 400,"Envio de datos erroneo");
        } catch (Exception e) {
            return new CustomResponse<>(null,true, 500, "Error de servidor");
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Book> delete(Long id) {
        try {
            if(!this.repository.existsById(id))
                return new CustomResponse<>(null,true, 400, "Envio de datos erroneo");
            this.repository.deleteById(id);
            return new CustomResponse<>(null, false, 200,"Eliminado correctamente");
        } catch (Exception e) {
            return new CustomResponse<>(null,true, 500, "Error de servidor");
        }
    }

    public List<Book> searchByName(String keyword) {
        return repository.findByNameBookContainingIgnoreCase(keyword);
    }

    public List<Book> searchByAuthor(String author) {
        return repository.findByAuthor(author);
    }

    public List<Book> searchByDateRange(Date startDate, Date endDate) {
        return repository.findByPublicationDateBetween(startDate, endDate);
    }

    public List<Book> searchByGenre(String genre) {
        return repository.findByGenre(genre);
    }

    public List<Book> searchByPublicationDateDescending() {
        return repository.findByOrderByPublicationDateDesc();
    }
}
