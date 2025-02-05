package controller;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import view.ResultPage;

public class ResultPageController {
    public Label time;
    public Label winner;
    public Label score1;
    public Label score2;
    public HBox scoreBox;
    ResultPage resultPage;

    public void back(MouseEvent mouseEvent) {
        resultPage.back();
    }

    public void setViewPage(ResultPage resultPage) {
        this.resultPage = resultPage;
        time.setText(resultPage.getTime());
        score1.setText(resultPage.getTopScore() + "");
        score2.setText(resultPage.getBottomScore() + "");
        applyResult();
    }

    private void applyResult() {
        switch (resultPage.getGameMode()) {
            case 1 -> {
                if (resultPage.getTopScore() > resultPage.getBottomScore())
                    winner.setText("Blue win!");
                else
                    winner.setText("Pink win!");
            }
            case 2 -> {
                if (resultPage.getTopScore() > resultPage.getBottomScore())
                    winner.setText("You lost :(");
                else
                    winner.setText("You win :)");
            }
            case 3 -> {
                winner.setText("You spent " + time.getText() + " resisting " + score1.getText() + " mistakes :)");
                scoreBox.getChildren().removeAll(scoreBox.getChildren());
            }
        }
    }
}
