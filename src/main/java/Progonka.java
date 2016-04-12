/**
 * Created by Admin on 04.03.2016.
 */
public class Progonka {
    private double l;
    private double D;
    private double H;
    private double Beta;
    private double Xstep;
    private double Tstep;
    private int I;
    private int K;
    private double curT;
    private double[][] P;

    public Progonka(double l, double D, double H, double Beta) {
        this.l = l;
        this.D = D;
        this.H = H;
        this.Beta = Beta;
    }

    public void setCurT(double curT) {

        this.curT = curT;
        Tstep = curT / K;

    }

    public void setI(int I) {
        this.I = I + 1;
        Xstep = l / I;
    }

    public void setK(int K) {
        this.K = K + 1;
    }

    public double getXstep() {
        return Xstep;
    }

    public double getTstep() {
        return Tstep;
    }

    public double getK() {
        return K - 1;
    }

    public double getI() {
        return I - 1;
    }

    public void genMatrix() {
        P = new double[I][I];
        P[0][0] = 1.0 / Tstep + 2.0 * D / (Xstep * Xstep) * (1.0 + Xstep * H) - Beta;
        P[0][1] = -2.0 * D / (Xstep * Xstep);
        for (int i = 1; i < I - 1; i++) {
            P[i][i - 1] = -D / (Xstep * Xstep);
            P[i][i] = 1.0 / Tstep + 2.0 * D / (Xstep * Xstep) - Beta;
            P[i][i + 1] = -D / (Xstep * Xstep);
        }
        P[I - 1][I - 2] = -2.0 * D / (Xstep * Xstep);
        P[I - 1][I - 1] = 1.0 / Tstep + 2.0 * D / (Xstep * Xstep) * (1.0 + Xstep * H) - Beta;
    }

    public double[] solveMatrix() {

        this.genMatrix();
        double f[] = new double[I];
        for (int i = 0; i < I; i++) {
            f[i] = 0.1 * (Math.cos(Math.PI * Xstep * (i + 1) / l) + 1) / Tstep;
        }

        double c[] = new double[I];
        double a[] = new double[I];
        double b[] = new double[I];
        double x[] = new double[I];

        for (int i = 1; i < I - 1; i++) {
            c[i] = P[i][i];
            a[i] = P[i][i - 1];
            b[i] = P[i][i + 1];
        }
        b[0] = P[0][1];
        b[I - 1] = 0.0;
        a[0] = 0.0;
        c[I - 1] = P[I - 1][I - 1];
        a[I - 1] = P[I - 1][I - 2];
        c[0] = P[0][0];

        double[] alpha = new double[I];
        double[] betha = new double[I];


        for (int k = 0; k < K; k++) {
            alpha[1] = -b[0] / c[0];
            betha[1] = f[0] / c[0];

            for (int i = 2; i < I; i++) {
                alpha[i] = -b[i - 1] / (a[i - 1] * alpha[i - 1] + c[i]);
                betha[i] = (f[i - 1] - a[i - 1] * betha[i - 1]) / (a[i - 1] * alpha[i - 1] + c[i]);
            }

            x[I - 1] = (f[I - 1] - a[I - 1] * betha[I - 1]) / (c[I - 1] + a[I - 1] * alpha[I - 1]);

            for (int i = I - 2; i >= 0; i--) {
                x[i] = alpha[i + 1] * x[i + 1] + betha[i + 1];
            }

            for (int i = 0; i < I; i++) {
                f[i] = x[i] / Tstep;
            }
        }
        return x;
    }
}
