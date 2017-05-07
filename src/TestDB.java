
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDB {
	
	public Connection con = null;
	
	public TestDB() {
		
		//Importieren der JDBC Driver Klasse
		try {
			System.out.println("Einbinden der JDBC Driver Klasse...");
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Fehler beim einbinden der hsqldb jar");
			e.printStackTrace();
		}
		
		//Aufbauen einer Verbindung zur db
		try {
			System.out.println("Verbinden zur Datenbank...");
			con = DriverManager.getConnection("jdbc:hsqldb:file:db; shutdown=true", "SA", "");
		} catch (SQLException e) {
			System.out.println("Fehler beim verbinden zur DB");
			e.printStackTrace();
		}
		System.out.println("Erfolgreich verbunden!");
		System.out.println("--------------------------------------------");
		
		//Verifizieren der Ausgabe eines Teststatements
		System.out.println("Verifizieren der Ausgabe eines Teststatements");
		selectAllTestTable();
		
		//Schließen der Verbindung
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Verbindung geschlossen.");
	}
	
	public void selectAllTestTable() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Test");
			int idresult;
			String textresult;
			while(rs.next()) {
				idresult = rs.getInt(1);
				textresult = rs.getString(2);
				System.out.println("ID: " + idresult + ", Content: " + textresult);
				if (idresult == 1 && textresult.equals("Test")) {
					System.out.println("Test erfolgreich. Korrekte Ausgabe");
				} else {
					System.out.println("Fehlerhafte Ausgabe.");
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new TestDB();
	}

}
