package com.konan.controller;

import com.konan.pojo.Books;
import com.konan.service.BookService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookControl {

    private final BookService bookService;

    public BookControl(@Qualifier("bookServiceImpl") BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/allBook")
    public String allBook(Model model) {
        List<Books> books = bookService.queryAllBook();
        model.addAttribute("list",books);
        return "allBook";
    }

    @RequestMapping("/toAddBook")
    public String toAddBook(Model model) {
        return "addBook";
    }

    @RequestMapping("/addBook")
    public String addBook(Books books) {
        System.out.println(books);
        bookService.addBook(books);
        return "redirect:/book/allBook";
    }

    @RequestMapping("/toUpdateBook")
    public String toUpdateBook(Model model, int id) {
        Books books = bookService.queryBookById(id);
        System.out.println(books);
        model.addAttribute("book",books );
        return "updateBook";
    }

    @RequestMapping("/updateBook")
    public String updateBook(Model model, Books book) {
        System.out.println(book);
        bookService.updateBook(book);
        Books books = bookService.queryBookById(book.getBookID());
        model.addAttribute("books", books);
        return "redirect:/book/allBook";
    }

    @RequestMapping("/del/{bookID}")
    public String del(@PathVariable int bookID) {
        int i = bookService.deleteBookById(bookID);
        String msg=null;
        msg=i>0?"删除成功":"删除失败";
        System.out.println(msg);
        return "redirect:/book/allBook";
    }
}
