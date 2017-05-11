package com.rest.controller;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rest.entity.Book;
import com.rest.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@GetMapping()
    public String getAllBooks(Model model){
		model.addAttribute("books", bookService.getAllBooks());
		return "books";
	}
	
	@PostMapping("/getBookByid")
	public String getBookById(HttpServletRequest request, Model model){
		int id = parseInt(request.getParameter("id"));
		List<Book> books = new ArrayList<>();
		Book book = bookService.getBookById(id);
		if(book != null)
			books.add(book);
		model.addAttribute("books", books);
		return "books";
	}
	
	@PostMapping("/getBookByauthor")
	public String getBooksByAuthor(HttpServletRequest request, Model model){
		String author = request.getParameter("author");
		model.addAttribute("books", bookService.getBooksByAuthor(author));
		return "books";
	}
	
	@PostMapping("/getBookByname")
	public String getBooksByName(HttpServletRequest request, Model model){
		String name = request.getParameter("name");
		model.addAttribute("books", bookService.getBooksByName(name));
		return "books";
	}
	
	@PostMapping("/deleteBook")
	public String deleteBookById(HttpServletRequest request, Model model){
		int id = parseInt(request.getParameter("id"));
		bookService.deleteBookById(id);
		model.addAttribute("books", bookService.getAllBooks());
		return "books";
	}
	
	@PostMapping("/addBook")
	public String addOrUpdateBook(HttpServletRequest request, Model model){
		int id = parseInt(request.getParameter("id"));
		Double price = parseDouble(request.getParameter("price"));
		String author = request.getParameter("author");
		String name = request.getParameter("name");
		Book book = new Book(id, author, name, price);
		bookService.addOrUpdateBook(book);
		model.addAttribute("books", bookService.getAllBooks());
		return "books";
	}
}