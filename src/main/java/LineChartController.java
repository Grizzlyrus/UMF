import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;


/**
 * Created by Кирилл on 29.11.2015.
 */
public class LineChartController {
    private final static double l = 30.0;
    private final static double D = 0.6;
    private final static double H = 2.0;
//private final static double H = 0.00001;
    private final static double Beta = 0.004;
    private  double epsilon = 10e-5;
    private int n;
    private int k;
    private int i;

    private Stage LineChartStage;
    private Solution sol;
    private Progonka prog;

    private double currentT = -1.0;
    private double CurCalcNX = 0.0;
    private double CurCalcNT = 0.0;
    private ArrayList<Double> allTvalues = new ArrayList<>();

    final ToggleGroup group = new ToggleGroup();

    boolean selector = true;

    @FXML
    private Label Xlabel;

    @FXML
    private Label Tlabel;

    @FXML
    private Label Errorlabel;

    @FXML
    private Label Nlabel;

    @FXML
    private javafx.scene.control.Label curT;

    @FXML
    private Button Button1;

    @FXML
    private Button Button2;

    @FXML
    private Button CalcN;

    @FXML
    private TextField FieldX;

    @FXML
    private TextField FieldT;

    @FXML
    private TextField FieldError;

    @FXML
    private     NumberAxis x = new NumberAxis();

    @FXML
    private     NumberAxis y = new NumberAxis();

    @FXML
    private LineChart<Number,Number> Chart = new LineChart<>(x,y);

    @FXML
    private TextField field;

    @FXML
    private TextField FieldN;

    @FXML
    private TextField FieldI;

    @FXML
    private TextField FieldK;

    @FXML
    private Button SetIbutton;

    @FXML
    private Button SetKbutton;

    @FXML
    private Button SetNbutton;

    @FXML
    private RadioButton RadBut1;

    @FXML
    private RadioButton RadBut2;

    @FXML
    private Label Klabel;

    @FXML
    private Label Ilabel;

    public LineChartController(){
        Solution sol = new Solution(l,D,H,Beta);
//        sol.CalcN();
        this.sol = sol;

        Progonka prog = new Progonka(l,D,H,Beta);
        this.prog=prog;

    }

    @FXML
    private void initialize() {
        field.setDisable(true);
        Button2.setDisable(true);
        Button1.setDisable(true);
        Chart.setCreateSymbols(false);
        Chart.setTitle("U(x,t)");
        curT.setText("");
        RadBut1.setToggleGroup(group);
        RadBut2.setToggleGroup(group);
        RadBut1.setSelected(true);
        selector = true;

        SetIbutton.setDisable(true);
        SetKbutton.setDisable(true);
        CalcN.setDisable(false);
        SetNbutton.setDisable(false);
        FieldI.setDisable(true);
        FieldK.setDisable(true);
        FieldError.setDisable(false);
        FieldT.setDisable(false);
        FieldX.setDisable(false);
        FieldN.setDisable(false);

    }

    public void setLineChartStage(Stage LineChartStage) {
        this.LineChartStage = LineChartStage;
    }


    public void initGraph(double t, boolean sel) {
        if (sel) {
            curT.setText(((Double) t).toString());
            XYChart.Series series1 = new XYChart.Series();

            series1.setName("An. sol. t= " + t);
            ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();

            for (double i = 0; i < l; i += 0.05) {
                datas.add(new XYChart.Data(i, sol.U(i, t)));
            }

            series1.setData(datas);

            Chart.getData().add(series1);
        } else {
            prog.setCurT(t);
            curT.setText(((Double) t).toString());
            double x[] = prog.solveMatrix();
            XYChart.Series series2 = new XYChart.Series();

            series2.setName("Num. sol. t= " + t);
            ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();
            for(int i=0; i<x.length; i++){
                datas.add(new XYChart.Data(i*prog.getXstep(),x[i]));
            }
            series2.setData(datas);
            Chart.getData().add(series2);
        }
    }


    @FXML
    public void SetRadBut1Press(){
        selector = true;
        SetIbutton.setDisable(true);
        SetKbutton.setDisable(true);
        CalcN.setDisable(false);
        SetNbutton.setDisable(false);
        FieldI.setDisable(true);
        FieldK.setDisable(true);
        FieldError.setDisable(false);
        FieldT.setDisable(false);
        FieldX.setDisable(false);
        FieldN.setDisable(false);
        if(n!=0){
            field.setDisable(false);
            Button2.setDisable(false);
            Button1.setDisable(false);
        }else{
            field.setDisable(true);
            Button2.setDisable(true);
            Button1.setDisable(true);
        }
    }


