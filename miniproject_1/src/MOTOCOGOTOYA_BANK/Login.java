package MOTOCOGOTOYA_BANK;
import java.sql.ResultSet;
import java.sql.Date;
import com.mysql.jdbc.Statement;
public class Login extends Sub_modules{
    public long acc_no,contact_number;
    public String cus_name;
    public float balance;
    public  Date joined_date;
	Login(){
		try {
			Statement ss=(Statement) con.createStatement();
			System.out.print("\n\t\t\t\t"+"   "+concolors.BOXING+concolors.TEAL_BACKGROUND+concolors.PURPLE_ITALIC+" Enter your Account Number "+concolors.RESET+"\t:    ");
			long acc_no_l=s.nextLong();
			String acc_no_s=Long.toString(acc_no_l);
			ResultSet rs=ss.executeQuery("select * from login_details where acc_no="+acc_no_s);
			if(rs.first()) {
			acc_no=rs.getLong(1);cus_name=rs.getString(2);balance=rs.getFloat(3);contact_number=rs.getLong(6);joined_date=rs.getDate(7);
			String pwd=pswd();
			if(rs.getString(4).equals(pwd)) {
				System.out.printf("\n\n\t\t\t"+"  "+concolors.BOXING+concolors.LIGHT_PURPLE_BACKGROUND+concolors.WHITE_BOLD_BRIGHT+"\t \t \t "+"      "+" Welcome %-20s ",rs.getString(2)+" \t \t \t \n"+concolors.RESET);
				System.out.println("\n\t\t"+concolors.BLUE_BRIGHT+"Last Login"+concolors.RESET+" : "+concolors.BANANA_YELLOW+rs.getDate(5)+" "+rs.getTime(5)+concolors.RESET+"\t\t\t\t\t"+concolors.BLUE_BRIGHT+"    Balance "+concolors.RESET+":"+concolors.BANANA_YELLOW+rs.getFloat(3)+concolors.RESET+"\n");
				ss.executeUpdate("update login_details set last_login=now() where acc_no="+acc_no_s);
				sub_modules(acc_no,cus_name,balance,contact_number,joined_date);
			}
			else {
				System.out.println("\n\t\t\t\t\t"+"     "+concolors.BOXING+concolors.RED_BACKGROUND+concolors.BANANA_YELLOW+" ->||Incorrect password||<- "+concolors.RESET);
				System.out.println("\n\t\t\t\t"+"   "+concolors.BOXING+concolors.ORANGE_BACKGROUND+concolors.BLACK_ITALIC+"  (1)Forgot Password  "+concolors.RESET+"\t\t"+concolors.BOXING+concolors.ORANGE_BACKGROUND+concolors.BLACK_ITALIC+"    (2)Retry     "+concolors.RESET);
				System.out.printf("\n\t"+concolors.GREEN+"Enter the Number to select the option:"+concolors.RESET);
				int option=s.nextInt();
				switch(option) {
				case 1:
					System.out.print("\n\t"+concolors.DARK_BLUE+"(You can create password with alphanumeric and special characters but make sure to avoid spaces)\n"+concolors.RESET+"\n\t\t\t\t\t"+concolors.BOXING+concolors.GREEN_BACKGROUND_BRIGHT+concolors.BROWN+" Enter New Password "+concolors.RESET+"   :    ");
					String new_passcode=s.next();
					ss.executeUpdate("update login_details set passcode='"+new_passcode+"' where acc_no="+Long.toString(acc_no));
					System.out.println(concolors.RED_ITALIC+"\n\t\t\t\t\t\t"+"    "+"*Password Updated*"+concolors.RESET);
					Login l=new Login();
					break;
				case 2:
					Login l1=new Login();break;
				default:
					System.out.println("\n\t\t\t\t\t\t"+concolors.YELLOW+"  *Enter 1 or 2 only*"+concolors.RESET);
					Login l2=new Login();break;
				}
			}}
			else {
				System.out.println("\n\t\t\t\t"+"   "+concolors.BOXING+concolors.RED_BACKGROUND+concolors.BANANA_YELLOW+"    ->||Account number doesn't exists||<-    "+concolors.RESET);
				Login L=new Login();
			}
		}
		catch(Exception e) {
			System.out.println("\n\t\t\t\t\t\t"+"    "+concolors.RED_BACKGROUND+concolors.BANANA_YELLOW+" *Invalid Entry* \n"+concolors.RESET+"\t");
			System.out.println(e);
		}
			
		}
		static String pswd() {
			if (concolors.console!=null) {
			char[] passcode=concolors.console.readPassword("\n\t\t\t\t"+"   "+concolors.BOXING+concolors.TEAL_BACKGROUND+concolors.PURPLE_ITALIC+"    Enter the Passcode    "+concolors.RESET+"\t:    ",System.in);
			for(int i=0;i<passcode.length;i++) {
				System.out.print("*");
			}
			String pwd=new String(passcode);
			return pwd;
			}
			else {
				System.out.print("\n\t\t\t\t"+"   "+concolors.BOXING+concolors.TEAL_BACKGROUND+concolors.PURPLE_ITALIC+"     Enter the Passcode    "+concolors.RESET+"\t:    ");
				String pwd=s.next();
				return pwd;
			}
		}
		
	}



