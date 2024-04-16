import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Game {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String englishAlphabetAndNumbers = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";
    private static final List<String> dictionary = getDictionary();
    private static final Set<String> allInputSymbols = new HashSet<>();

    private static int win = 0;
    private static int lose = 0;


    public static void rulesGame() {
        Message.getRules();
    }

    public static void selectMode() {
        while (true) {
            System.out.println("Хотите начать новую игру ? Д - да, Н - нет ");
            switch (scanner.nextLine()) {
                case "Д", "д" -> startGame();
                case "Н", "н" -> System.exit(0);
                default -> System.out.println("Неправильная команда! Попробуйте снова!");
            }
        }
    }


    private static void startGame() {
        String mysteryWord = getMysteryWord();
        StringBuilder guessesWord = new StringBuilder("_".repeat(mysteryWord.length()));
        int indexOfSymbol;
        int countOfMistake = 0;
        System.out.println("Слово загаданно\nВведите букву :");
        boolean isTrue = false;
        while (countOfMistake < 7 && !(isTrue)) {
            String symbol = scanner.nextLine().toLowerCase();
            if (!(incorrectInput(symbol))) {
                System.out.println("Некорректный ввод! Попробуйте снова!");
                continue;
            }
            indexOfSymbol = mysteryWord.indexOf(symbol);
            if (allInputSymbols.contains(symbol)) {
                System.out.println("Вы уже использовали такую букву,попробуйте еще раз");
                continue;
            }
            if (indexOfSymbol == -1) {
                countOfMistake++;
                System.out.println("Тут такой буквы нет");
                print(guessesWord, countOfMistake, symbol);
                continue;
            }
            while (indexOfSymbol >= 0) {
                guessesWord.setCharAt(indexOfSymbol, symbol.charAt(0));
                indexOfSymbol = mysteryWord.indexOf(symbol, indexOfSymbol + 1);
            }
            isTrue = mysteryWord.contentEquals(guessesWord);
            print(guessesWord, countOfMistake, symbol);
        }
        allInputSymbols.clear();
        if (isTrue) {
            System.out.println("Вы выиграли,поздравляем! У вас хорошо получается\nПопробуйте еще раз ");
            win++;
        } else {
            lose++;
            System.out.println("Вы проиграли :(\nЗагаданное слово было: " + mysteryWord + "\nПопробуйте еще раз ");
        }
        System.out.println("Побед: " + win + "\nПоражений: " + lose);
    }

    private static List<String> getDictionary() {
        List<String> dictionary = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src\\dictionary.txt"))) {
            String words = bufferedReader.readLine();
            while (words != null) {
                dictionary.add(words);
                words = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.err.println("Dictionary not found");
        }
        return dictionary;
    }

    private static String getMysteryWord() {
        return dictionary.get(new Random().nextInt(dictionary.size()));
    }

    private static void print(StringBuilder answer, int countOfMistake, String symbol) {
        allInputSymbols.add(symbol);
        System.out.println("Введенные буквы: " + allInputSymbols);
        System.out.println("Загаданное слово: " + answer);
        System.out.println("Попыток осталось: " + (7 - countOfMistake));
    }

    private static boolean incorrectInput(String symbol) {
        if (symbol.isEmpty()) {
            return false;
        }
        int index = englishAlphabetAndNumbers.indexOf(symbol.charAt(0));
        return index < 0 && symbol.length() == 1;
    }
}
