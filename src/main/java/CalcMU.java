import java.util.ArrayList;

/**
 * Created by Кирилл on 26.11.2015.
 */
public class CalcMU {

    private int n;
    private double H;
    private double l;
    private double epsilon;
    private ArrayList<Double> mu = new ArrayList<>();

    public CalcMU(double H, double l, double epsilon){
        this.H = H;
        this.l = l;
        this.epsilon = epsilon;
    }


    public ArrayList getMu(){
        return mu;
    }

    private double function(double y) {
        return 1/Math.tan(y)-y/(2*H*l)+(H*l)/(2*y);
    }

    private double CalkMuN(int k, double muMinus,boolean isPos) {

        if(!isPos){
            double yL=Math.PI*(k+0.5);
            double yR = Math.PI*(k+1)-10e-12;
            double yMed = (yR+yL)/2.0;
            while (Math.abs(yL-yR)>epsilon){
                if (Math.signum(function(yR))*Math.signum(function(yMed))<0){
                    yL=yMed;
                    yMed = (yR+yL)/2.0;
                }else{
                    yR=yMed;
                    yMed = (yR+yL)/2.0;
                }
            }
            return yMed;
        }

        double yL = Math.PI*k+10e-12;
        double yR = muMinus+Math.PI;
        if(yL>yR) return Math.PI*k;

        double yMed = (yR+yL)/2.0;
        while (Math.abs(yL-yR)>epsilon){
            if (Math.signum(function(yR))*Math.signum(function(yMed))<0){
                yL=yMed;
                yMed = (yR+yL)/2.0;
            }else{
                yR=yMed;
                yMed = (yR+yL)/2.0;
            }
        }
        return yMed;

    }

    public void solution(int n){
        this.n=n;
//        mu = new double[n];
        boolean isPos = false;
        int k1=mu.size();
        int k=mu.size();

        while(function(k*Math.PI+0.5*Math.PI)>0&&k<n){
            mu.add(CalkMuN(k,0.0,isPos));
            k++;
        }

        isPos = true;
        if(k==0){
            mu.add(CalkMuN(k,0.0,isPos));
            k++;
        }

        while (k<n&&(k-1)>=0){
            if(k==k1){
                mu.add(CalkMuN(k,mu.get(k-1)*l,isPos));
            }else {
                mu.add(CalkMuN(k, mu.get(k - 1), isPos));
            }
            k++;
        }

        for (int i = k1; i< n; i++){
            mu.set(i,mu.get(i)/l);
        }
    }

    public void printMu(){
        for(int i=0;i<n;i++){
            System.out.println(mu.get(i));
        }
    }

}
