import java.util.ArrayList;
import java.util.Scanner;

// Classe abstrata representando uma moeda genérica
abstract class Coin {
    protected double value;

    // Construtor que inicializa o valor da moeda
    public Coin(double value) {
        this.value = value;
    }

    // Método abstrato para converter a moeda para Reais
    public abstract double convertToReal();

    // Método para exibir a moeda como string
    @Override
    public String toString() {
        return String.format("Coin: %.2f", value);
    }
}

// Classe Real representando a moeda Real (R$)
class Real extends Coin {
    public Real(double value) {
        super(value);
    }

    // A conversão para Real retorna o próprio valor, já está em Reais
    @Override
    public double convertToReal() {
        return value; 
    }

    // Exibe a moeda Real como R$
    @Override
    public String toString() {
        return String.format("Real: R$ %.2f", value);
    }
}

// Classe Dollar representando a moeda Dólar (USD)
class Dollar extends Coin {
    public Dollar(double value) {
        super(value);
    }

    // A conversão de Dólar para Real, com a taxa de 1 Dólar = 6.09 Reais
    @Override
    public double convertToReal() {
        return value * 6.09;
    }

    // Exibe a moeda Dólar como $
    @Override
    public String toString() {
        return String.format("Dollar: $ %.2f", value);
    }
}

// Classe Euro representando a moeda Euro (EUR)
class Euro extends Coin {
    public Euro(double value) {
        super(value);
    }

    // A conversão de Euro para Real, com a taxa de 1 Euro = 6.44 Reais
    @Override
    public double convertToReal() {
        return value * 6.44;
    }

    // Exibe a moeda Euro como €
    @Override
    public String toString() {
        return String.format("Euro: € %.2f", value);
    }
}

// Classe AquilesBank gerencia uma coleção de moedas
class AquilesBank {
    private ArrayList<Coin> coins; // Lista de moedas no banco

    // Construtor que inicializa a lista de moedas
    public AquilesBank() {
        coins = new ArrayList<>();
    }

    // Adiciona uma moeda ao banco
    public void addCoin(Coin coin) {
        coins.add(coin);
    }

    // Remove uma moeda do banco
    public boolean removeCoin(Coin coin) {
        return coins.remove(coin); // Retorna true se a moeda foi removida
    }

    // Exibe todas as moedas armazenadas no banco
    public void displayCoins() {
        if (coins.isEmpty()) {
            System.out.println("The bank is empty!"); // Se não houver moedas
        } else {
            coins.forEach(System.out::println); // Exibe cada moeda na lista
        }
    }

    // Calcula o total em Reais somando as conversões de todas as moedas
    public double calculateTotalInReais() {
        return coins.stream().mapToDouble(Coin::convertToReal).sum(); // Usa stream para somar os valores
    }
}

public class AquilesBankApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AquilesBank aquilesBank = new AquilesBank(); // Cria o banco
        int option; // Variável para armazenar a opção do usuário

        // Loop que exibe o menu até o usuário escolher a opção de sair
        do {
            System.out.println("\n--- Aquiles Bank Menu ---");
            System.out.println("1. Add a coin");
            System.out.println("2. Remove a coin");
            System.out.println("3. List coins");
            System.out.println("4. Calculate total in Reais");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            option = scanner.nextInt(); // Lê a opção do usuário

            switch (option) {
                case 1:
                    addCoinToBank(scanner, aquilesBank); // Chama o método para adicionar moeda
                    break;
                case 2:
                    removeCoinFromBank(scanner, aquilesBank); // Chama o método para remover moeda
                    break;
                case 3:
                    aquilesBank.displayCoins(); // Exibe as moedas no banco
                    break;
                case 4:
                    // Exibe o total de moedas convertido para reais
                    System.out.printf("Total in Reais: R$ %.2f%n", aquilesBank.calculateTotalInReais());
                    break;
                case 0:
                    System.out.println("Exiting the system..."); // Mensagem de saída
                    break;
                default:
                    System.out.println("Invalid option!"); // Caso a opção não seja válida
            }
        } while (option != 0); // O menu continua até o usuário digitar '0' para sair

        scanner.close(); // Fecha o scanner
    }

    // Método para adicionar uma moeda ao banco
    private static void addCoinToBank(Scanner scanner, AquilesBank aquilesBank) {
        System.out.print("Choose the coin (1-Real, 2-Dollar, 3-Euro): ");
        int type = scanner.nextInt(); // Lê o tipo da moeda
        System.out.print("Enter the coin value: ");
        double value = scanner.nextDouble(); // Lê o valor da moeda

        // Cria a moeda com base no tipo escolhido
        Coin coin = createCoin(type, value);
        if (coin != null) {
            aquilesBank.addCoin(coin); // Adiciona a moeda ao banco
            System.out.println("Coin added!");
        } else {
            System.out.println("Invalid coin type!"); // Caso o tipo da moeda seja inválido
        }
    }

    // Método para remover uma moeda do banco
    private static void removeCoinFromBank(Scanner scanner, AquilesBank aquilesBank) {
        System.out.print("Enter the coin value to remove: ");
        double value = scanner.nextDouble(); // Lê o valor da moeda a ser removida
        System.out.print("Enter the coin type (1-Real, 2-Dollar, 3-Euro): ");
        int type = scanner.nextInt(); // Lê o tipo da moeda

        // Cria a moeda a ser removida
        Coin coin = createCoin(type, value);
        if (coin != null && aquilesBank.removeCoin(coin)) {
            System.out.println("Coin removed!"); // Se a moeda foi removida com sucesso
        } else {
            System.out.println("Coin not found!"); // Caso a moeda não tenha sido encontrada
        }
    }

    // Método para criar uma moeda com base no tipo e valor fornecido
    private static Coin createCoin(int type, double value) {
        return switch (type) {
            case 1 -> new Real(value); // Se for Real
            case 2 -> new Dollar(value); // Se for Dólar
            case 3 -> new Euro(value); // Se for Euro
            default -> null; // Caso inválido
        };
    }
}