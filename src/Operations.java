import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Operations {

    DecimalFormat df = new DecimalFormat("#,##0.00");
    private String file_credentials = "src/credentials.txt";

/*    public void addBudget() {
        Scanner sc = new Scanner(System.in);
        System.out.println("ENTER YOUR BUDGET AMOUNT:");
        float amount = sc.nextFloat();
        System.out.println("ENTER DATE(DD/MM/YYYY):");
        String dateAsString = sc.nextLine();
        Date date = DateUtil.getDate(dateAsString);

        //Add Expense details in expense obj

        Expense exp = new Expense();
        exp.setAmount(amount);
        exp.setDate(date);
        repo.expList.add(Exp);
        System.out.println("Success:Expense Added");
    }

    private void onExpenseList() {
        System.out.println("Expense Listing...");
        List<Expense> expList = repo.expList;
        for (int i = 0; i < expList.size(); i++) {

        }
		
		*//*double expense;
		
		 double totalExpenses = 0.0;
		do {
			System.out.print("Enter an expense, or 0 to calculate balance:");
	        expense = sc.nextDouble();

	        totalExpenses += expense;

	    } while (expense >0);

	    Display the amount after expenses.
	    double balance = calculateAmountOverBudget(monthlyBudget, totalExpenses);
	    if (balance < 0) {
	        System.out.println("You are OVER budget by Rupees"
	                + Math.abs(balance));
	    } else if (balance > 0) {
	        System.out.println("You are UNDER budget by Rupees"
	                + (balance));
	    } else {
	        System.out.println("You spent the amount of:"+balance);
	    }
	}

	static double calculateAmountOverBudget(double monthlyBudget,
	        double totalExpenses) {
	    return monthlyBudget - totalExpenses;
	}*//*


    }*/

    public void setMonthlyBudget() {
    }

    public boolean login() {
        boolean status = true;
        return status;
    }

    public void markEntry() {
    }

    public void getBudgetaryLogs() {
    }

    public void changePassword() {
        try {

            Scanner sc = new Scanner(System.in);
            System.out.print("ENTER THE OLD PASSWORD - ");
            String old_password = sc.next();
            File file = new File(file_credentials);
            Scanner reader = new Scanner(file);
            if (reader.hasNext()) {
                String[] credentials = reader.nextLine().split(",");
                if (credentials[1].equals(old_password)) {
                    System.out.print("ENTER THE NEW PASSWORD - ");
                    String new_password = sc.next();
                    FileWriter fileWriter = new FileWriter(file_credentials);
                    String cred = credentials[0] + "," + new_password;
                    fileWriter.write(cred);
                    fileWriter.close();
                    System.out.println("YOUR PASSWORD HAS BEEN CHANGED SUCCESSFULLY.");
                } else {
                    System.out.println("PASSWORD DID NOT MATCH.");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
		
		
