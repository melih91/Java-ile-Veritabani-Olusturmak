import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Baglanti {

	private String kullaniciAdi = "root";
	private String parola = "";
	private String dbIsmi = "demo";
	private String host = "localhost";
	private int port = 3306;
	private Connection con = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;

	public void baglanti() {

		// "jdbc:mysql://localhost:3306/demo"
		String url = "jdbc:mysql://" + host + ":" + port + "/" + dbIsmi + "?useUnicode=true&characterEncoding=utf8";

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver bulunamadi.");
		}
		try {
			con = DriverManager.getConnection(url, kullaniciAdi, parola);
			System.out.println("Baglanti basarili..");
		} catch (SQLException e) {
			System.out.println("Baglanti basarisiz..");
		}

	}

	public void calisanlariGetir() {
		String sorgu = "Select * From calisanlar";

		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sorgu);

			while (rs.next()) {
				int id = rs.getInt("id");
				String ad = rs.getString("ad");
				String soyad = rs.getString("soyad");
				String email = rs.getString("email");

				System.out.println("id: " + id + " ad: " + ad + " soyad: " + soyad + " email: " + email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void calisanEkle() {
		try {
			statement = con.createStatement();
			String ad = "Semih";
			String soyad = "Aktaş";
			String email = "semihaktas@gmail.com";
			// INSERT INTO calisanlar(ad, soyad, email) VALUES ('Ali',
			// 'Aktass','aliaktas@gmail.com')
			String sorgu = "Insert Into calisanlar(ad, soyad, email) VALUES (" + "'" + ad + "'," + "'" + soyad + "',"
					+ "'" + email + "')";
			statement.executeUpdate(sorgu);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void calisanGuncelle() {
		try {
			statement = con.createStatement();
			String sorgu = "Update calisanlar Set email = 'yusufcetinkaya@gmail.com' where id = '5'";
			statement.executeUpdate(sorgu);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void calisanSil() {
		try {
			statement = con.createStatement();
			String sorgu = "Delete from calisanlar where id > 5";
			int deger = statement.executeUpdate(sorgu);
			System.out.println("Silme isleminden " + deger + " tane veri etkilendi..");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void preparedCalisanlariGetir(int id) {
/*
		try {
			statement = con.createStatement();
			String sorgu = "Select * From calisanlar Where ad Like 'M%'";
			ResultSet rs = statement.executeQuery(sorgu);
			while (rs.next()) {
				System.out.println("Ad: " + rs.getString("ad"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
*/
		String sorgu = "Select * From calisanlar Where id > ? and ad like ? ";
		try {
			preparedStatement = con.prepareStatement(sorgu);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, "M%");
			
			ResultSet rs =preparedStatement.executeQuery();
			while (rs.next()) {
				String ad = rs.getString("ad");
				String soyad = rs.getString("soyad");
				String email = rs.getString("email");
				
				System.out.println(" ad: " + ad + " soyad: " + soyad + " email: " + email);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		Baglanti baglanti = new Baglanti();
		baglanti.baglanti();
		// System.out.println("Onceki hali...");
		//baglanti.calisanlariGetir();
		//System.out.println("********************");
		// baglanti.CalisanEkle();
		// baglanti.CalisanGuncelle();
		// baglanti.CalisanSil();
		baglanti.preparedCalisanlariGetir(1);
		// System.out.println("Güncel hali...");
		// baglanti.CalisanlariGetir();

	}

}