package de.hup.newadressverwaltung;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class NewUnaddressableApplication {
	private static final Logger logger = Logger.getLogger(NewUnaddressableApplication.class.getName());
	private final List<Address> addressList;

	// ----------------------------------------------------------------------------------------------------------------

	private static final List<People> people = new ArrayList<>(List.of(
		new People("maksym", -1),
		new People("ivan", 101),
		new People("nick", -1)
	));

	// ----------------------------------------------------------------------------------------------------------------

	public NewUnaddressableApplication() {
		addressList = new ArrayList<>(List.of(
			new Address(101, "Comeniusstr32", 43342, "22332", "BS")
		));
	}

	// ----------------------------------------------------------------------------------------------------------------

	record People                                                                         (String name, int addressId) {
		@Override
		public String toString() {
			return "name: " + name + ", id: " + (addressId != -1 ?  + addressId : "no id or address");
		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	public static void main                                                                            (String[] args) {
		new NewUnaddressableApplication().run(new CLI());
	}

	// ----------------------------------------------------------------------------------------------------------------

	private static void AddressExtention                                                           (Runnable runnable) {
		for (; ; ) {
			try {
				runnable.run();

				if (false) {
					throw new AddressValidator.AddressException(null);
				}

				return;
			} catch (AddressValidator.AddressException exception) {
				logger.warning(exception.getMessage());
			}
		}
	}

	public void run                                                                                          (CLI cli) {
		var exit = false;
		while (!exit) {
			var state = cli.menu();

			switch (state) {
				case LIST -> {			System.out.println("All addresses: ");                            addressList();
					optionsMenuForList(cli);
				}
				case ADDNEWADDRESS -> addAddress(cli);
				case ADDPERSON ->
					addNewPerson(cli);
				case EXIT -> {
					exit = true;
					System.out.println("byyyeeeeeeeee");
				}
				case KILL -> killAddress(cli);
				case EDIT -> {
					final var id = cli.promptId();

					switch (cli.promptEditMenu()) {
						case "street":
							final var street = cli.promptStreet();
							forAddress(id, address -> address.setStreet(street));
							optionsMenu(cli);
							break;
						case "postfach":
							final var postfach = cli.promptPostfach();
							forAddress(id, address -> address.setPostfach(postfach));
							optionsMenu(cli);
							break;
						case "plz":
							final String plz = cli.promptPlz();
							forAddress(id, address -> address.setPlz(plz));
							optionsMenu(cli);
							break;
						case "ort":
							final var stadt = cli.promptOrt();
							forAddress(id, address -> address.setOrt(stadt));
							optionsMenu(cli);
							break;
						case "":
							break;
							default:
								AddressValidator.wrongMenuInput();
								optionsMenu(cli);
					}
				}
				case DETECT -> System.out.println("detect soon");
				case PEOPLE -> {
					justListOfPeople(cli);
					optionsMenuForPeople(cli);
				}
			}
		}
	}

	private void addNewPerson                                                                                (CLI cli) {
		{
			String name = cli.promptName();
			int addressId = cli.promptId();

			if (isValidAddressId(addressId)) {
				people.add(new People(name, addressId));
				System.out.println(name + " was added and assigned to address ID: " + addressId);
			} else {
				people.add(new People(name, -1));
				System.out.println(name + " was added with no address.");
			}
		}
	}

	private boolean isValidAddressId(int addressId) {
		return addressList.stream().anyMatch(address -> address.getId() == addressId);
	}
	// ----------------------------------------------------------------------------------------------------------------

	private void forAddress                                                       (int id, Consumer<Address> consumer) {
		for (final var address : addressList) {
			if (address.getId() == id) {
				consumer.accept(address);
				break;
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------




	// ----------------------------------------------------------------------------------------------------------------

	private void killAddress                                                                                 (CLI cli) {

		int id = cli.promptId();

		for (var address : addressList) {
			if (address.getId() == id) {
				addressList.remove(address);
				System.out.println("Address was deleted.");
				return;
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	private void addAddress                                                                                  (CLI cli) {
		// Create an instance of IdGenerator
		IdGenerator idGenerator = new IdGenerator();

		// AtomicReference and AtomicInteger are used to capture validated inputs
		AtomicReference<String> street = new AtomicReference<>();
		AtomicInteger postfach = new AtomicInteger();
		AtomicReference<String> plz = new AtomicReference<>();
		AtomicReference<String> ort = new AtomicReference<>();

		// Validate the street input
		AddressExtention(() -> {
			street.set(cli.promptStreet());
			AddressValidator.isValidStreet(street.get());  // Validation
		});

		// Validate the postfach input
		postfachExtention(() -> {
			postfach.set(cli.promptPostfach());
			AddressValidator.isValidPostfach(postfach.get());  // Validation
		});

		// Validate the postal code (PLZ)
		plzExtention(() -> {
			plz.set(cli.promptPlz());
			AddressValidator.isValidPlz(plz.get());  // Validation
		});

		// Validate the city (Ort)
		ortExtention(() -> {
			ort.set(cli.promptOrt());
			AddressValidator.isValidOrt(ort.get());  // Validation
		});

		try {
			// Generate a unique ID for the new address
			int id = idGenerator.generateUniqueId();

			// Create the Address object and add it to the list
			Address address = new Address(id, street.get(), postfach.get(), plz.get(), ort.get());
			addressList.add(address);

			System.out.println("Address added: " + address);
		} catch (Exception e) {
			logger.warning("Failed to add address: " + e.getMessage());
		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	private void postfachExtention                                                                 (Runnable runnable) {
		for (; ; ) {
			try {
				runnable.run();

				if (false) {
					throw new AddressValidator.PostfachException(null);
				}

				return;
			} catch (AddressValidator.PostfachException exception) {
				logger.warning(exception.getMessage());
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	private void plzExtention                                                                      (Runnable runnable) {
		for (; ; ) {
			try {
				runnable.run();

				if (false) {
					throw new AddressValidator.PlzException(null);
				}

				return;
			} catch (AddressValidator.PlzException exception) {
				logger.warning(exception.getMessage());
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	private void ortExtention                                                                      (Runnable runnable) {
		for (; ; ) {
			try {
				runnable.run();

				if (false) {
					throw new AddressValidator.OrtException(null);
				}

				return;
			} catch (AddressValidator.OrtException exception) {
				logger.warning(exception.getMessage());
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	private void userExtention(Runnable runnable) {
		for (; ; ) {
			try {
				runnable.run();

				if (false) {
					throw new AddressValidator.ValidUserName(null);
				}

				return;
			} catch (AddressValidator.ValidUserName exception) {
				logger.warning(exception.getMessage());
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	private void addressList                                                                                        () {
		for (final var address : addressList) {
			System.out.println(address);

		}
	}

	// ----------------------------------------------------------------------------------------------------------------
	private void justListOfPeople(CLI cli) {
		for (final var person : people) {
				System.out.println(person);
		}
		optionsMenuForPeople(cli);
	}


	private void listOfPeople(CLI cli) {
		for (final var person : people) {
			if (person.addressId() != -1) {
				var address = addressList.stream()
					.filter(a -> a.getId() == person.addressId())
					.findFirst()
					.orElse(null);

				System.out.println(person + ", Address: " + (address != null ? address : "Not found"));
			} else {
				System.out.println(person);
			}
		}
		optionsMenuForPeople(cli);
	}

	private void optionsMenuForList(CLI cli){
		switch(cli.peopleOptionsMenu()){
			case "people": listOfPeople(cli); break;
			case "": break;
			default: AddressValidator.wrongMenuInput();
		}
	}

	private void optionsMenu(CLI cli){
		switch(cli.peopleOptionsMenu()){
			case "people": listOfPeople(cli); break;
			case "": break;
			default: AddressValidator.wrongMenuInput();
		}
	}

	private void optionsMenuForPeople(CLI cli){
		switch(cli.optionsMenuForPeople()){
			case "people with": listOfPeople(cli); break;
			case "find by id": searchAddressById(cli); break;
			case "find by name": searchAddressByUser(cli); break;
			case "": break;
			default: AddressValidator.wrongMenuInput();
		}
	}

	public void searchAddressByUser(CLI cli) {
		String userName = cli.promptName();
		People foundPerson = people.stream()
			.filter(person -> person.name().equalsIgnoreCase(userName))
			.findFirst()
			.orElse(null);

		if (foundPerson != null) {
			if (foundPerson.addressId() != -1) {
				Address foundAddress = addressList.stream()
					.filter(address -> address.getId() == foundPerson.addressId())
					.findFirst()
					.orElse(null);

				if (foundAddress != null) {
					System.out.println("User: " + foundPerson.name() + " lives at " + foundAddress);
				} else {
					System.out.println("No address found for the given user.");
				}
			} else {
				System.out.println(userName + " has no assigned address.");
			}
		} else {
			System.out.println("User not found.");
		}
		optionsMenuForPeople(cli);
	}

	public void searchAddressById(CLI cli) {
		int userId = cli.promptId();
		People foundPerson = people.stream()
			.filter(person -> person.addressId() == userId)
			.findFirst()
			.orElse(null);

		if (foundPerson != null) {
			Address foundAddress = addressList.stream()
				.filter(address -> address.getId() == userId)
				.findFirst()
				.orElse(null);

			if (foundAddress != null) {
				System.out.println("User: " + foundPerson.name() + " lives at " + foundAddress);
			} else {
				System.out.println("No address found for the given user ID.");
			}
		} else {
			System.out.println("No user found with the given ID.");
		}
		optionsMenuForPeople(cli);
	}


}
