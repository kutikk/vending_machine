import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private final CoinAcceptor coinAcceptor;

    private static boolean isExit = false;
    private static boolean isAddBalance = false;

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
            }
        } catch (IllegalArgumentException e) {
            print("Недопустимая буква. Попрбуйте еще раз.");
            chooseAction(products);
        }

    }




    private void addBalance(CoinAcceptor coinAcceptor){
        System.out.println("Выберите способ пополнения баланса");
        String chooseBalance = fromConsole();
        switch (chooseBalance){
            case "1":
                System.out.print("Введите номер карты в формате - #### #### #### #### ");
                String cardNum = fromConsole();
                System.out.println();
                System.out.println("Введите временный пароль - ");
                String tempPassword = fromConsole();
                CardReader card = new CardReader(cardNum,tempPassword);
                int money =  coinAcceptor.getAmount()+card.getCash();
                coinAcceptor.setAmount(money);
                System.out.println("Карта баланс пополнен на "+card.getCash());
            case "2":
                Scanner sc = new Scanner(System.in);
                System.out.println("Вводите монеты!");
                int coin = sc.nextInt();
                coin = coinAcceptor.getAmount()+coin;
                coinAcceptor.setAmount(coin);
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
