/**
 * Created by Admin on 14.04.2016.
 */
public class Laba2 {
    private Solution sol;
    private ImplicitScheme prog;
    private int N;
    private double epsilon;
    private int startI;
    private int startK;
    private double t;
    private double l;


    public Laba2(double l,double D,double H, double Beta, double epsilon, double t){
        this.epsilon = epsilon;
        this.t = t;
        this.l = l;

        this.sol = new Solution(l,D,H,Beta);
        this.prog = new ImplicitScheme(l,D,H,Beta);
        sol.setN(500);
//        sol.CalcN1(0.0,0.0,epsilon);
//        this.N = sol.getN();
    }

    public double[][] solution(int startI, int startK, int number){
        this.startI = startI;
        this.startK = startK;
        double temp1;
        double temp2;

        double arr[][] = new double[number][5];
        arr[0][0] = startI;
        arr[0][1] = startK;
        temp1 = norm(anmatrix(startI,startK),nummatrix(startI,startK));
        arr[0][2] = temp1;
        for (int n = 0; n < number-1; n++) {
            startI*=2;
            startK*=4;
            arr[n+1][0] = startI;
            arr[n+1][1] = startK;
            temp2 = norm(anmatrix(startI,startK),nummatrix(startI,startK));
            arr[n][3] = temp2;
            arr[n+1][2] = temp2;
            arr[n][4]= temp1/temp2;
            temp1 = temp2;
        }
        startI*=2;
        startK*=4;
        temp2 = norm(anmatrix(startI,startK),nummatrix(startI,startK));
        arr[number-1][3] = temp2;
        arr[number-1][4] = temp1/temp2;

        for (int i = 0; i < number; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }

        return arr;
    }

    private double[][] anmatrix(int I,int K){
        double xStep = l/(I+1);
        double tStep = t/(K+1);
        double[][] matrix = new double[K+1][I+1];
//        System.out.println(matrix.length+" " + matrix[0].length );
        for (int k = 0; k < K+1; k++) {
            for (int i = 0; i < I+1;i++){
                matrix[k][i] = sol.U(i*xStep, k*tStep);
            }
        }
        return matrix;
    }

    private double[][] nummatrix(int I, int K){
        prog.setI(I);
        prog.setK(K);
        prog.setCurT(t);
//        System.out.println(prog.solveMatrix().length + " " + prog.solveMatrix()[0].length);

        return prog.solveMatrix();
    }

    private double norm(double[][] an, double[][] num){
        double result = Double.MIN_VALUE;
        for (int i = 0; i < an.length; i++) {
            for (int j = 0; j < an[0].length; j++) {
                result = Math.abs(an[i][j]-num[i][j]) > result ? Math.abs(an[i][j]-num[i][j]) : result;
            }
        }
        return result;
    }
}
