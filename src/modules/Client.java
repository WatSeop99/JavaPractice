package modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
    public enum ReturnFlags {
        SUCCESS, FAIL, ERROR
    }

    private List<Integer> gameState;
    private Server server;

    private int myAnswer;

    public Client() {
        gameState = new ArrayList<>(Arrays.asList(0, 0, 0)); // win , rose, win rate
        server = new Server();
        myAnswer = -1;
    }

    /*
    public void printGameState() {
        System.out.println("Player state =>\n" +
                "win : " + gameState.get(0) + "\n" +
                "lose : " + gameState.get(1) + "\n" +
                "win rate : " + gameState.get(2) + "\n");
    }*/

    public boolean setMyAnswer(int value) {
        if ((value / 10000) == 1 || value <= 0) {
            return false;
        }

        myAnswer = value;
        return true;
    }

    public int getMyAnswer() {
        return myAnswer;
    }

    public List<Integer> getState() {
        return server.getTotalCount();
    }

    public List<Integer> getGameState() {
        return gameState;
    }

    public ReturnFlags playGame() {
        if (myAnswer == -1) {
            return ReturnFlags.ERROR;
        }

        if (server.checkAnswer(myAnswer)) {
            gameState.set(0, gameState.get(0) + 1);

            int winRate = gameState.get(0) / (gameState.get(0) + gameState.get(1));
            gameState.set(2, winRate);

            return ReturnFlags.SUCCESS;
        } else {
            return ReturnFlags.FAIL;
        }
    }

    public void clearState() {
        server.clearCount(6);
    }
}
