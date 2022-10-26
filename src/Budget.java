public class Budget {
    private int budget_amount;
    private int month;

    public Budget(int budget_amount, int month) {
        this.budget_amount = budget_amount;
        this.month = month;
    }

    public int getBudget_amount() {
        return budget_amount;
    }

    public void setBudget_amount(int budget_amount) {
        this.budget_amount = budget_amount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return month + ":" + budget_amount + ",";
    }
}
