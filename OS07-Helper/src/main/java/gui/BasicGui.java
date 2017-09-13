package gui;

import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author ode
 */
public class BasicGui extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        initUI(stage);
        stage.show();
    }

    private void initUI(Stage stage) {
        stage.setTitle("OS07-Helper");
        stage.setAlwaysOnTop(true);
        setPosition(stage);
        stage.setResizable(false);
        stage.toFront();

        GridPane grid = new GridPane();
        grid.setStyle("-fx-border-color: red");
        addComponents(grid);

        Scene scene = new Scene(grid);
        stage.setScene(scene);
    }

    private void setPosition(Stage stage) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to the lower right corner of the visible bounds of the main screen
        stage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 200);
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(200);
    }

    private void addComponents(GridPane grid) {
        grid.setPadding(new Insets(20, 10, 15, 10));
        grid.setHgap(10);
        grid.setVgap(10);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(column1, column2);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(100);
//        RowConstraints row2 = new RowConstraints();
//        column2.setPercentWidth(50);
        grid.getRowConstraints().addAll(row1);

        grid.add(setLog(), 0, 0);
        grid.add(setRunSettingsPane(), 1, 0);
    }

    private Label setLog() {
        Label log = new Label("Log log log log log");
        log.setStyle("-fx-border-color: green");
        log.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return log;
    }

    private Node setRunSettingsPane() {
        VBox runSettings = new VBox(10);
        runSettings.setStyle("-fx-border-color: purple");
        runSettings.setAlignment(Pos.TOP_CENTER);

        HBox topBox = new HBox(10);
        topBox.setStyle("-fx-border-color: purple");
        Label title = new Label("Set script:");
        topBox.getChildren().add(title);

        HBox middleBox1 = new HBox(10);
        middleBox1.setStyle("-fx-border-color: purple");
        ComboBox cb = new ComboBox(FXCollections.observableArrayList(
                "AFK", "NMZ", "Fletch (longbow)"));
        Button runButton = new Button("Run!");
        HBox.setHgrow(cb, Priority.ALWAYS);
        VBox.setVgrow(middleBox1, Priority.ALWAYS);
        middleBox1.setAlignment(Pos.CENTER);
        middleBox1.getChildren().add(cb);
        middleBox1.getChildren().add(runButton);

        HBox bottombox = new HBox(10);
        bottombox.setStyle("-fx-border-color: purple");
//        bottombox.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        bottombox.setAlignment(Pos.CENTER);
        ProgressBar pb = new ProgressBar(0.6);
        HBox.setHgrow(pb, Priority.ALWAYS);
//        ProgressIndicator pi = new ProgressIndicator(0.6);
        bottombox.getChildren().addAll(pb);

        runSettings.getChildren().addAll(topBox, middleBox1, bottombox);
        return runSettings;
    }

}
