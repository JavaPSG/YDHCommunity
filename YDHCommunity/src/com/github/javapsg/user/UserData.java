package com.github.javapsg.user;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserData {
	protected static final Map<String, User> userMap = Collections.synchronizedMap(new HashMap<>());
	protected static final Map<UUID, String> accountMap = Collections.synchronizedMap(new HashMap<>());
}
