/**
 * Created by Admin on 23.04.2016.
 */
public class Laba2_Tel {
    private ExplicitScheme scheme;
    private int N;
    private int startI;
    private int startK;
    private double t;
    private double l;


    public Laba2_Tel(double l, double D, double H, double Beta, double t) {
        this.t = t;
        this.l = l;

        this.scheme = new ExplicitScheme(l, D, H, Beta);

    }

    public double[][] solution(int startI, int startK, int number) {
        this.startI = startI;
        this.startK = startK;
        double temp1;
        double temp2;

        double arr[][] = new double[number][5];
        arr[0][0] = startI;
        arr[0][1] = startK;
        temp1 = norm(nummatrix(startI, startK), nummatrix(startI, startK * 2));
        arr[0][2] = temp1;
        for (int n = 0; n < number - 1; n++) {

            startK *= 2;
            arr[n + 1][0] = startI;
            arr[n + 1][1] = startK;
            temp2 = norm(nummatrix(startI, startK), nummatrix(startI, startK * 2));
            arr[n][3] = temp2;
            arr[n + 1][2] = temp2;
            arr[n][4] = temp1 / temp2;
            temp1 = temp2;
        }

        startK *= 2;
        temp2 = norm(nummatrix(startI, startK), nummatrix(startI, startK * 2));
        arr[number - 1][3] = temp2;
        arr[number - 1][4] = temp1 / temp2;

        System.out.println("Явная схема: ");
        for (int i = 0; i < number; i++) {
//            for (int j = 0; j < 5; j++) {
//                System.out.print(arr[i][j] + " ");
//            }
            System.out.print("I= " + arr[i][0]);
            System.out.print("; K= " + arr[i][1]);
            System.out.print("; hx= " + l/arr[i][0]);
            System.out.print("; ht= " + t/arr[i][1]);

            System.out.print("; eps(ht,hx)= " + arr[i][2]);
            System.out.print("; eps(ht/2,hx)= " + arr[i][3]);
            System.out.print("; delta= " + arr[i][4]);
            System.out.println();
        }

        return arr;
    }


    private double[][] nummatrix(int I, int K) {
        scheme.setI(I);
        scheme.setK(K);
        scheme.setCurT(t);

        return scheme.solveMatrix();
    }

    private double norm(double[][] num1, double[][] num2) {
        double result = 0;
        for (int i = 0; i < num1.length; i++) {
            for (int j = 0; j < num1[0].length; j++) {
                result += Math.abs(num1[i][j] - num2[i * 2][j]) * Math.abs(num1[i][j] - num2[i * 2][j]);
            }
        }
        result = Math.sqrt(result / num1.length / num1[0].length);
        return result;
    }
}
