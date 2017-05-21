import java.util.LinkedList;

import db.DbObject;
import db.HbrntDBManager;

public class tester {

	public static void main(String[] args) {

		HbrntDBManager hbrnet = new HbrntDBManager();

		LinkedList<DbObject> fromDB = (LinkedList<DbObject>) hbrnet.getTable("from user_data where Username = 'alice'");
		System.out.println("Im tester");
		System.out.println(fromDB.toString());
	}

}
