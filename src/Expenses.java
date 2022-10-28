import java.util.Date;
import java.util.Objects;

public class Expenses {
    private int expense_id;
    private String expense_category;
    private String expense_description;
    private int expense_amount;
    private String expense_date;

    public Expenses(int expense_id, String expense_category, String expense_description, int expense_amount, String expense_date) {
        this.expense_id = expense_id;
        this.expense_category = expense_category;
        this.expense_description = expense_description;
        this.expense_amount = expense_amount;
        this.expense_date = expense_date;
    }

    public int getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(int expense_id) {
        this.expense_id = expense_id;
    }

    public String getExpense_category() {
        return expense_category;
    }

    public void setExpense_category(String expense_category) {
        this.expense_category = expense_category;
    }

    public String getExpense_description() {
        return expense_description;
    }

    public void setExpense_description(String expense_description) {
        this.expense_description = expense_description;
    }

    public int getExpense_amount() {
        return expense_amount;
    }

    public void setExpense_amount(int expense_amount) {
        this.expense_amount = expense_amount;
    }

    public String getExpense_date() {
        return expense_date;
    }

    public void setExpense_date(String expense_date) {
        this.expense_date = expense_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expenses expenses = (Expenses) o;
        return expense_id == expenses.expense_id && expense_amount == expenses.expense_amount && Objects.equals(expense_category, expenses.expense_category) && Objects.equals(expense_description, expenses.expense_description) && expense_date.equals(expenses.expense_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expense_id, expense_category, expense_description, expense_amount, expense_date);
    }

    @Override
    public String toString() {
        return expense_id + ":" + expense_category + ":" + expense_description + ":" + expense_amount + ":" + expense_date + ",";
    }
}
