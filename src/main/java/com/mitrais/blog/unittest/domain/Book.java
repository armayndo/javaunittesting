package com.mitrais.blog.unittest.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;

    @Column(name = "isbn", length = 25, nullable = false)
    private String isbn;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "author", length = 30, nullable = false)
    private String author;

    @Column(name = "status_shelved", columnDefinition = "boolean NOT NULL DEFAULT false")
    private boolean isShelved;
    
    @ManyToOne
    @JoinColumn(name = "shelf_id", nullable = true)
    private Shelf shelf;
    
    public Book() {}

	public Book(Integer id, String isbn, String title, String author, boolean isShelved, Shelf shelf) {
		this.id = id;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.isShelved = isShelved;
		this.shelf = shelf;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isShelved() {
		return isShelved;
	}

	public void setShelved(boolean isShelved) {
		this.isShelved = isShelved;
	}

	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}
}
