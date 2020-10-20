package banking;

public class Card {

    private long number;
    private int pin;
    private int id;
    private int balance;


    public Card (boolean isEmpty) {
        if (!isEmpty) {
            generateNumberAndPin();
        }
    }

    public Card () {
        this (true);
    }

    private void generateNumberAndPin () {
        String part1 = "400000";
        String part2 = String.valueOf(100_000_000 + (int) (Math.random() * 99_999_999));
        int last = runLuhn(String.join("", part1, part2));
        number = Long.parseLong(part1 + part2 + last);
        pin = 1000 + (int) (Math.random() * 999);
    }

    public static int runLuhn (String number) {
        if (number.length() >= 16) {
            number = number.substring(0, 16);
        }

        int [] all = new int[number.length()];
        int sum = 0;
        for (int i = 0; i < all.length; i++) {
            all[i] = number.charAt(i) - '0';
        }
        for (int i = 0; i < all.length; i = i + 2) {
            all[i] = all[i] * 2;
        }
        for (int i = 0; i < all.length; i++) {
            if (all[i] > 9) {
                all[i] = all[i] - 9;
            }
            sum = sum + all[i];
        }

        return 10 - (sum % 10) == 10 ? 0 : 10 - (sum % 10);
    }

    public boolean checkCard(long number) {
        int luhn = runLuhn(String.valueOf(number));
        int last = (int) (number % 10);
        return luhn == last;
    }

    public long getNumber() {
        return number;
    }

    public int getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    public int getId() {
        return id;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setId(int id) {
        this.id = id;
    }

}
