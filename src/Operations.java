import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Operations {

    private final String file_credentials = "src/credentials.txt";
    private final String file_monthly_budget = "src/budget.txt";
    private final String file_expenses = "src/expenses.txt";

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
        boolean status = false;
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("USERNAME - ");
            String username = sc.next();
            System.out.print("PASSWORD - ");
            String password = sc.next();
            File file = new File(file_credentials);
            Scanner reader = new Scanner(file);
            if (reader.hasNext()) {
                String[] credentials = reader.nextLine().split(",");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    status = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
                int i = 0;
                while (i < 3) {
                    i++;
                    System.out.print("ENTER YOUR PASSWORD - ");
                    String password = sc.next();
                    File file = new File(file_credentials);
                    Scanner reader = new Scanner(file);
                    if (reader.hasNext()) {
                        String[] credentials = reader.nextLine().split(",");
                        if (credentials[1].equals(password)) {
                            FileWriter fw1 = new FileWriter(file_expenses, true);
                            fw1.write(expenses.toString());
                            fw1.close();
                            FileWriter fw2 = new FileWriter(file_monthly_budget);
                            fw2.write(b.toString());
                            fw2.close();
                            System.out.println("EXPENSE RECORDED SUCCESSFULLY.");
                            break;
                        } else {
                            System.out.println((i != 3) ? "INCORRECT PASSWORD." : "YOU HAVE REACHED THE MAXIMUM LIMIT OF INCORRECT PASSWORD.");
                        }
                    }
                }
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
        Scanner sc;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (sc.hasNext()) {
            String[] expense = sc.nextLine().split(",");
            for (String s : expense) {
                String[] content = s.split(":");
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setMonthlyBudget() {
        Validator validator = new Validator();
        Scanner sc = new Scanner(System.in);
        Budget b = getMonthlyBudget();
        String budgetAmount;
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
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file_monthly_budget);
            fileWriter.write(b.toString());
            fileWriter.close();
            System.out.println("YOUR MONTHLY BUDGET HAS BEEN UPDATED SUCCESSFULLY.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getBudgetaryLogs() {
        System.out.println("1. DATE-WISE LOG");
        System.out.println("2. MONTH-WISE LOG");
        System.out.println("3. TOTAL BUDGET");
        System.out.println("4. DELETE BUDGETARY LOG");
        System.out.print("SELECT THE BUDGET LOG YOU WANT TO DISPLAY - ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.println("ENTER THE DATE IN DD-MM-YYYY FORMAT FOR WHICH YOU WANT TO DISPLAY THE BUDGETARY LOGS - ");
                String budgetDate = sc.next();
                displayLogs(getDateWiseLog(budgetDate));
                break;
            case 2:
                System.out.println("ENTER THE MONTH NUMBER BETWEEN 1 TO 12 FOR WHICH YOU WANT TO DISPLAY THE BUDGETARY LOGS - ");
                int month = sc.nextInt();
                displayLogs(getMonthWiseLog(month));
                break;
            case 3:
                getTotalLog();
                break;
            case 4:
                System.out.print("ENTER THE MONTH NUMBER BETWEEN 1 TO 12 FOR DELETING THE LOG - ");
                int m = sc.nextInt();
                deleteLog(m, getMonthWiseLog(m));
                break;
            default:
                System.out.println("NOT A VALID CHOICE.");
                break;
        }
    }

    private void displayLogs(List<Expenses> expensesList) {
        if (expensesList.size() > 0) {
            System.out.println("===================================================================================================");
            System.out.printf("%5s %17s %17s %17s %17s", "ID", "DATE", "CATEGORY", "AMOUNT", "DESCRIPTION");
            System.out.println();
            System.out.println("===================================================================================================");
            for (Expenses ex : expensesList) {
                System.out.format("%5s %17s %17s %17s %17s", ex.getExpense_id(), ex.getExpense_date(), ex.getExpense_category(), ex.getExpense_amount(), ex.getExpense_description());
                System.out.println();
            }
            System.out.println("===================================================================================================");
        } else {
            System.out.println("NO DATA PRESENT AT THIS MOMENT");
        }
    }

    private void deleteLog(int m, List<Expenses> monthWiseLog) {
        List<Expenses> expensesList = getExpensesList();
        if (monthWiseLog.size() > 0 && expensesList.size() > 0) {
            expensesList.removeAll(monthWiseLog);
            try {
                FileWriter fw = new FileWriter(file_expenses);
                for (Expenses ex : expensesList) {
                    fw.write(ex.toString());
                }
                fw.close();
                System.out.println("BUDGETARY LOG FOR MONTH " + Month.of(m) + "(" + m + ") " + "HAS BEEN DELETED SUCCESSFULLY.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("NO DATA PRESENT FOR MONTH - " + Month.of(m));
        }
    }

    private List<Expenses> getDateWiseLog(String budgetDate) {
        List<Expenses> e = getExpensesList();
        return e.stream()
                .filter(expenses -> budgetDate.equals(expenses.getExpense_date()))
                .collect(Collectors.toList());
    }

    private List<Expenses> getMonthWiseLog(int month) {
        List<Expenses> e = getExpensesList();

        return e.stream()
                .filter(expenses -> month == getMonthOfDate(expenses.getExpense_date()))
                .collect(Collectors.toList());
    }

    private int getMonthOfDate(String date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate currentDate
                = LocalDate.parse(date, df);
        return currentDate.getMonthValue();
    }

    private int getYearOfDate(String date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate currentDate
                = LocalDate.parse(date, df);
        return currentDate.getYear();
    }

    private void getTotalLog() {
        File file = new File(file_monthly_budget);
        Scanner sc;
        try {
            sc = new Scanner(file);
            String[] budgetData = sc.nextLine().split(",");
            System.out.println("+---------------------------------+");
            System.out.printf("%1s %10s %10s %5s\n", '|', "MONTHLY BUDGET :", budgetData[0], "|");
            System.out.printf("%1s %10s %10s %5s\n", '|', "CURRENT BUDGET :", budgetData[1], "|");
            System.out.printf("%1s %10s %10s %5s\n", '|', "TOTAL SPENDING :", budgetData[2], "|");
            System.out.println("+---------------------------------+");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void isNewMonthOrYear() {
        Date date = Date.from(Instant.now());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        int currentMonth = getMonthOfDate(sdf.format(date));
        int currentYear = getYearOfDate(sdf.format(date));
        File file = new File(file_monthly_budget);
        Scanner sc;
        try {
            sc = new Scanner(file);
            String[] budget = sc.nextLine().split(",");
            int budgetAmount = Integer.parseInt(budget[0]);
            int budgetMonth = Integer.parseInt(budget[3]);
            int budgetYear = Integer.parseInt(budget[4]);

            if (budgetMonth < currentMonth || budgetYear < currentYear) {
                resetBudget(new Budget(budgetAmount, budgetAmount, 0, currentMonth, currentYear));
                System.out.println("YOUR BUDGET HAS BEEN RESET TO " + budgetAmount + " FOR " + Month.of(currentMonth).toString() + "-" + currentYear + ".");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void resetBudget(Budget budget) {
        try {
            FileWriter fileWriter = new FileWriter(file_monthly_budget);
            fileWriter.write(budget.toString());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
		
		
