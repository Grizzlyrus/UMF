/**
 * Created by Admin on 23.04.2016.
 */
public class ExplicitScheme {
    private double l;
    private double D;
    private double H;
    private double Beta;
    private double Xstep;
    private double Tstep;
    private int I=0;
    private int K=0;
    private double curT;
    private double[][] P;

    public ExplicitScheme(double l, double D, double H, double Beta) {
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

    public double gamma(){
        return Tstep/(Xstep*Xstep)*D;
    }

    public double[][] solveMatrix() {

        double result[][] = new double[K][I];

        if(1-2*gamma()+Beta*Tstep<0.0){
            System.out.println("not work");
            return result;
        }

        for (int i = 0; i < I; i++) {
            result[0][i] = 0.1 * (Math.cos(Math.PI * Xstep * (i + 1) / l) + 1);
        }

        if(Tstep == 0.0){return  result;}

        for (int k = 1; k < K; k++) {
            result[k][0]= result[k-1][0]*(1-2*gamma()*(1+Xstep*H)+Beta*Tstep)+result[k-1][1]*2*gamma();
            for (int i=1;i<I-1;i++){
                result[k][i] = result[k-1][i] * (1-2*gamma()+Beta*Tstep)+result[k-1][i+1]* gamma()+ result[k-1][i-1]*gamma();
            }
            result[k][I-1]= result[k-1][I-1]*(1-2*gamma()*(1+Xstep*H)+Beta*Tstep)+result[k-1][I-2]*2*gamma();
        }

        for (int k = 0; k < K; k++) {
            for (int i = 0; i < I; i++) {
                if(Math.abs(result[k][i])>Math.exp(Tstep*K*Beta)*(1+Tstep*K)*0.2 ){
                    System.out.println("not work");
                    result = new double[K][I];
                    return result;
                }
            }
        }

//        for (int k = 0; k < K; k++) {
//            for (int i = 0; i < I; i++) {
//                System.out.print(result[k][i]+" ");
//            }
//            System.out.println();
//        }


        return result;
    }
}
