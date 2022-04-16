//package DJ;

import java.util.ArrayList;


public class RandomGenerator {

	private final long IA= 16807;
	private long IM= 2147483647;
	private double AM= (1.0/IM);
	private long IQ= 127773;
	private long IR= 2836;
	private int NTAB= 32;
	private double NDIV= (1+(IM-1)/NTAB);
	private double EPS= 1.2e-7;
	private double RNMX= (1.0-EPS);
	private long iy;
	private long iv[]= new long [NTAB];
	private long idum;

	
	ArrayList<RandomGenerator> arr = new ArrayList<RandomGenerator>();

	RandomGenerator(){
		sran1((int)System.currentTimeMillis());
	}
	
	RandomGenerator(int seed){
		sran1(seed);
	}
	
	public void sran1(int seed){
		
		int j;
		long k;

		idum = seed;
		if (idum < 1) idum=1;
		for (j=NTAB+7;j>=0;j--) {
			k=(idum)/IQ;
			idum=IA*(idum-k*IQ)-IR*k;
			if (idum < 0) idum += IM;
			if (j < NTAB) iv[j] = idum;
		}
		iy=iv[0];

	}

	
	public double ran1(){
		
		int j;
		long k;
		double temp;

		k=(idum)/IQ;
		idum=IA*(idum-k*IQ)-IR*k;
		if (idum < 0) idum += IM;
		j = (int)(iy / NDIV);
		iy=iv[j];
		iv[j] = idum;
		temp=AM*iy;
		if (temp > RNMX) return RNMX;
		else return temp;
	}
	

	public double randVal(double low, double high){
		
		return (float)(ran1()*(high-low)+low);
	}

	
	public int randVal(int low, int high){
		
		return (int)(Math.floor(ran1()*(high-low)+low+.5));
	}


}
