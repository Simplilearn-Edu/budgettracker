public class Budget {
    private int budget_amount;
    private int current_budget;
    private int spending;
    private int budget_month;
    private int budget_year;

    public Budget(int budget_amount, int current_budget, int spending) {
        this.budget_amount = budget_amount;
        this.current_budget = current_budget;
        this.spending = spending;
    }

    public Budget(int budget_amount, int current_budget, int spending, int budget_month, int budget_year) {
        this.budget_amount = budget_amount;
        this.current_budget = current_budget;
        this.spending = spending;
        this.budget_month = budget_month;
        this.budget_year = budget_year;
    }

    public Budget() {
        budget_amount = 0;
        current_budget = 0;
        spending = 0;
    }

    public int getBudget_amount() {
        return budget_amount;
    }

    public void setBudget_amount(int budget_amount) {
        this.budget_amount = budget_amount;
    }

    public int getCurrent_budget() {
        return current_budget;
    }

    public void setCurrent_budget(int current_budget) {
        this.current_budget = current_budget;
    }

    public int getSpending() {
        return spending;
    }

    public void setSpending(int spending) {
        this.spending = spending;
    }

    public int getBudget_month() {
        return budget_month;
    }

    public void setBudget_month(int budget_month) {
        this.budget_month = budget_month;
    }

    public int getBudget_year() {
        return budget_year;
    }

    public void setBudget_year(int budget_year) {
        this.budget_year = budget_year;
    }

    @Override
    public String toString() {
        return budget_amount + "," + current_budget + "," + spending + "," + budget_month + "," + budget_year;
    }
}
