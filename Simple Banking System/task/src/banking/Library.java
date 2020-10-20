package banking;

public class Library {

    private final String database;
    private int nextCardId = 1;

    public Library (String database) {
        this.database = database;
    }

    public void assertTableCard() {
        DBConnector.execute(database, "CREATE TABLE IF NOT EXISTS card (" +
            "id INTEGER, number TEXT, pin TEXT, balance INTEGER DEFAULT 0" +
        ")", null);
    }

    public void createCard(long num, int pin) {
        String output = String.format(
            "INSERT INTO card VALUES (%d, '%d', '%d', %d)",
            nextCardId++, num, pin, 0
        );
        System.out.println(output);
        DBConnector.execute(database, output, null);
    }

    public Card getCard(long number) {
        String output = String.format(
            "SELECT * FROM card WHERE number = '%d'",
            number
        );
        System.out.println(output);

        Card card = new Card();
        card.setId(-1);

        DBConnector.execute(database, output, result -> {
            if (result.next()) {
                card.setPin(result.getInt("pin"));
                card.setNumber(result.getLong("number"));
                card.setId(result.getInt("id"));
                card.setBalance(result.getInt("balance"));
            }
        });
        return card;
    }

    public void updateCardBalance(long number, int amount) {
        String output = String.format(
            "UPDATE card SET balance = balance + %d WHERE number = '%d'",
            amount, number
        );
        System.out.println(output);
        DBConnector.execute(database, output, null);
    }

    public void deleteCard(long number) {
        String output = String.format(
            "DELETE FROM card WHERE number = '%d'",
            number
        );
        System.out.println(output);
        DBConnector.execute(database, output, null);
    }

}
