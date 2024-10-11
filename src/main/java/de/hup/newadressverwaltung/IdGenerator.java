package de.hup.newadressverwaltung;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class IdGenerator {

	private static final int MAX_ID = 100;
	private Set<Integer> usedIds = new HashSet<>();

	public int generateUniqueId() throws Exception {
		if (usedIds.size() >= MAX_ID) {
			throw new Exception("All IDs are taken.");
		}

		int newId;
		do {
			newId = Math.abs(UUID.randomUUID().hashCode()) % MAX_ID;
		} while (usedIds.contains(newId));

		usedIds.add(newId);
		return newId;
	}

	public boolean isIdInUse(int id) {
		return usedIds.contains(id);
	}
}
