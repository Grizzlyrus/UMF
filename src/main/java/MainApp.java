import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Кирилл on 26.11.2015.
 */
public class MainApp extends Application{
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("UMF");
        this.primaryStage.setMinHeight(500.0);
        this.primaryStage.setMinWidth(650.0);
        initRootLayout();
        showDataOverview();

    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDataOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("LineChart.fxml"));
            AnchorPane Chart = (AnchorPane) loader.load();

            Stage LineChartStage = new Stage();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(Chart);

            // Give the controller access to the main app.
            LineChartController controller = loader.getController();
            controller.setLineChartStage(LineChartStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        launch(args);
//        Solution sol = new Solution(30.0,0.6,2.0,10.0,0.004,5);
//
//        CalcMU calc = new CalcMU(30,2.0,30.0,10e-5);
//        calc.solution();
//        calc.printMu();
//        for(int n=0;n<15;n++) {
//            System.out.println(Math.tan(n * Math.PI / 2.0 + 10e-10));
//        }
    }
}
