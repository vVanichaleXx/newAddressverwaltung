package de.hup.newadressverwaltung;

import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

public class CLI {
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private final Scanner scanner;

	public CLI() {
		this.scanner = new Scanner(System.in);
	}


	public State menu() {
		Optional<State> state = Optional.empty();
		var isValidAction = false;
		while (!isValidAction) {
			System.out.print("""
				Options:
				1. Add a new address              [Enter 'add address' to add]
				2. List all the addresses         [Enter 'list' to list]
				3. Exit the program               [Enter 'exit' to exit]
				4. Delete address                 [Enter 'kill' to kill]
				5. Edit smth                      [Enter 'edit' to edit]
				6. Add a new person               [Enter 'detect' to add human]
				7. List all humans                [Enter 'people' to people]
				8. Add person                     [Enter 'add person' to add someone]
				""");
			String nextLine = scanner.nextLine().trim();

			switch (nextLine) {
				case "add address" -> state = Optional.of(State.ADDNEWADDRESS);
				case "list" -> state = Optional.of(State.LIST);
				case "exit" -> state = Optional.of(State.EXIT);
				case "kill" -> state = Optional.of(State.KILL);
				case "edit" -> state = Optional.of(State.EDIT);
				case "detect" -> state = Optional.of(State.DETECT);
				case "add person" -> state = Optional.of(State.ADDPERSON);
				case "people" -> state = Optional.of(State.PEOPLE);
				default -> System.out.println("please enter a valid option");
			}

			if (state.isPresent()) {
				isValidAction = true;
			}
		}
		return state.get();
	}

	public int promptId() {
		Optional<String> result = Optional.empty();
		while (result.isEmpty()) {
			System.out.print("Enter id: ");
			try {
				return Integer.parseInt(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				logger.warning("Invalid input. Please enter a valid id.");
			}
		}
		return Integer.parseInt(result.get());
	}

	public String promptStreet() {
		Optional<String> result = Optional.empty();
		while (result.isEmpty()) {
			System.out.print("Enter Str and Hausnummer: ");
			String input = scanner.nextLine().trim().replaceAll("\\s+", " ");

			if (!input.isEmpty()) {
				return input;
			}

			logger.warning("Input cannot be empty. Please try again.");
		}
		return result.get();
	}

	public int promptPostfach                                                                                       () {
		Optional<String> result = Optional.empty();
		while (result.isEmpty()) {
			System.out.print("Enter Postfach: ");
			try {
				return Integer.parseInt(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				logger.warning("Invalid input. Please enter a valid integer.");
			}
			System.out.println("new postfach accepted");
		}
		return Integer.parseInt(result.get());
	}

	public String promptPlz() {
		Scanner scanner = new Scanner(System.in);
		String input = "";
		boolean isValid = false;

		while (!isValid) {
			System.out.print("Enter PLZ: ");
			input = scanner.nextLine().trim();

			if (input.matches("\\d{5}")) {
				isValid = true;
			} else {
				System.out.println("Invalid input. Please enter exactly 5 digits.");
			}
		}

		return input;
	}

	public String promptOrt() {
		Optional<String> result = Optional.empty();
		while (result.isEmpty()) {
			System.out.print("Enter Ort: ");
			String input = scanner.nextLine().trim();
			if (!input.isEmpty()) {
				return input;
			}
			logger.warning("Input cannot be empty. Please try again.");
		}
		return result.get();
	}

	public String promptName() {
		System.out.print("Enter ur name: ");
		return scanner.nextLine().trim();
	}

	public String promptEditMenu() {
		System.out.println("what you wanna change?");
		System.out.print("""
			Options:
			1. edit street \t [Enter 'street' to street]
			2. edit postfach \t [Enter 'postfaach' to postfach]
			3. change plz \t [Enter 'plz' to plz]
			4. to change stadt \t [Enter 'ort' to ort]
			5. if u wanna go back \t [Enter 'ENTER' to back]
			""");
		return scanner.nextLine();
	}

	public String listOptionsMenu(){
		System.out.println("""
			If u wanna go back to main menu [press "ENTER"]
			Address with owners             [Enter "with"]
			unavailable                     [unavailable]
			""");
		return scanner.nextLine().trim();
	}

	public String peopleOptionsMenu(){
		System.out.println("""
			If u wanna go back to main menu [press "ENTER"]
			Address with owners             [Enter "people"]
			unavailable                     [unavailable for people]
			""");
		return scanner.nextLine().trim();
	}

	public String optionsMenuForPeople(){
		System.out.println("""
			If u wanna go back to main menu     [press "ENTER"]
			If u wanna see people with address  [Enter "people with"]
			Or find address by name             [Enter "find by name"]
			Or find address by id               [Enter "find by id"]
			""");
		return scanner.nextLine().trim();
	}

	public void addressesWithHumans() {
		System.out.println("pwh");
	}

	public void unavailable() {
		System.out.println("Addresses without owners:");
	}




}
