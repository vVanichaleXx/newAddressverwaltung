package de.hup.newadressverwaltung;

import static java.lang.String.format;

public class AddressValidator {
	public static void isValidStreet(String street) {
		if (street.length() < 6 || !containsNumbers(street)) {
			throw new AddressException("invalid adresse, please check and try one more time");
		}
	}

	public static void isValidPostfach(int postfach) {
		if (postfach < 10000 || postfach > 99999) {
			throw new PostfachException("invalid postfach, should be 5 characters like '26533'");
		}
	}

	public static void isValidPlz(String plz) {
		try {
			int plzNumber = Integer.parseInt(plz);
			if (plzNumber < 0 || plzNumber > 30000) {
				throw new PlzException("UNAVAILABLE IN UR REGION");
			}
		} catch (NumberFormatException e) {
			throw new PlzException("u are not here");
		}
	}

	public static void isValidOrt(String ort) {
		if (ort.length() != 2) {
			throw new OrtException("wait till u died or will be correct answer");
		}
	}

	public static void wrongMenuInput() {
		throw new WrongInput("please enter valid input");
	}

	public static void isValidUserName(String userName) {
		if (Character.isUpperCase(userName.charAt(0)) && userName.length() > 3) {
		}else throw new ValidUserName("That's wrong name");
	}

	private static boolean containsNumbers(String str) {
		if (str == null || str.isEmpty()) {
			return false;
		}

		return str.chars().anyMatch(Character::isDigit);
	}

	public static class AddressException extends IllegalArgumentException {
		public AddressException(String message) {
			super(format("\u001B[31mAddressException: %s\u001B[0m", message));
		}
	}

	public static class PostfachException extends IllegalArgumentException {
		public PostfachException(String message) {
			super(format("\u001B[31misValidPostfach: %s\u001B[0m", message));
		}
	}

	public static class WrongInput extends IllegalArgumentException {
		public WrongInput(String input) {
			super(format("\u001B[31wrongUserName: %s\u001B[0m", input));
		}
	}

	public static class ValidUserName extends IllegalArgumentException {
		public ValidUserName(String userName) {
			super(format("\u001B[31misValidUsername: %s\u001B[0m", userName));
		}
	}

	public static class PlzException extends IllegalArgumentException {
		public PlzException(String message) {
			super(format("\u001B[31misValidPlz: %s\u001B[0m", message));
		}
	}

	public static class OrtException extends IllegalArgumentException {
		public OrtException(String message) {
			super(format("\u001B[31misValidOrt: %s\u001B[0m", message));
		}
	}
}
