package mx.edu.utez.proyectoslibrosbackend.controllers;

import mx.edu.utez.proyectoslibrosbackend.services.BookServices;
import mx.edu.utez.proyectoslibrosbackend.models.Book;
import mx.edu.utez.proyectoslibrosbackend.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/libros/")
@CrossOrigin(origins = {"*"})
public class BookController {
    @Autowired
    BookServices bookServices;

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Book>>> getAll() {
        return new ResponseEntity<>(this.bookServices.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Book>> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.bookServices.getOne(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Book>> insert(@Validated @RequestBody Book book) {
        return  new ResponseEntity<>(this.bookServices.insert(book), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<CustomResponse<Book>> update(@Validated  @RequestBody Book book)  {
        return new ResponseEntity<>(this.bookServices.update(book),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Book>> delete(@PathVariable("id") Long id){
        return new ResponseEntity<>(this.bookServices.delete(id),HttpStatus.CREATED);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<CustomResponse<List<Book>>> searchByName(@RequestParam String keyword) {
        List<Book> result = bookServices.searchByName(keyword);
        return new ResponseEntity<>(new CustomResponse<>(result, false, 200, "OK"), HttpStatus.OK);
    }

    // Endpoint busquedas especificas
    @GetMapping("/searchByAuthor")
    public ResponseEntity<CustomResponse<List<Book>>> searchByAuthor(@RequestParam String author) {
        List<Book> result = bookServices.searchByAuthor(author);
        return new ResponseEntity<>(new CustomResponse<>(result, false, 200, "OK"), HttpStatus.OK);
    }
    @GetMapping("/searchByDateRange")
    public ResponseEntity<CustomResponse<List<Book>>> searchByDateRange(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<Book> result = bookServices.searchByDateRange(startDate, endDate);
        return new ResponseEntity<>(new CustomResponse<>(result, false, 200, "OK"), HttpStatus.OK);
    }
    @GetMapping("/searchByGenre")
    public ResponseEntity<CustomResponse<List<Book>>> searchByGenre(@RequestParam String genre) {
        List<Book> result = bookServices.searchByGenre(genre);
        return new ResponseEntity<>(new CustomResponse<>(result, false, 200, "OK"), HttpStatus.OK);
    }
    @GetMapping("/getByPublicationDateDescending")
    public ResponseEntity<CustomResponse<List<Book>>> getByPublicationDateDescending() {
        List<Book> result = bookServices.searchByPublicationDateDescending();
        return new ResponseEntity<>(new CustomResponse<>(result, false, 200, "OK"), HttpStatus.OK);
    }
}
