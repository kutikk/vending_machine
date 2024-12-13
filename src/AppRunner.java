import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private final CoinAcceptor coinAcceptor;

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        coinAcceptor = new CoinAcceptor(100);

    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {

                print("В автомате доступны:");
                showProducts(products);
                print("Монет на сумму: " + coinAcceptor.getAmount());
                UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
                allowProducts.addAll(getAllowedProducts().toArray());
                chooseAction(allowProducts);

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (coinAcceptor.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }

        }
        return allowProducts;
    }


    private void chooseAction(UniversalArray<Product> products) {

        showActions(products);
        print(" h - Выйти");
        print("a - Пополнить баланс");
        String action = fromConsole().substring(0, 1);
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    coinAcceptor.setAmount(coinAcceptor.getAmount() - products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
                    break;
                } else if ("h".equalsIgnoreCase(action)) {
                    isExit = true;
                    break;
                } else if ("a".equalsIgnoreCase(action)) {
                   addBalance(coinAcceptor);
                   break;

                }
                if (coinAcceptor.getAmount()<=0){
                    addBalance(coinAcceptor);
                }
            }
        } catch (IllegalArgumentException e) {
            if ("h".equalsIgnoreCase(action)) {
                isExit = true;
            } else {
                print("Недопустимая буква. Попрбуйте еще раз.");
                chooseAction(products);
                e.getMessage();
                e.printStackTrace();
            }
        }

    }




    private void addBalance(CoinAcceptor coinAcceptor) {
        System.out.println("Выберите способ пополнения баланса: 1 - Оплата картой \n2 - Оплата монетами");
        String chooseBalance = fromConsole();
        switch (chooseBalance) {
            case "1":
                System.out.print("Введите номер карты в формате - #### #### #### ####: ");
                String cardNum = fromConsole();
                System.out.println("Введите временный пароль: ");
                String tempPassword = fromConsole();
                CardReader card = new CardReader(cardNum, tempPassword);
                int newBalance = coinAcceptor.getAmount() + card.getCash();
                coinAcceptor.setAmount(newBalance);
                System.out.println("Карта баланс пополнен на " + card.getCash());
                break;
            case "2":
                System.out.println("Вводите монеты!");
                try {
                    Scanner sc = new Scanner(System.in);
                    int coin = sc.nextInt();
                    coinAcceptor.setAmount(coinAcceptor.getAmount() + coin);
                    System.out.println("Баланс пополнен на " + coin);
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка: введите целое число.");
                }
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
