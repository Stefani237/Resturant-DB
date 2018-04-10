
public class Main {

	private static LoginWin login;
	public static DBconnector dbCon;

	public static void main(String[] args) {		
		// working with application
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				login = new LoginWin();
			}
		});
	}
}
