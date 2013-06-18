package fr.ac_versailles.crdp.apiscol.database;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

import fr.ac_versailles.crdp.apiscol.utils.LogUtility;

public class MongoUtils {
	private static Logger logger;

	public static Mongo getMongoConnection() throws DBAccessException {

		Mongo mongo;
		try {
			getLogger().info("Creating a new Mongo instance");
			mongo = new MongoClient();
			mongo.setWriteConcern(WriteConcern.SAFE);
		} catch (UnknownHostException e) {
			String message = "Problem while connecting to mongodb host";
			getLogger().error(message);
			throw new DBAccessException(message + e.getMessage());
		} catch (MongoException e) {
			String message = "Problem while connecting to mongodb ";
			getLogger().error(message);
			throw new DBAccessException(message + e.getMessage());
		}
		return mongo;
	}

	public static DBCollection getCollection(String dbName,
			String collectionName, Mongo mongo) throws DBAccessException {
		DB apiscolDB;
		;
		try {
			apiscolDB = mongo.getDB(dbName);
		} catch (MongoException e) {
			String message = "Problem while accessing to database " + dbName;
			getLogger().error(message);
			throw new DBAccessException(message + e.getMessage());
		}
		DBCollection resourcesCollection;
		try {
			resourcesCollection = apiscolDB.getCollection(collectionName);
		} catch (MongoException e) {
			String message = "Problem while accessing collection "
					+ collectionName;
			getLogger().error(message);
			throw new DBAccessException(message + e.getMessage());
		}
		return resourcesCollection;
	}

	public static void dbDisconnect(Mongo mongo) {
		if (mongo != null) {
			mongo.close();
			System.out
					.println("The database connection has been closed in thread "
							+ Thread.currentThread().getId());
		}
	}

	private static void createLogger() {
		logger = LogUtility.createLogger(MongoUtils.class.getCanonicalName());

	}

	public static Logger getLogger() {
		if (logger == null)
			createLogger();
		return logger;
	}
}
