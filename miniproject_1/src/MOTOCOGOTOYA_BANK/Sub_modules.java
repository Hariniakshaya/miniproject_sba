package MOTOCOGOTOYA_BANK;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Scanner;
import java.time.*;
import com.mysql.jdbc.Statement;
public class Sub_modules {
    static Scanner s=new Scanner(System.in);
    static Connection con = connection.getConnection();
	void sub_modules(long acc_no,String cus_name,float balance,long contact_number,Date joined_date ){
		System.out.print("\t \t "+concolors.BOXING+concolors.BANANA_YELLOW_BACKGROUND+concolors.DARK_BLUE+" (1)Transfer Money "+concolors.RESET+"\t\t"+concolors.BOXING+concolors.BANANA_YELLOW_BACKGROUND+concolors.DARK_BLUE+" (2)View Statements "+concolors.RESET+"\t\t"+concolors.BOXING+concolors.BANANA_YELLOW_BACKGROUND+concolors.DARK_BLUE+" (3)service requests "+concolors.RESET+"\t\t"+concolors.BOXING+concolors.BANANA_YELLOW_BACKGROUND+concolors.DARK_BLUE+" (4)Logout "+concolors.RESET+concolors.PURPLE_BRIGHT+"\n\n\tEnter the Number to select the option: "+concolors.RESET);
		byte option=s.nextByte();		
		switch(option) {
		case 1:send_money(acc_no,balance,contact_number,cus_name,joined_date);break;
		case 2:view_statement(acc_no,joined_date,cus_name,balance,contact_number);break;
		case 3:services(acc_no,cus_name,contact_number,balance,joined_date);break;
		case 4:concolors.logout();break;
		default:
			System.out.println("Invalid Entry");break;
		}
	}
		void send_money(long acc_no,float balance,long contact_number,String cus_name, Date joined_date) {
		System.out.println("\n\t\t\t\t"+concolors.BOXING+concolors.GREEN_BACKGROUND_BRIGHT+concolors.DARK_RED+" 1)Through Mobile number "+concolors.RESET+"\t"+"   "+concolors.BOXING+concolors.GREEN_BACKGROUND_BRIGHT+concolors.DARK_RED+" 2)Through Account number "+concolors.RESET+"\n");
		System.out.print("\t"+concolors.ORANGE+"Enter the number to select the option: "+concolors.RESET);
		byte option=s.nextByte();		
		if(option==1) {
			try {
			Statement ss=(Statement) con.createStatement();
			System.out.print("\n\t\t\t"+"  "+concolors.BOXING+concolors.BLUE_BACKGROUND+concolors.BLACK_BOLD+"       Enter the Mobile number      "+concolors.RESET+" :     ");
			long mobile_number=s.nextLong();
			ResultSet rs1=ss.executeQuery("select * from login_details where mobile_number="+Long.toString(mobile_number));
			if(rs1.first()) {
			if (mobile_number==rs1.getLong(6)) {
				long b_account_no=rs1.getLong(1);
				float b_balance=rs1.getFloat(3);
				System.out.print("\n\t\t\t"+"  "+concolors.BOXING+concolors.BLUE_BACKGROUND+concolors.BLACK_BOLD+" Enter the amount to be transferred "+concolors.RESET+" :     ");
				float amount=s.nextFloat();
				if(amount<(balance-1000)) {
					ss.executeUpdate("update login_details set balance="+Float.toString(balance-amount)+"where mobile_number="+Long.toString(contact_number) );
					ss.executeUpdate("update login_details set balance="+Float.toString(b_balance+amount)+"where mobile_number="+Long.toString(mobile_number));
					ss.executeUpdate("insert into sender(acc_no,description,withdrawal,balance) values("+Long.toString(acc_no)+",'Transferred money to "+Long.toString(b_account_no)+"',"+Float.toString(amount)+","+Float.toString(balance-amount)+")");
					ss.executeUpdate("insert into beneficiary(acc_no,description,deposit,balance) values("+Long.toString(b_account_no)+",'Money Transferred by "+Long.toString(acc_no)+"',"+Float.toString(amount)+","+Float.toString(b_balance+amount)+")");
					ResultSet rs2=ss.executeQuery("select * from sender where acc_no="+Long.toString(acc_no)+" order by tran_id desc limit 1");
					rs2.next();
					System.out.println("\n\t\t\t\t\t\t"+"  "+concolors.BOXING+concolors.PURPLE_BACKGROUND_BRIGHT+concolors.BANANA_YELLOW+" Transaction Completed "+concolors.RESET+concolors.LIGHT_GREEN+"\n\n\t\t\t Your Transaction ID is "+rs2.getString(1)+". Your Current Balance is Rs."+rs2.getString(6)+concolors.RESET+"\n");
					sub_modules(acc_no, cus_name, balance, contact_number, joined_date);
				}
				else { 
					System.out.println("\n\t\t\t\t\t\t"+"  "+concolors.BOXING+concolors.RED_BACKGROUND+concolors.BANANA_YELLOW+" Not enough balance "+concolors.RESET+"\n");
					sub_modules(acc_no, cus_name, balance, contact_number, joined_date);
				}
			}}
			else {
				System.out.println("\n\t\t\t\t\t\t"+"  "+concolors.BOXING+concolors.RED_BACKGROUND+concolors.BANANA_YELLOW+" Invalid Mobile number "+concolors.RESET+"\n");
				sub_modules(acc_no, cus_name, balance, contact_number, joined_date);
			}
				}catch(Exception e) {
					System.out.println("\n\t\t\t\t\t\t"+"    "+concolors.RED_BACKGROUND+concolors.BANANA_YELLOW+" *Invalid Entry* \n"+concolors.RESET+"\t");
					System.out.println(e);
					}}
		else if(option==2) {
			try {
			Statement ss=(Statement) con.createStatement();
			System.out.print("\n\t\t\t"+"  "+concolors.BOXING+concolors.BLUE_BACKGROUND+concolors.BLACK_BOLD+"       Enter the Account number:      "+concolors.RESET+" :     ");
			long Account_number=s.nextLong();
			ResultSet rs2=ss.executeQuery("select * from login_details where acc_no="+Long.toString(Account_number));
			if(rs2.first()) {
			if (Account_number==rs2.getLong(1)) {
				float b_balance=rs2.getFloat(3);
				System.out.print("\n\t\t\t"+"  "+concolors.BOXING+concolors.BLUE_BACKGROUND+concolors.BLACK_BOLD+"  Enter the amount to be transferred  "+concolors.RESET+" :     ");
				float amount=s.nextFloat();
				if(amount<(balance-1000)) {
					ss.executeUpdate("update login_details set balance="+Float.toString(balance-amount)+"where acc_no="+Long.toString(acc_no) );
					ss.executeUpdate("update login_details set balance="+Float.toString(b_balance+amount)+"where acc_no="+Long.toString(Account_number));
					ss.executeUpdate("insert into sender(acc_no,description,withdrawal,balance) values("+Long.toString(acc_no)+",'Transferred money to "+Long.toString(Account_number)+"',"+Float.toString(amount)+","+Float.toString(balance-amount)+")");
					ss.executeUpdate("insert into beneficiary(acc_no,description,deposit,balance) values("+Long.toString(Account_number)+",'Money Transferred by "+Long.toString(acc_no)+"',"+Float.toString(amount)+","+Float.toString(b_balance+amount)+")");
					ResultSet rs3=ss.executeQuery("select * from sender where acc_no="+Long.toString(acc_no)+" order by tran_id desc limit 1");
					rs3.next();
					System.out.println("\n\t\t\t\t\t\t"+"  "+concolors.BOXING+concolors.PURPLE_BACKGROUND_BRIGHT+concolors.BANANA_YELLOW+" Transaction Completed "+concolors.RESET+concolors.LIGHT_GREEN+"\n\n\t\t\t Your Transaction ID is "+rs3.getString(1)+". Your Current Balance is Rs."+rs3.getString(6)+concolors.RESET+"\n");
					sub_modules(acc_no, cus_name, balance, contact_number, joined_date);
				}
				else {
					System.out.println("\n\t\t\t\t\t\t"+"  "+concolors.BOXING+concolors.RED_BACKGROUND+concolors.BANANA_YELLOW+" Not enough balance "+concolors.RESET+"\n");
					sub_modules(acc_no, cus_name, balance, contact_number, joined_date);	
				}
				}}
			else {
				System.out.println("\n\t\t\t\t\t\t"+" "+concolors.BOXING+concolors.RED_BACKGROUND+concolors.BANANA_YELLOW+" Invalid Account number "+concolors.RESET+"\n");
				sub_modules(acc_no, cus_name, balance, contact_number, joined_date);
				}
			}catch(Exception e) {
				System.out.println("\n\t\t\t\t\t\t"+"    "+concolors.RED_BACKGROUND+concolors.BANANA_YELLOW+" *Invalid Entry* \n"+concolors.RESET+"\t");
				System.out.println(e);
					}}
		else {
			System.out.println("\n\t\t\t\t\t\t"+concolors.YELLOW+"  *Enter 1 or 2 only*"+concolors.RESET);
			send_money(acc_no,balance,contact_number,cus_name,joined_date);
		}

			}

