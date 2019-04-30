package com.mitrais.blog.unittest.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "shelf")
public class Shelf {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shelf_id")
    private Integer id;

    @Column(name = "max_capacity")
    private Long maxCapacity;

    @Column(name = "current_capacity")
    private Long currentCapacity;

//    @OneToMany(mappedBy = "shelf")
//    private List<Book> books = new ArrayList<>();

	public Shelf() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Shelf(Integer id, Long maxCapacity, Long currentCapacity) {
	this.id = id;
	this.maxCapacity = maxCapacity;
	this.currentCapacity = currentCapacity;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(Long maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public Long getCurrentCapacity() {
		return currentCapacity;
	}

	public void setCurrentCapacity(Long currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

//	public List<Book> getBooks() {
//		return books;
//	}
//
//	public void setBooks(List<Book> books) {
//		this.books = books;
//	}
}
