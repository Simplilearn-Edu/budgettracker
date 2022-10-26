public class Validator {
    public boolean isValidNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isValidPrice(String per_day_price) {
        try {
            double price = Double.parseDouble(per_day_price);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
