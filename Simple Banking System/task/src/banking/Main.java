package banking;

import java.util.Scanner;

public class Main {

    private static Library lib;

    private static Scanner scanner;

    public static void bankingSystem() {
        mainLoop:
        while (true) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            int input = scanner.nextInt();
            if (input == 1) {
                createAccount();
            } else if (input == 2) {
                System.out.println("Enter your card number:");
                long number = scanner.nextLong();
                System.out.println("Enter your PIN:");
                int pin = scanner.nextInt();
                Card card = lib.getCard(number);

                if (card.getPin() != pin) {
                    System.out.println("Wrong card number or PIN!");
                } else {
                    System.out.println("You have successfully logged in!");
                    while (true) {
                        System.out.println("1. Balance");
                        System.out.println("2. Add income");
                        System.out.println("3. Do transfer");
                        System.out.println("4. Close account");
                        System.out.println("5. Log out");
                        System.out.println("0. Exit");
                        int input2 = scanner.nextInt();

                        if (input2 == 1) {
                            balance(card);
                        } if (input2 == 2) {
                            card = addIncome(card);
                        } if (input2 == 3) {
                            card = doTransfer(card);
                        } else if (input2 == 4) {
                            closeAccount(card);
                            continue mainLoop;
                        } else if (input2 == 5) {
                            logOut();
                            continue mainLoop;
                        } else if (input2 == 0) {
                            exit();
                            break mainLoop;
                        }
                    }
                }
            } else if (input == 0) {
                exit();
                break;
            }
        }
    }

    public static void createAccount () {
        Card card = new Card(false);
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(card.getNumber());
        System.out.println("Your card PIN:");
        System.out.println(card.getPin());
        lib.createCard(card.getNumber(), card.getPin());
    }

    public static void balance(Card card) {
        System.out.println("Balance: " + card.getBalance());
    }

    public static Card addIncome(Card card) {
        System.out.println("Enter income:");
        int income = scanner.nextInt();
        lib.updateCardBalance(card.getNumber(), income);
        card = lib.getCard(card.getNumber());
        System.out.println("Income was added!");
        return card;
    }

    public static Card doTransfer(Card card) {
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        long cardNumber = scanner.nextLong();
        Card to = lib.getCard(cardNumber);
        if (card.getNumber() == cardNumber) {
            System.out.println("You can't transfer money to the same account!");
            return card;
        } else if (lib.getCard(cardNumber).checkCard(cardNumber)){
            System.out.println("Probably you made mistake in the card number. Please try again");
            return card;
        } else if (to.getId() == -1) {
            System.out.println("Such a card does not exist.");
            return card;
        }
        System.out.println("Enter how much money you want to transfer:");
        int money = scanner.nextInt();
        if (card.getBalance() < money) {
            System.out.println("Not enough money!");
        } else {
            lib.updateCardBalance(card.getNumber(), -money);
            lib.updateCardBalance(to.getNumber(), money);
            card = lib.getCard(card.getNumber());
            System.out.println("Success!");
        }

        return  card;
    }


    public static void closeAccount(Card card) {
        System.out.println("The account has been closed!");
        lib.deleteCard(card.getNumber());
    }

    public static void logOut() {
        System.out.println("You have successfully logged out!");
    }

    public static void exit() {
        System.out.println("Bye!");
    }
    
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        lib = new Library(args[1]);
        lib.assertTableCard();

        bankingSystem();
    }

}