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
 * Created by ������ on 29.11.2015.
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
//    private ImplicitScheme prog;
    private ExplicitScheme prog;

    private double CurCalcNX = 0.0;
    private double CurCalcNT = 0.0;
    private ArrayList<Double> allTvalues = new ArrayList<>();

    final ToggleGroup group = new ToggleGroup();

    final ToggleGroup group1 = new ToggleGroup();

    final ToggleGroup group2 = new ToggleGroup();


    boolean selector1 = true;

    boolean selector2 = true;

    boolean selector3 = true;

    @FXML
    private TextField fieldxnew;

    @FXML
    private TextField fieldt0;

    @FXML
    private TextField fieldt1;

    @FXML
    private RadioButton RadBut5;

    @FXML
    private RadioButton RadBut6;

    @FXML
    private Label Xlabel;

    @FXML
    private Label Tlabel;

    @FXML
    private Label Errorlabel;

    @FXML
    private Label Nlabel;


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

    @FXML
    private RadioButton RadBut3;

    @FXML
    private RadioButton RadBut4;

    public LineChartController(){
        Solution sol = new Solution(l,D,H,Beta);
//        sol.CalcN();
        this.sol = sol;

//        ImplicitScheme prog = new ImplicitScheme(l,D,H,Beta);
//        this.prog=prog;

        ExplicitScheme exscheme = new ExplicitScheme(l,D,H,Beta);
        this.prog = exscheme;

//        Laba2_1 laba2 = new Laba2_1(l,D,H,Beta,10);
//        laba2.solution(10,5,10);
//        System.out.println("***************************************************");
//        Laba2_Tel laba2_1 = new Laba2_Tel(l,D,H,Beta,10);
//        laba2_1.solution(10,5,10);
    }

    @FXML
    private void initialize() {
        RadBut3.setSelected(true);
        RadBut3.setToggleGroup(group1);
        RadBut4.setToggleGroup(group1);

        RadBut5.setSelected(true);
        RadBut5.setToggleGroup(group2);
        RadBut6.setToggleGroup(group2);

        field.setDisable(true);
        fieldxnew.setDisable(true);
        fieldt0.setDisable(true);
        fieldt1.setDisable(true);
        Button2.setDisable(true);
        Button1.setDisable(true);
        Chart.setCreateSymbols(false);
        Chart.setTitle("U(x,t)");
        RadBut1.setToggleGroup(group);
        RadBut2.setToggleGroup(group);
        RadBut1.setSelected(true);
        selector1 = true;

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
            XYChart.Series series1 = new XYChart.Series();

            series1.setName("Ан. реш. t= " + t);
            ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();

            double step;
            if(prog.getI()== -1.0){
                step = 0.05;
            }else{
                step = prog.getXstep();
            }
            for (double i = 0; i <= l; i += step) {
                datas.add(new XYChart.Data(i, sol.U(i, t)));
            }

            series1.setData(datas);

            Chart.getData().add(series1);
        } else {
            prog.setCurT(t);
            double x[][] = prog.solveMatrix();
            XYChart.Series series2 = new XYChart.Series();

            series2.setName("Явная схема t= " + t);
            ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();
            for(int i=0; i<x[k].length; i++){
                datas.add(new XYChart.Data(i*prog.getXstep(),x[k][i]));
            }
            series2.setData(datas);
            Chart.getData().add(series2);
        }
    }

    public void initGraph(double x, double t0, double t1) {

            XYChart.Series series1 = new XYChart.Series();

            series1.setName("Ан. реш. x= " + x);
            ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();

            double step=(t1-t0)/10000;

            for (double i = t0; i <= t1; i += step) {
                datas.add(new XYChart.Data(i, sol.U(x, i)));
            }

            series1.setData(datas);

            Chart.getData().add(series1);

    }


    @FXML
    public void SetRadBut1Press(){
        selector1 = true;
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
        RadBut3.setDisable(false);
        RadBut4.setDisable(false);


            if (n != 0) {
                field.setDisable(false);
                Button2.setDisable(false);
                Button1.setDisable(false);
            } else {
                field.setDisable(true);
                Button2.setDisable(true);
                Button1.setDisable(true);
            }

    }


    @FXML
    public void SetRadBut2Press(){
        selector1 = false;
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
        RadBut3.setDisable(true);
        RadBut4.setDisable(true);

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
    public void SetRadBut3Press() {
        FieldX.setDisable(false);
        selector2 = true;
    }

    @FXML
    public void SetRadBut4Press() {
        FieldX.setDisable(true);
        selector2 = false;
    }

    @FXML
    public void SetRadBut5Press() {
        if(n!=0) {
            field.setDisable(false);
        }
        fieldt0.setDisable(true);
        fieldt1.setDisable(true);
        fieldxnew.setDisable(true);

        SetRadBut1Press();
        RadBut1.setDisable(false);
        RadBut2.setDisable(false);

        if(!selector3){
            this.ClearButtonPress();
        }
        selector3 = true;
    }

    @FXML
    public void SetRadBut6Press() {
        field.setDisable(true);
        if(n!=0) {
            fieldt0.setDisable(false);
            fieldt1.setDisable(false);
            fieldxnew.setDisable(false);
        }

        RadBut1.setDisable(true);
        RadBut2.setDisable(true);

        if(selector3){
            this.ClearButtonPress();
        }
        selector3 = false;
    }

    @FXML
    public void DrawButtonPress(){
        Double t;
        Double t0;
        Double t1;
        Double x;

        if(selector3) {
            try {
                t = new Double(field.getCharacters().toString());
                if (t >= 0 && selector1) {

                    allTvalues.add(t);
                    initGraph(t, selector1);
                } else if (t >= 0 && !selector1) {
                    initGraph(t, selector1);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Inputting value is negative or already exists");
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
        }else {
            try {
                t0 = new Double(fieldt0.getCharacters().toString());
                t1 = new Double(fieldt1.getCharacters().toString());
                x = new Double(fieldxnew.getCharacters().toString());
                if (t0 >= 0 && t1 > t0&& x>0 &&x<l) {

                    initGraph(x,t0,t1);

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Inputting value is negative or already exists");
                    alert.setContentText("Please, press OK and try again");
                    alert.showAndWait();
                    fieldt0.clear();
                    fieldt1.clear();
                    fieldxnew.clear();

                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Inputting value must be a number");
                alert.setContentText("Please, press OK and try again");
                alert.showAndWait();
                fieldt0.clear();
                fieldt1.clear();
                fieldxnew.clear();
            }
        }
    }



    @FXML
    public void ClearButtonPress(){
        allTvalues.clear();
        field.clear();
        Chart.getData().clear();
    }


    @FXML
    public void CalcNButtonPress(){
        Double x,t, error;
        try {
            t = new Double(FieldT.getCharacters().toString());
            if(selector2 == true) {
                x = new Double(FieldX.getCharacters().toString());
            }else{
                x=0.0;
            }
            error = new Double(FieldError.getCharacters().toString());

            if(t>=0&&x>=0&&x<=l&&error>0 && error<0.2) {
                CurCalcNT = t;
                if(selector2 == true){
                    CurCalcNX = x;
                }

                epsilon = error;

                if(selector2 == true) {
                    sol.CalcN1(x, t, error);
                    Xlabel.setText("X: " + x);
                }else{
                    sol.CalcN(t,error);
                }
                Tlabel.setText("T: "+t);
                Errorlabel.setText("Погр. : "+epsilon);
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
                if(selector3) {
                    sol.setN(n);
                    Xlabel.setText("X: ");
                    Tlabel.setText("T: ");
                    Errorlabel.setText("Погр.: ");
                    Nlabel.setText("N: " + sol.getN());

                    field.setDisable(false);
                    Button2.setDisable(false);
                    Button1.setDisable(false);
                }else{
                    sol.setN(n);
                    Xlabel.setText("X: ");
                    Tlabel.setText("T: ");
                    Errorlabel.setText("Погр.: ");
                    Nlabel.setText("N: " + sol.getN());

                    fieldxnew.setDisable(false);
                    fieldt0.setDisable(false);
                    fieldt1.setDisable(false);
                    Button2.setDisable(false);
                    Button1.setDisable(false);
                }

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
