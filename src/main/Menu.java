package main;

public class Menu {
	public static void optionsMenu() {
		char dimond = '\u2666';
		//char heart = '\u2665';
		char club = '\u2663';
		//char spade = '\u2660';
		//char degree = '\u00B0';
		System.out.println("");
		for (int i = 0; i < 50; i++) {
			System.out.print(club);
		}
		System.out.println("");
		for (int i = 0; i < 19; i++) {
			System.out.print("*");
		}
		System.out.print(" FORTUNE KENCES"+" ");
		for (int i = 0; i < 20; i++) {
			System.out.print("*");
		}
		System.out.println("");
		for (int i = 0; i < 50; i++) {
			System.out.print(club);
		}
		System.out.println("");
		System.out.println("                  Welcome Receptionist!");
		System.out.println("");
		System.out.println("      Please Choose from below options-");
		System.out.println("");
		System.out.println("      1. See Available Rooms ");
		System.out.println("      2. Guest Registration");
		System.out.println("      3. Book By Reception member");
		System.out.println("      4. Total Bill of Guest");
		System.out.println("      5. Add Services for Guest");
		System.out.println("      6. Booking Information ");
		System.out.println("");
		System.out.println("      Press (n) to exit...");
		System.out.println("");
		for (int i = 0; i < 50; i++) {
			System.out.print(dimond);
		}
		System.out.println("");
	}
}
