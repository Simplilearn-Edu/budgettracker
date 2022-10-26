import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("+----------------------------------+");
        System.out.println("|  WELCOME TO BUDGET TRACKER APP  |");
        System.out.println("+----------------------------------+");

        System.out.println("PLEASE LOGIN TO CONTINUE - ");
        Operations o = new Operations();
        try {
            if (o.login()) {
                do {
                    System.out.println("1. SET MONTHLY BUDGET");
                    System.out.println("2. MAKE AN ENTRY");
                    System.out.println("3. BUDGETARY LOGS");
                    System.out.println("4. CHANGE PASSWORD");
                    System.out.println("5. EXIT");
                    try {
                        switch (sc.nextInt()) {
                            case 1:
                                o.setMonthlyBudget();
                                break;
                            case 2:
                                o.markEntry();
                                break;
                            case 3:
                                o.getBudgetaryLogs();
                                break;
                            case 4:
                                o.changePassword();
                                break;
                            case 5:
                                System.out.println("THANKS VISIT AGAIN.");
                                return;
                            default:
                                System.out.println("ERROR : INVALID CHOICE. PLEASE SELECT THE OPTION BETWEEN 1 TO 4.");
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR : INVALID INPUT.");
                        break;
                    }
                } while (true);
            } else {
                System.out.println("ERROR : LOGIN FAILURE! INVALID CREDENTIALS.");
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }
}