		void view_statement(long acc_no,Date joined_date,String cus_name,float balance,long contact_number) {
		try {
			Statement ss=(Statement)con.createStatement();
			System.out.print("\n\t\t\t\t"+"   "+concolors.BOXING+concolors.WHITE_BACKGROUND_BRIGHT+concolors.BLACK_ITALIC+" 1.All Statements "+concolors.RESET+" \t\t"+concolors.BOXING+concolors.WHITE_BACKGROUND_BRIGHT+concolors.BLACK_ITALIC+" 2.by date wise "+concolors.RESET+"\n\n\t"+concolors.BROWN+"Enter the number to select the option: "+concolors.RESET);
			int option=s.nextInt();
			if(option==1) {
			ss.executeUpdate("create view statement as select * from sender where acc_no="+Long.toString(acc_no)+" union all select * from beneficiary where acc_no="+Long.toString(acc_no));
			ResultSet rs4=ss.executeQuery("select * from statement order by Date desc");
			if(rs4.first()) {
				rs4.beforeFirst();
			System.out.println("\n\t-------------------------------------------------------------------------------------------------------------");
			System.out.println("\t|           DATE           |            DESCRIPTION            |  WITHDRAWAL  |    DEPOSIT    |   BALANCE   |");
			System.out.println("\t-------------------------------------------------------------------------------------------------------------");
			while(rs4.next())
			{
			System.out.printf("\t| %-24s | %-33s | %12s | %13s | %11s |%n",rs4.getString(8),rs4.getString(3),rs4.getString(4),rs4.getString(5),rs4.getString(6));	
			}
			System.out.println("\t-------------------------------------------------------------------------------------------------------------\n");

			ss.executeUpdate("drop view statement");
			sub_modules(acc_no, cus_name, balance, contact_number, joined_date);
			}
			else{
				ss.executeUpdate("drop view statement");
				System.out.println("\n\t\t\t\t\t\t"+concolors.RED_ITALIC+"      *No records*   \n"+concolors.RESET);
				sub_modules(acc_no, cus_name, balance, contact_number, joined_date);
				}
			}
			else if(option==2) {
			System.out.printf("\n\t"+concolors.BLUE_UNDERLINED+"Your started this account on is "+joined_date+". So your from and to should be equal to or between "+joined_date+" and "+LocalDate.now()+"."+concolors.RESET+concolors.TEAL+"\n\n\t\t\t\t\tEnter the date as per YYYY-MM-DD format"+concolors.RESET+"\n");
			System.out.printf("\n\t\t\t\t\t\t"+concolors.BOXING+concolors.BANANA_YELLOW_BACKGROUND+concolors.GREEN_BOLD_BRIGHT+" From "+concolors.RESET+"\t:  ");
			String from=s.next();
			LocalDate from_date=LocalDate.parse(from);String sql_from_date=from_date.minusDays(1).toString();
			System.out.printf("\n\n\t\t\t\t\t\t"+concolors.BOXING+concolors.BANANA_YELLOW_BACKGROUND+concolors.GREEN_BOLD_BRIGHT+"  To  "+concolors.RESET+"\t:  ");
			String to=s.next();
			LocalDate to_date=LocalDate.parse(to);String sql_to_date=to_date.plusDays(1).toString();
			LocalDate joineddate=joined_date.toLocalDate();
			if((from_date.isEqual(joineddate)||from_date.isAfter(joineddate)) && (to_date.isEqual(LocalDate.now())||to_date.isBefore(LocalDate.now()))) 
			{
			ss.executeUpdate("create view statement as select * from sender where acc_no="+Long.toString(acc_no)+" union all select * from beneficiary where acc_no="+Long.toString(acc_no));
			ResultSet rs5=ss.executeQuery("select * from statement where Date between '"+sql_from_date+"' and '"+sql_to_date+"' order by Date desc");
			if(rs5.first()) {
				rs5.beforeFirst();
			System.out.println("\n\t-------------------------------------------------------------------------------------------------------------");
			System.out.println("\t|           DATE           |            DESCRIPTION            |  WITHDRAWAL  |    DEPOSIT    |   BALANCE   |");
			System.out.println("\t-------------------------------------------------------------------------------------------------------------");
			while(rs5.next())
			{
			System.out.printf("\t| %-24s | %-33s | %12s | %13s | %11s |%n",rs5.getString(8),rs5.getString(3),rs5.getString(4),rs5.getString(5),rs5.getString(6));	
			}
			System.out.println("\t-------------------------------------------------------------------------------------------------------------\n");

			ss.executeUpdate("drop view statement");
			sub_modules(acc_no, cus_name, balance, contact_number, joined_date);
			}
			else {
				ss.executeUpdate("drop view statement");
				System.out.println("\n\t\t\t\t\t\t"+concolors.RED_ITALIC+"      *No records*   \n"+concolors.RESET);
				sub_modules(acc_no, cus_name, balance, contact_number, joined_date);
			}}
			else {
				System.out.println("\n\t"+concolors.BLUE_UNDERLINED+"Your started this account on is "+joined_date+". So your from and to should be equal to or between "+joined_date+" and "+LocalDate.now()+"."+concolors.RESET+"\n");
				view_statement(acc_no, joined_date, cus_name, balance, contact_number);
			}
			}
			else {
				System.out.println("\n\t\t\t\t\t\t"+concolors.LIGHT_PINK+"  *Enter 1 or 2 only*"+concolors.RESET);
				view_statement(acc_no, joined_date, cus_name, balance, contact_number);
			}
	}catch(Exception e) {
		System.out.println("\n\t\t\t\t\t\t"+"    "+concolors.RED_BACKGROUND+concolors.BANANA_YELLOW+" *Invalid Entry* \n"+concolors.RESET+"\t");
		System.out.println(e);
	}
	}
		void services(long acc_no,String cus_name,long contact_number,float balance,Date joined_date) {
			try {
			Statement ss=(Statement)con.createStatement();
			System.out.println("\n\n\t\t\t\t"+"    "+concolors.BOXING+concolors.LIGHT_PINK_BACKGROUND+concolors.RED_ITALIC+" 1.Requests "+concolors.RESET+"\t\t "+concolors.BOXING+concolors.LIGHT_PINK_BACKGROUND+concolors.RED_ITALIC+" 2.status "+concolors.RESET);
			System.out.print("\n\t"+concolors.FOREST_GREEN+"Enter the Number to select the option : "+concolors.RESET);
			int option=s.nextInt();
			if(option==1) {
				System.out.println("\n\n\t\t\t"+concolors.BOXING+concolors.DARK_BLUE_BACKGROUND+concolors.BANANA_YELLOW+" 1.To change contact number "+concolors.RESET+"\t\t"+concolors.BOXING+concolors.DARK_BLUE_BACKGROUND+concolors.BANANA_YELLOW+" 2.To change holder's Name "+concolors.RESET);
				System.out.print("\n\t"+concolors.LIGHT_PINK+"Enter the Number to select the option : "+concolors.RESET);		
				int c_option=s.nextInt();
				if(c_option==1) {
					System.out.print("\n\n\t\t\t"+"  "+concolors.BOXING+concolors.CYAN_BACKGROUND_BRIGHT+concolors.BLACK_BOLD+"    Enter the new contact number   "+concolors.RESET+"\t:\t");
					long new_contact_number=s.nextLong(10);
					if(new_contact_number!=contact_number) {
						ss.executeUpdate("insert into request(acc_no,request,new_value) values("+Long.toString(acc_no)+",'requested to change contact number',"+Long.toString(new_contact_number)+")");	
						System.out.println("\n\n\t\t\t\t\t"+concolors.BLUE_ITALIC+"     Request Applied   \n"+concolors.RESET);
						sub_modules(acc_no, cus_name, balance, contact_number, joined_date);
					}
					else {
						System.out.println("\n\n\t\t\t\t"+concolors.RED_ITALIC+"   Contact number already exists   "+concolors.RESET);
						services(acc_no,cus_name,contact_number, balance, joined_date);
					}
				}
				else if(c_option==2) {
					System.out.print("\n\n\t\t\t"+"  "+concolors.BOXING+concolors.CYAN_BACKGROUND_BRIGHT+concolors.BLACK_BOLD+"  Enter the new name to change   "+concolors.RESET+"\t:\t");
					switch(chname().compareTo(cus_name)) {
					case 0:
						System.out.println("\n\n\t\t\t\t\t"+concolors.RED_ITALIC+"     Existing name    \n"+concolors.RESET);
						services(acc_no,cus_name,contact_number, balance, joined_date);
						break;
					default:
						ss.executeUpdate("insert into request(acc_no,request,new_value) values("+Long.toString(acc_no)+",'requested to change name',"+"'"+chname()+"')");
						System.out.println("\n\n\t\t\t\t\t"+concolors.BLUE_ITALIC+"     Request Applied   \n"+concolors.RESET);
						sub_modules(acc_no, cus_name, balance, contact_number, joined_date);
						break;
					}
				}
				else
					System.out.println("\n\t\t\t\t\t\t"+concolors.YELLOW+"  *Enter 1 or 2 only*"+concolors.RESET);
					services(acc_no, cus_name, contact_number, balance, joined_date);
				}
			else if(option==2) {
				ResultSet rs=ss.executeQuery("select * from request where acc_no="+acc_no+" order by request_id desc");
				if(rs.first()) {
				rs.beforeFirst();
				System.out.println("\n\t----------------------------------------------------------------------------------------------------------------");
				System.out.println("\t| Request id |                           Request                          |   status   |         Date          |");
				System.out.println("\t----------------------------------------------------------------------------------------------------------------");
				while(rs.next()) {
				System.out.printf("\t|%12s|%-37s : %-20s| %-11s|%23s|%n",rs.getString(1),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
				}
				System.out.println("\t----------------------------------------------------------------------------------------------------------------\n");
				sub_modules(acc_no, cus_name, balance, contact_number, joined_date);
				}
				
				else {
					System.out.println("\n\t\t\t\t\t\t"+concolors.RED_ITALIC+"      *No requests*   \n"+concolors.RESET);
					sub_modules(acc_no, cus_name, balance, contact_number, joined_date);
				}
				}
			else {
				System.out.println("\n\t\t\t\t\t\t"+concolors.YELLOW+"  *Enter 1 or 2 only*"+concolors.RESET);
				services(acc_no, cus_name, contact_number, balance, joined_date);
			}
		}catch(Exception e) {
			System.out.println("\n\t\t\t\t\t\t"+"    "+concolors.RED_BACKGROUND+concolors.BANANA_YELLOW+" *Invalid Entry* \n"+concolors.RESET+"\t");
			System.out.println(e);
		}
	}
String chname() {
	String c_name=s.nextLine();
	return c_name;
}

}




