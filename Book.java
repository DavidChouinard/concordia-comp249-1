// -----------------------------------------------------
// Assignment 1
// Question: Part 1
// Written by: David Chouinard, 1695452
//
// This program enables a user to manage an inventory of books
//
// -----------------------------------------------------

public class Book {
	
	private String title;
	private String author;
	private long ISBN;
	private double price;
	private static int numberOfCreatedBooks = 0;

	public Book() {
		title = "";
		author = "";
		ISBN = 0;
		price = 0;
		// Increment count of number of books
		numberOfCreatedBooks++;
	}

	public Book(String title, String author, long ISBN, double price) {
		this.title = title;
		this.author = author;
		this.ISBN = ISBN;
		this.price = price;
		// Increment count of number of books
		numberOfCreatedBooks++;
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

	public long getISBN() {
		return ISBN;
	}

	public void setISBN(long ISBN) {
		this.ISBN = ISBN;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Author: " + author
				+ "\nTitle: " + title
				+ "\nISBN: " + ISBN
				+ "\nPrice: " + price;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (ISBN != other.ISBN)
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		return true;
	}
	
	public static int findNumberOfCreatedBooks() {
		return numberOfCreatedBooks;
	}

}
