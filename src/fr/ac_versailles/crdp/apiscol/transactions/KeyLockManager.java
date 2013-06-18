package fr.ac_versailles.crdp.apiscol.transactions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class KeyLockManager {
	private class LockEntry {
		int acquisitionCount = 0;
		final Lock lock = new ReentrantLock();
	}

	private static KeyLockManager instance;
	public static String GLOBAL_LOCK_KEY = "global_lock";

	private final Map<String, LockEntry> locks = new HashMap<String, LockEntry>();
	private final Object mutex = new Object();

	public KeyLock getLock(String keyName) {
		synchronized (mutex) {
			LockEntry lockEntry = locks.get(keyName);
			if (lockEntry == null) {
				lockEntry = new LockEntry();
				locks.put(keyName, lockEntry);
			}
			lockEntry.acquisitionCount++;
			return new KeyLock(this, keyName, lockEntry.lock);
		}
	}

	void releaseLock(String keyName) {
		synchronized (mutex) {
			// HACK
			if (locks.containsKey(keyName)) {
				LockEntry lockEntry = locks.get(keyName);
				lockEntry.acquisitionCount--;
				if (lockEntry.acquisitionCount == 0) {
					locks.remove(keyName);
				}
			}
		}
	}

	public static KeyLockManager getInstance() {
		if (instance == null)
			instance = new KeyLockManager();
		return instance;
	}

	private KeyLockManager() {
	}

}
