package MOTOCOGOTOYA_BANK;
import java.util.Scanner;
public class Main_module {
	public static void main(String[] args) {
		Main_module mm=new Main_module();
		mm.entering_application();
		System.out.println("\n\t\t\t\t\t"+"       "+concolors.ROSY_PINK_BACKGROUND+concolors.WHITE_BOLD_BRIGHT+" *^_^* Have a nice day! *^_^* "+concolors.RESET);
	}
		
		void entering_application(){
		Scanner s=new Scanner(System.in);
		System.out.println("\n\t\t"+"  "+concolors.DARK_BLUE_BACKGROUND+concolors.WHITE_BOLD_BRIGHT+"\t \t \t \t \t MOTOCOGOTOYA BANK \t \t \t \t \t \n"+concolors.RESET);
		System.out.print("\t\t\t"+ concolors.RED_ITALIC+"If you want to exit press '0' or else press any characters or numbers"+concolors.RESET+" : ");
		String e=s.next();
		switch(e) {
		case "0":
			System.out.println("\n\t\t\t\t\t"+"       "+concolors.ROSY_PINK_BACKGROUND+concolors.WHITE_BOLD_BRIGHT+" *^_^* Have a nice day! *^_^* "+concolors.RESET);
			System.exit(0); break;
		default:
		Login l=new Login();break;
		}}
}
