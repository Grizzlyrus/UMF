import java.util.ArrayList;

/**
 * Created by Кирилл on 24.11.2015.
 */
public class Solution {
    private double l;
    private double D;
    private double H;
    private double Beta;
    private int n;
    private int n1;
    private double epsilon;
    private ArrayList<Double> mu = new ArrayList<>();

    private CalcMU a;

    public Solution(double l,double D,double H, double Beta){
        this.l=l;
        this.D=D;
        this.H=H;
        this.Beta=Beta;
    }


    public double Psi(double x){
        return 0.1*(Math.cos(Math.PI*x/l)+1);
    }

    public void setMu(ArrayList mu){
        this.mu = mu;
    }

    private double OmegaN(double x,int n){
        return H/mu.get(n)*Math.sin(mu.get(n)*x)+Math.cos(mu.get(n)*x);
    }

    private double func(int n){
        return Beta-mu.get(n)*mu.get(n)*D;
    }

    private double expFunc(double t, int n){
        return Math.exp(t*func(n));
    }

    private double Int2(int n){
        return H*H*l/(2*mu.get(n)*mu.get(n))-H*H/(4*mu.get(n)*mu.get(n)*mu.get(n))*Math.sin(2*mu.get(n)*l)-H/(2*mu.get(n)*mu.get(n))*Math.cos(2*mu.get(n)*l)+H/(2*mu.get(n)*mu.get(n))+l/2.0+1.0/(4*mu.get(n))*Math.sin(2*mu.get(n)*l);
    }

    private double Int1(int n){
        return 0.1*(H/mu.get(n)*(-(ch(l,n)*(cosCalc(l,n)+1))/und(l,n))+ch(l,n)*sinCalc(l,n)/und(l,n)-H/(mu.get(n)*mu.get(n))*(cosCalc(l,n)-1)+sinCalc(l,n)/mu.get(n));
    }

    private double cosCalc(double l, int n){
        return Math.cos(mu.get(n)*l);
    }

    private double sinCalc(double l, int n){
        return Math.sin(mu.get(n) * l);
    }

    private double und(double l,int n){
        return Math.PI*Math.PI-l*l*mu.get(n)*mu.get(n);
    }

    private double ch(double l, int n){
        return l*l*mu.get(n);
    }

    private double aN(double t, int n){
        return expFunc(t,n)*Int1(n)/Int2(n);
    }

    private double uN(double x, double t, int n){
        return aN(t,n)*OmegaN(x,n);
    }

    public double U(double x, double t){
        double sum = 0.0;
        for(int i=0;i<n;i++){
            sum+=uN(x,t,i);
        }
        return sum;
    }

    public double sumU(double x, double t, int k){
        double sum = 0.0;
        for(int i=0;i<k;i++){
            sum+=uN(x,t,i);
        }
        return sum;
    }

    public void CalcN1(double x, double t, double epsilon){
        this.epsilon=epsilon;
        int step = 256;
        int k=256;
        if(a==null) {
            a = new CalcMU(H, l, 10e-10);
        }
        if(k>=n1) {
            a.solution(k);
            this.setMu(a.getMu());
        }
        double temp1=sumU(x,t,k-step);
        double temp2=sumU(x,t,k);
        while (Math.abs(temp2-temp1)>epsilon){
            k+=step;
            if(k>=n1) {
                a.solution(k);
                this.setMu(a.getMu());
            }
            temp1=temp2;
            temp2=sumU(x,t,k);
        }
        this.n1=k;

        if(k!=step) {
            k = k - step - step / 2;
        }else{
            k=k-step/2;
        }
        step/=2;
        temp1=sumU(x,t,k);
        while (step>1){
            if(Math.abs(temp2-temp1)<epsilon){
                step/=2;
                k-=step;
                temp1=sumU(x,t,k);
            }else{
                step/=2;
                k+=step;
                temp1=sumU(x,t,k);
            }
        }

        if(k==1){k++;}

        this.n = k;
    }

    public double error(int n, double t){
        return 0.2*H*l*l/(D*Math.PI*Math.PI*Math.PI*n*n*t)*Math.exp(t*Beta-2.0*D*Math.PI*Math.PI*n*t/(l*l))*(1.0+l/(Math.PI*n));
    }

    public void CalcN(double t, double epsilon){
        this.epsilon=epsilon;
        int step = 256;
        int k=256;
        if(a==null) {
            a = new CalcMU(H, l, 10e-10);
        }
        if(k>=n1) {
            a.solution(k);
            this.setMu(a.getMu());
        }
        double temp1 = error(k,t);

        while (Math.abs(temp1)>epsilon){
            k+=step;
            if(k>=n1) {
                a.solution(k);
                this.setMu(a.getMu());
            }

            temp1 = error(k,t);
        }
        this.n1=k;


            k=k-step/2;

        step/=2;
        temp1 = error(k,t);
        while (step>1){
            if(Math.abs(temp1)<epsilon){
                step/=2;
                k-=step;
                temp1 = error(k,t);
            }else{
                step/=2;
                k+=step;
                temp1 = error(k,t);
            }
        }

        if(k==1){k++;}

        this.n = k;
    }


    public int getN(){
        return n;
    }

    public void setN(int k){
        if(a==null) {
            a = new CalcMU(H, l, 10e-10);
        }
        if(k>=n1) {
            this.n1=k;
            a.solution(k);
            this.setMu(a.getMu());
        }

        this.n=k;
    }


}
