import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                int numberOfTurns = 0;
                String route = generateRoute("RLRFR", 100);
                for (int j = 0; j<route.length(); j++) {
                    if (route.charAt(j) == 'R') numberOfTurns++;
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(numberOfTurns)) {
                        sizeToFreq.put(numberOfTurns, sizeToFreq.get(numberOfTurns) + 1);
                    } else {
                        sizeToFreq.put(numberOfTurns, 1);
                    }
                }
            }).start();
        }
        printMethod();
    }
    public static String generateRoute (String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i< length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void printMethod() {
        Map.Entry<Integer, Integer> maxEntry = sizeToFreq.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElse(null);
        System.out.println("Самое частое количество повторений " + maxEntry.getKey() + " (встретилось " + maxEntry.getValue() + " раз)");
        System.out.println("Другие параметры: ");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            System.out.println("- " + entry.getKey() + " (" +entry.getValue()+ " раз)");
        }
    }
}
