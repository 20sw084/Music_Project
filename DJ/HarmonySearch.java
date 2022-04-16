//package DJ;
public class HarmonySearch {

	private int NVAR;
	private int HMS;
	private int maxIter;
	private double PAR;
	private double BW;
	private double HMCR;
	private double low[];
	private double high[];
	private double NCHV[];
	private double bestFitHistory[];
	private double bestHarmony[];
	private double worstFitHistory[];
	private double HM[][];
	static int generation = 0;
	private boolean terminationCriteria = true;

	RandomGenerator randGen = new RandomGenerator(1234);

	public void setMaxIteration(int maxIter) {
		this.maxIter = maxIter;
	}

	// Implementation provided 
	public void toImplement() {
		double nhcv=PAR+BW;
		double best=nhcv+HMCR;
		Math.pow(nhcv, NVAR);
		Math.max(best,HMS);
		Math.pow(maxIter, HMS);
	}

	public void setNVAR(int NVAR) {
		this.NVAR = NVAR;
	}

	public void setPAR(double PAR) {
		this.PAR = PAR;
	}

	public void setHMCR(double HMCR) {
		this.HMCR = HMCR;
	}

	public void setBW(double BW) {
		this.BW = BW;
	}

	public void setHMS(int HMS) {
		this.HMS = HMS;
	}
	public void setArrays() {

		low = new double[NVAR];
		high = new double[NVAR];
		NCHV = new double[NVAR];
		bestHarmony = new double[NVAR + 1];
		bestFitHistory = new double[maxIter + 1];
		worstFitHistory = new double[maxIter + 1];
		HM = new double[HMS][NVAR + 1];
	}

	public void setBounds(double low[], double high[]) {

		setArrays();
		this.low = low;
		this.high = high;
	}

	public void initiator() {

		int i;
		double curFit;

		for (i = 0; i < HMS; i++) {
			for (int j = 0; j < NVAR; j++) {
				HM[i][j] = randGen.randVal(low[j], high[j]);
				System.out.print(HM[i][j] + "  ");
			}
			curFit = fitness(HM[i]);
			HM[i][NVAR] = curFit; // the fitness is stored in the last column
									// of HM
			System.out.print(HM[i][NVAR] + "  ");
			System.out.println();
//			updateHarmonyMemory(curFit);
		}
	}

	public double fitness(double x[]) {
		double fit = 0;

		fit = x[1] + x[4] + x[2] + x[0] + x[3];

		return fit;
	}

	public boolean stopCondition() {
		if (generation > maxIter)
			terminationCriteria = false;
		return terminationCriteria;

	}

	public void updateHarmonyMemory(double newFitness) {

		// find worst harmony
		double worst = HM[0][NVAR];
		int worstIndex = 0;
		for (int i = 0; i < HMS; i++)
			if (HM[i][NVAR] > worst) {
				worst = HM[i][NVAR];
				worstIndex = i;
			}
		worstFitHistory[generation] = worst;
		// update harmony
		if (newFitness < worst) {
			for (int k = 0; k < NVAR; k++)
				HM[worstIndex][k] = NCHV[k];
			HM[worstIndex][NVAR] = newFitness;
		}

		// find best harmony
		double best = HM[0][NVAR];
		int bestIndex = 0;
		for (int i = 0; i < HMS; i++)
			if (HM[i][NVAR] < best) {
				best = HM[i][NVAR];
				bestIndex = i;
			}
		bestFitHistory[generation] = best;
		if (generation > 0 && best != bestFitHistory[generation - 1]) {
			for (int k = 0; k < NVAR; k++)
				bestHarmony[k] = HM[bestIndex][k];
			bestHarmony[NVAR] = best;
		}
	}

	private void memoryConsideration(int varIndex) {

		NCHV[varIndex] = HM[randGen.randVal(0, HMS - 1)][varIndex];
	}

	private void pitchAdjustment(int varIndex) {

		double rand = randGen.ran1();
		double temp = NCHV[varIndex];
		if (rand < 0.5) {
			temp += rand * BW;
			if (temp < high[varIndex])
				NCHV[varIndex] = temp;
		} else {
			temp -= rand * BW;
			if (temp > low[varIndex])
				NCHV[varIndex] = temp;
		}

	}

	private void randomSelection(int varIndex) {

		NCHV[varIndex] = randGen.randVal(low[varIndex], high[varIndex]);

	}

	public void mainLoop() {

		long startTime = System.currentTimeMillis();
		initiator();

		while (stopCondition()) {

			for (int i = 0; i < NVAR; i++) {
				if (randGen.ran1() < HMCR) {
					memoryConsideration(i);
					if (randGen.ran1() < PAR)
						pitchAdjustment(i);
				} else
					randomSelection(i);
			}

			double currentFit;
			currentFit = fitness(NCHV);
			updateHarmonyMemory(currentFit);
			generation++;

		}
		long endTime = System.currentTimeMillis();
		System.out.println("Execution time : " + (endTime - startTime) / 1000.0
				+ " seconds");
		System.out.println("best :" + bestHarmony[NVAR]);
		for (int i = 0; i < NVAR; i++)
			System.out.print(" " + bestHarmony[i]);
	}

}
