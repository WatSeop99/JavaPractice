package modules;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

public class Server {
    private List<Integer> totalCount;
    private Boolean isWin;
    private List<Integer> answer;

    public Server() {
        totalCount = new ArrayList<>(Arrays.asList(0, 0, 0)); // strike, ball, out
        isWin = false;

        Random rand = new Random(System.currentTimeMillis());
        Integer randNum = rand.nextInt(100, 1000);
        Integer divisor = 100;

        answer = new ArrayList<>();
        while (randNum > 0) {
            Integer input = randNum / divisor;
            answer.add(input);

            randNum %= divisor;
            divisor /= 10;
        }
    }

    public List<Integer> getTotalCount() {
        return totalCount;
    }

    public void regenerateAnswer() {
        Random rand = new Random(System.currentTimeMillis());
        Integer randNum = rand.nextInt(100, 1000);
        answer = new ArrayList<>(Arrays.asList(randNum / 100, randNum % 100, randNum % 10));
    }

    public boolean checkAnswer(int clientAnswer) {
        checkStrike(clientAnswer);
        checkBall(clientAnswer);

        if (totalCount.get(0).equals(3)) {
            checkOut();
            return true;
        }

        return false;
    }

    public void checkStrike(int clientAnswer) {
        int divisor = 100;

        for (int i = 0; i < 3; i++) {
            int val = clientAnswer / divisor;
            int ans = answer.get(i);

            if (ans == val) {
                totalCount.set(0, totalCount.get(0) + 1);
            }

            clientAnswer %= divisor;
            divisor /= 10;
        }
    }

    public void checkBall(int clientAnswer) {
        int divisor = 100;

        for (int i = 0; i < 3; i++) {
            int val = clientAnswer / divisor;

            for (int j = 0; j < 3; j++) {
                int ans = answer.get(j);
                if (ans == val && i != j) {
                    totalCount.set(1, totalCount.get(1) + 1);
                }
            }

            clientAnswer %= divisor;
            divisor /= 10;
        }
    }

    public void checkOut() {
        if (totalCount.get(0).equals(3)) {
            totalCount.set(2, totalCount.get(2) + 1);
            clearCount(6);
        }
    }

    public void clearCount(int count) {
        byte strikeClear = (byte) (count & 4);
        byte ballClear = (byte) (count & 2);
        byte outClear = (byte) (count & 1);

        if (strikeClear == 4) {
            totalCount.set(0, 0);
        }
        if (ballClear == 2) {
            totalCount.set(1, 0);
        }
        if (outClear == 1) {
            totalCount.set(2, 0);
        }
    }
}
