import java.util.Scanner;

public class BookInterface {
	// Change to desired password
	private static final String password = "password";
	private static int maxBooks;
	private static Book[] inventory;
	
	public static Scanner keyboard=new Scanner(System.in);
	
	public static void main(String[] args) {
		String prompt = "Welcome to your bookstore manager\n"
					+ "Enter the maximum number of books you want your store to contain > ";
		System.out.print(prompt);
		
		maxBooks=keyboard.nextInt();
		keyboard.nextLine(); //to clear the trailing line break
		
		inventory = new Book[maxBooks];
			
		// Keep prompting until the program quits
		while(true) {
			prompt = "\nWhat do you want to do? \n"
					+ "	1. Enter new books (password required)\n"
					+ "	2. Change information of a book (password required)\n"
					+ "	3. Display all books by a specific author\n"
					+ "	4. Display all books under a certain a price.\n"
					+ "	5. Quit\n"
					+ "Please enter your choice > ";
			System.out.print(prompt);
			
			int choice = keyboard.nextInt();
			keyboard.nextLine();
	
			switch (choice) {
				case 1:
					// Authenticate the user
					if(promptPassword()) {
						System.out.print("\nHow many books do you want to enter? ");
						
						int nbBooks = keyboard.nextInt();
						keyboard.nextLine();
						
						// Make sure the user isn't asking us to create more books than we're allowed
						int nbBooksLeft = maxBooks - Book.findNumberOfCreatedBooks();
						if (nbBooksLeft < nbBooks) {
							System.out.println("You've reached the limit, you'll be only able to add " + nbBooksLeft + " book(s).");
							// Let the user add the maximum number of books
							nbBooks = nbBooksLeft;
						}
						
						for (int i=1; i <= nbBooks; i++) {
							System.out.print("Title: ");
							String title = keyboard.nextLine();
						
							System.out.print("Author: ");
							String author = keyboard.nextLine();
							
							long ISBN = promptForISBN();
						
							double price = promptForPrice();	
						
							Book new_book = new Book(title, author, ISBN, price);
						
							inventory[Book.findNumberOfCreatedBooks() - 1] = new_book;
						
							System.out.println("\"" + title + "\" added.");
						}
					}
					break;
				case 2:
					if(promptPassword()) {
						// We move this to a method to able to recursively call the prompt on itself
						promptUpdateBook();
					}
					break;
				case 3:
					System.out.print("Author to search: ");
					String author = keyboard.nextLine();
					
					for(int i = 0;i < inventory.length; i++){
						if(inventory[i] != null) {
							if(inventory[i].getAuthor().equals(author)) {
								System.out.println("\nBook #" + (i + 1) + "\n" + inventory[i]);
							}
						}
					}
					break;
				case 4:
					System.out.print("Price to search: ");
					double price = keyboard.nextDouble();
					keyboard.nextLine();
					
					for(int i = 0;i < inventory.length; i++){
						if(inventory[i] != null) {
							if(inventory[i].getPrice() < price) {
								System.out.println("\nBook #" + (i + 1) + "\n" + inventory[i]);
							}							
						}
					}
					break;
				case 5:
					System.out.println("QuitingÉ");
					System.exit(0);
					break;
				default:
					System.out.println("Unknown choice, try again.");
					break;
			}
		}
	}
	
	private static long promptForISBN() {
	
		long ISBN;
	
		do {
			System.out.print("ISBN: ");
			ISBN = keyboard.nextLong();
			
			if (ISBN < 1000000000000L
				|| ISBN > 9999999999999L) System.out.println("Invalid ISBN: ISBN must be a positive "
											+ "13 digit integer.");
		} while (ISBN < 1000000000000L
				|| ISBN > 9999999999999L); //ISBN numbers are 13 digits long.
				
		return ISBN;
	}
	
	private static double promptForPrice() {
	
		double price;
	
		do {
			System.out.print("Price: ");
			price = keyboard.nextDouble();
			keyboard.nextLine();
			
			if (price < 0) System.out.println("Invalid Price: Price must be a positive number.");
		} while (price < 0);
		
		return price;
	}
	
	private static boolean promptPassword() {
		for(int i = 2; i >= 0; i--) {
			System.out.print("Enter your password: ");
			String input = keyboard.nextLine();
			if(password.equals(input)) {
				return true;
			} else {
				if(i == 0) {
					System.out.println("Wrong password, no attempts left. Returning to main menu.");
				} else {
					System.out.print("Wrong password, try again (" + i + " attempt(s) left). ");
				}
			}
		}
		return false;
	}
	
	private static void promptUpdateBook() {
		System.out.print("\nWhat book number? ");
		
		int index = keyboard.nextInt() - 1;  // array is zero-indexed
		keyboard.nextLine();
		
		if(index >= maxBooks || index <= -1 || inventory[index] == null) {
			System.out.print("\nThat book number doesn't exist. Try again? ('y' or 'yes', anything else bring you back to the main menu): ");
			String response = keyboard.nextLine();
			if(response.equals("yes") || response.equals("y")) {
				promptUpdateBook();
			}
			return;
		} else {
			promptUpdateIndividualBook(index);
		}
	}
	
	private static void promptUpdateIndividualBook(int index) {
		System.out.println("\nBook #" + (index + 1) + "\n" + inventory[index]);
		
		String prompt = "\nWhat information would you like to change? \n"
				+ "	1. author\n"
				+ "	2. title\n"
				+ "	3. ISBN\n"
				+ "	4. price\n"
				+ "	5. Quit\n"
				+ "Enter your choice > ";
		System.out.print(prompt);
		
		int update_choice = keyboard.nextInt();
		keyboard.nextLine();
		
		switch (update_choice) {
			case 1:
				System.out.print("Enter a new author: ");
				String author = keyboard.nextLine();
				inventory[index].setAuthor(author);

				promptUpdateIndividualBook(index);
				break;
			case 2:
				System.out.print("Enter a new title: ");
				String title = keyboard.nextLine();
				inventory[index].setTitle(title);
				
				promptUpdateIndividualBook(index);
				break;
			case 3:
				System.out.print("Enter a new ISBN: ");
				long ISBN = promptForISBN();
				inventory[index].setISBN(ISBN);
				
				promptUpdateIndividualBook(index);
				break;
			case 4:
				System.out.print("Enter a new Price: ");
				double price = promptForPrice();
				inventory[index].setPrice(price);
				
				promptUpdateIndividualBook(index);
				break;
			case 5:
				break;
			default:
				promptUpdateIndividualBook(index);
				break;
		}	
	}
}
