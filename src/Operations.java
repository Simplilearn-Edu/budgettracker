import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Operations {

    DecimalFormat df = new DecimalFormat("#,##0.00");
    private String file_credentials = "src/credentials.txt";
    private String file_monthly_budget = "src/budget.txt";
    private String file_expenses = "src/expenses.txt";

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

    private Budget getMonthlyBudget() {
        File f = new File(file_monthly_budget);
        Scanner sc;
        Budget b = new Budget();
        try {
            sc = new Scanner(f);
            if (sc.hasNext()) {
                String[] line = sc.nextLine().split(",");
                b = new Budget(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return b;
    }


    public boolean login() {
        boolean status = true;
        return status;
    }

    public void recordExpense() {
        Scanner sc = new Scanner(System.in);
        System.out.println("CHOOSE THE EXPENSE CATEGORY - ");
        String[] categories = {"CLOTHES", "ELECTRICITY BILL", "EXAM FEES", "FOOD", "FUEL", "HOUSE RENT", "TRAVELLING", "OTHER",};
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        int category = sc.nextInt() - 1;
        String expense_category = categories[category];
        String expense_description = categories[category];
        if (category > 6) {
            System.out.print("ENTER THE EXPENSE DESCRIPTION - ");
            sc.nextLine();
            expense_description = sc.nextLine();
        }
        System.out.print("ENTER EXPENSE AMOUNT - ");
        int amount = sc.nextInt();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String expense_date = simpleDateFormat.format(date);

        Budget b = getMonthlyBudget();
        List<Expenses> expensesList = getExpensesList();
        int monthly_budget = b.getBudget_amount();
        int current_budget = b.getCurrent_budget();
        int spending = b.getSpending();
        int total_spending = spending + amount;

        if (current_budget >= amount && total_spending <= monthly_budget) {
            Expenses expenses = new Expenses(expensesList.size() + 1, expense_category, expense_description, amount, expense_date);
            b.setSpending(total_spending);
            b.setCurrent_budget(current_budget - amount);
            try {
                FileWriter fw1 = new FileWriter(file_expenses, true);
                fw1.write(expenses.toString());
                fw1.close();
                FileWriter fw2 = new FileWriter(file_monthly_budget);
                fw2.write(b.toString());
                fw2.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("ERROR : BUDGET LIMIT EXCEEDED.");
        }

    }

    private List<Expenses> getExpensesList() {
        List<Expenses> expenses = new ArrayList<>();
        File file = new File(file_expenses);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (sc.hasNext()) {
            String[] expense = sc.nextLine().split(",");
            for (int i = 0; i < expense.length; i++) {
                String[] content = expense[i].split(":");
                expenses.add(new Expenses(
                        Integer.parseInt(content[0]),
                        content[1],
                        content[2],
                        Integer.parseInt(content[3]),
                        content[4]));
            }
        }
        return expenses;
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

    public void setMonthlyBudget() {
        Validator validator = new Validator();
        Scanner sc = new Scanner(System.in);
        Budget b = getMonthlyBudget();
        String budgetAmount = "";
        if (b.getBudget_amount() > 0) {
            System.out.print("MONTHLY BUDGET IS ALREADY SET, DO YOU WANT TO UPDATE IT?(Y : YES | N : NO)");
            char choice = sc.next().charAt(0);
            switch (choice) {
                case 'Y':
                    System.out.print("ENTER THE MONTHLY BUDGET AMOUNT FOR EVERY MONTH - ");
                    boolean isNotValid;
                    do {
                        isNotValid = false;
                        budgetAmount = sc.next();
                        if (!validator.isValidNumber(budgetAmount)) {
                            isNotValid = true;
                            System.out.println("ERROR : INVALID AMOUNT.");
                        }
                    } while (isNotValid);
                    updateBudget(Integer.parseInt(budgetAmount), b);
                    break;
                case 'N':
                    System.out.println("OPERATION CANCELLED.");
                    break;
            }
        } else {
            System.out.print("ENTER THE MONTHLY BUDGET AMOUNT FOR EVERY MONTH - ");
            boolean isNotValid;
            do {
                isNotValid = false;
                budgetAmount = sc.next();
                if (!validator.isValidNumber(budgetAmount)) {
                    isNotValid = true;
                    System.out.println("ERROR : INVALID AMOUNT.");
                }
            } while (isNotValid);
            updateBudget(Integer.parseInt(budgetAmount), b);
        }
    }

    private void updateBudget(int amount, Budget b) {
        int spending = b.getSpending();
        b.setBudget_amount(amount);
        b.setCurrent_budget(amount - spending);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file_monthly_budget);
            fileWriter.write(b.toString());
            fileWriter.close();
            System.out.println("YOUR MONTHLY BUDGET HAS BEEN UPDATED SUCCESSFULLY.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
		
		