    @FXML
    public void SetRadBut2Press(){
        selector = false;
        CalcN.setDisable(true);
        SetNbutton.setDisable(true);
        SetIbutton.setDisable(false);
        SetKbutton.setDisable(false);
        FieldI.setDisable(false);
        FieldK.setDisable(false);
        FieldError.setDisable(true);
        FieldT.setDisable(true);
        FieldX.setDisable(true);
        FieldN.setDisable(true);

        if(i!=0 && k!=0){
            field.setDisable(false);
            Button2.setDisable(false);
            Button1.setDisable(false);
        }else{
            field.setDisable(true);
            Button2.setDisable(true);
            Button1.setDisable(true);
        }

    }

    @FXML
    public void DrawButtonPress(){
        Double t;
        try {
            t = new Double(field.getCharacters().toString());
            if(!isFind(-10) && t>=0 && selector) {
                currentT = t;
                allTvalues.add(t);
                initGraph(t,selector);
            } else if (!isFind(-10) && t>=0 && !selector){
                initGraph(t,selector);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Inputting value is negative or already exists");
                alert.setContentText("Please, press OK and try again");
                alert.showAndWait();
                field.clear();
            }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Inputting value must be a number");
            alert.setContentText("Please, press OK and try again");
            alert.showAndWait();
            field.clear();
        }
    }

    private boolean isFind(double value){
        for (Double val:allTvalues){
            if (val == value){
                return true;
            }
        }
        return false;
    }

    @FXML
    public void ClearButtonPress(){
        allTvalues.clear();
        field.clear();
        curT.setText("");
        Chart.getData().clear();
    }


    @FXML
    public void CalcNButtonPress(){
        Double x,t, error;
        try {
            t = new Double(FieldT.getCharacters().toString());
            x = new Double(FieldX.getCharacters().toString());
            error = new Double(FieldError.getCharacters().toString());

            if(t>=0&&x>=0&&x<=l&&error>0 && error<0.2) {
                CurCalcNT = t;
                CurCalcNX = x;
                epsilon = error;
                sol.CalcN(x, t, error);
                Xlabel.setText("X: "+x);
                Tlabel.setText("T: "+t);
                Errorlabel.setText("Error: "+epsilon);
                Nlabel.setText("N: "+ sol.getN());
                n=sol.getN();

                field.setDisable(false);
                Button2.setDisable(false);
                Button1.setDisable(false);

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Incorrect value");
                alert.setContentText("Please, press OK and try again");
                alert.showAndWait();
                field.clear();
            }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Inputting value must be a number");
            alert.setContentText("Please, press OK and try again");
            alert.showAndWait();
            field.clear();
        }
    }

    public void SetNButtonPress(){
        try {
            n = new Integer(FieldN.getCharacters().toString());

            if(n>0) {
                sol.setN(n);
                Xlabel.setText("X: ");
                Tlabel.setText("T: ");
                Errorlabel.setText("Error: ");
                Nlabel.setText("N: "+ sol.getN());

                field.setDisable(false);
                Button2.setDisable(false);
                Button1.setDisable(false);

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Inputting value is negative");
                alert.setContentText("Please, press OK and try again");
                alert.showAndWait();
                field.clear();
            }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Inputting value must be a number");
            alert.setContentText("Please, press OK and try again");
            alert.showAndWait();
            field.clear();
        }
    }


    public void SetIButtonPress() {
        try {
            i = new Integer(FieldI.getCharacters().toString());

            if (i > 1) {
                prog.setI(i);
                Ilabel.setText("I: "+prog.getI());

                if(k!=0){
                    field.setDisable(false);
                    Button2.setDisable(false);
                    Button1.setDisable(false);
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Inputting value must be >1");
                alert.setContentText("Please, press OK and try again");
                alert.showAndWait();
                field.clear();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Inputting value must be a number");
            alert.setContentText("Please, press OK and try again");
            alert.showAndWait();
            field.clear();
        }
    }


    public void SetKButtonPress() {
        try {
            k = new Integer(FieldK.getCharacters().toString());

            if (k > 1) {
                prog.setK(k);
                Klabel.setText("K: "+prog.getK());

                if(i!=0){
                    field.setDisable(false);
                    Button2.setDisable(false);
                    Button1.setDisable(false);
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Inputting must be >1");
                alert.setContentText("Please, press OK and try again");
                alert.showAndWait();
                field.clear();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Inputting value must be a number");
            alert.setContentText("Please, press OK and try again");
            alert.showAndWait();
            field.clear();
        }
    }



}
