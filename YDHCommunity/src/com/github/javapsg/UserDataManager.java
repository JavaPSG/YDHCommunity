package com.github.javapsg;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserDataManager {

	private Map<UUID, User> userMap = Collections.synchronizedMap(new HashMap<>());

	private UUID createUUID() {
		UUID uuid = UUID.randomUUID();
		if (userMap.containsKey(uuid)) {
			return createUUID();
		} else {
			return uuid;
		}

	}

	public User addUser(User user) {
		return userMap.put(createUUID(), user);
	}

	public boolean removeUser(UUID uuid) {
		if (userMap.containsKey(uuid)) {
			userMap.remove(uuid);
			return true;
		}
		return false;
	}

	public User getUser(UUID uuid) {
		return userMap.get(uuid);
	}

}
