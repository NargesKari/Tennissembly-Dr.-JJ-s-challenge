package controller;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import view.PreGameMenu;
import java.util.*;
public class PregameMenuController {
    public ImageView racket1;
    private PreGameMenu viewPage;

    public void setViewPage(PreGameMenu viewPage) {
        this.viewPage = viewPage;
    }

    public void startGame(MouseEvent mouseEvent) {
        viewPage.startNewGame();
    }
}
