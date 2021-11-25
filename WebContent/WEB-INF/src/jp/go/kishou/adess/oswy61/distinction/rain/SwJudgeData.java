package jp.go.kishou.adess.oswy61.distinction.rain;

import java.util.Optional;
import java.util.stream.Stream;

import jp.go.kishou.adess.common.util.MeteorologicalMath;

public class SwJudgeData {
	public int speed950;
	public double kIndex;
	public double ssi;
	public double tpw;
	public int ept950;
	public int ept850;
	public int eptDiff;
	public double r1;

	public boolean isValidData;

	public int hokubuScoreSum;
	public int nairikuScoreSum;
	public int kichuScoreSum;
	public int enganScoreSum;
	public int tanabeScoreSum;
	public score score;

	public class score{
		public int speed950Score;
		public int kIndexScore;
		public int ssiScore;
		public int tpwScore;
		public int ept950Score;
		public int ept850Score;
		public int eptDiffScore;

		public int sum(){
			return speed950Score + kIndexScore + ssiScore + tpwScore + ept950Score + ept850Score + eptDiffScore;
		}
	}

	public SwJudgeData(SwParameter parameter) {
		Optional<Double> u950 = parameter.u950();
		Optional<Double> v950 = parameter.v950();
		Optional<Double> u850 = parameter.u850();
		Optional<Double> v850 = parameter.v850();
		Optional<Double> t850 = parameter.t850();
		Optional<Double> t700 = parameter.t700();
		Optional<Double> t500 = parameter.t500();
		Optional<Double> t950 = parameter.t950();
		Optional<Double> rh700 = parameter.rh700();
		Optional<Double> rh850 = parameter.rh850();
		Optional<Double> tpw = parameter.tpw();
		Optional<Double> rh950 = parameter.rh950();

		Optional<Wind> wind950 = u950.isPresent() && v950.isPresent() ? Optional.of(new Wind(u950.get(),v950.get())) : Optional.empty();
		Optional<Integer> speed950 = wind950.map(w -> w.speed);
		Optional<Wind> wind850 = u850.isPresent() && v850.isPresent() ? Optional.of(new Wind(u850.get(),v850.get())) : Optional.empty();
		Optional<Double> kIndex = t850.isPresent() && t700.isPresent() && t500.isPresent() && rh700.isPresent() && rh850.isPresent() ?
			Optional.of(Calc.kIndex(t850.get(), t700.get(), t500.get(), rh850.get(), rh700.get())) : Optional.empty();
		Optional<Double> ssi = t850.isPresent() && t500.isPresent() && rh850.isPresent() ?
			Optional.of(Calc.ssi(t850.get(), t500.get(), rh850.get())) : Optional.empty();
		tpw = tpw.map(data -> Calc.roundHalfDouble(data));
		Optional<Integer> ept950 = t950.isPresent() && rh950.isPresent() ? Optional.of(Calc.ept(t950.get(), rh950.get(), 950)) : Optional.empty();
		Optional<Integer> ept850 = t850.isPresent() && rh850.isPresent() ? Optional.of(Calc.ept(t850.get(), rh850.get(), 850)) : Optional.empty();
		Optional<Integer> eptDiff = ept950.flatMap(e9 -> ept850.map(e8 -> e9 - e8));
		Optional<Double> r1 = v850.flatMap(v -> ept950.map(e -> r1(v, e)));

		isValidData = Stream.of(speed950,kIndex,ssi,tpw,ept950,ept850,eptDiff).map(Optional::isPresent).allMatch(v -> v == true);

		//Œ^‚Ì”»’è‚Æ“¾“_ŒvŽZ
		hokubuScoreSum = 9999;
		nairikuScoreSum = 9999;
		kichuScoreSum = 9999;
		enganScoreSum = 9999;
		tanabeScoreSum = 9999;
		score = defaultScore(kIndex.orElse(0.0), ssi.orElse(9999.0), tpw.orElse(0.0), ept950.orElse(0), ept850.orElse(0), eptDiff.orElse(0));

		if(wind950.isPresent() && wind850.isPresent()) {
			hokubuScoreSum = 2222;
			nairikuScoreSum = 2222;
			kichuScoreSum = 2222;
			enganScoreSum = 2222;
			tanabeScoreSum = 2222;

			if(isHokubuPattern(wind950.get(), wind850.get())) {
				score = hokubuScore(speed950.orElse(0), kIndex.orElse(0.0), ssi.orElse(9999.0), tpw.orElse(0.0), ept950.orElse(0), ept850.orElse(0), eptDiff.orElse(0));
				hokubuScoreSum = score.sum();
			}
			if(isNairikuPattern(wind950.get(), wind850.get())) {
				score = nairikuScore(speed950.orElse(0), kIndex.orElse(0.0), ssi.orElse(9999.0), tpw.orElse(0.0), ept950.orElse(0), ept850.orElse(0), eptDiff.orElse(0));
				nairikuScoreSum = score.sum();
			}
			if(isKichuPattern(wind950.get(), wind850.get())) {
				score = kichuScore(speed950.orElse(0), kIndex.orElse(0.0), ssi.orElse(9999.0), tpw.orElse(0.0), ept950.orElse(0), ept850.orElse(0), eptDiff.orElse(0));
				kichuScoreSum = score.sum();
			}
			if(isEnganPattern(wind950.get(), wind850.get())) {
				score = enganScore(speed950.orElse(0), kIndex.orElse(0.0), ssi.orElse(9999.0), tpw.orElse(0.0), ept950.orElse(0), ept850.orElse(0), eptDiff.orElse(0));
				enganScoreSum = score.sum();
			}
			if(isTanabePattern(wind950.get(), wind850.get())) {
				score = tanabeScore(speed950.orElse(0), kIndex.orElse(0.0), ssi.orElse(9999.0), tpw.orElse(0.0), ept950.orElse(0), ept850.orElse(0), eptDiff.orElse(0));
				tanabeScoreSum = score.sum();
			}
		}

		//’l‚ðŠi”[
		this.speed950 = speed950.orElse(9999);
		this.kIndex = kIndex.orElse(9999.0);
		this.ssi = ssi.orElse(9999.0);
		this.tpw = tpw.orElse(9999.0);
		this.ept950 = ept950.orElse(9999);
		this.ept850 = ept850.orElse(9999);
		this.eptDiff =  eptDiff.orElse(9999);
		this.r1 =r1.orElse(9999.0);
	}

	private double r1(double v850,int ept950) {
		double vKnot = v850 * MeteorologicalMath.KNOT_PER_METER;
		double r1 = 1.025 * Calc.roundHalfInt(ept950) + 0.415 * Calc.roundHalfDouble(vKnot) - 297.4;
		return Calc.roundHalfDouble(r1);
	}

	private boolean isHokubuPattern(Wind wind950,Wind wind850) {
		return (wind950.speed >= 25 || wind850.speed >= 25)
			&& wind950.direction >= 215 && wind950.direction <= 230
			&& wind850.direction >= 235 && wind850.direction <= 250
			&& wind850.direction - wind950.direction <= 20;
	}

	private boolean isNairikuPattern(Wind wind950, Wind wind850) {
		return (wind950.speed >= 25 || wind850.speed >= 25)
			&& wind950.direction >= 210 && wind950.direction <= 220
			&& wind850.direction >= 210 && wind850.direction <= 235
			&& wind850.direction - wind950.direction <= 15;
	}

	private boolean isKichuPattern(Wind wind950, Wind wind850) {
		return (wind950.speed >= 25 || wind850.speed >= 25)
			&& wind950.direction >= 170 && wind950.direction <= 230
			&& wind850.direction >= 180 && wind850.direction <= 250;
	}

	private boolean isEnganPattern(Wind wind950,Wind wind850) {
		return (wind950.speed < 25 && wind850.speed < 25)
			&& wind950.direction >= 125 && wind950.direction <= 210
			&& wind850.direction >= 180 && wind850.direction <= 225;
	}

	private boolean isTanabePattern(Wind wind950,Wind wind850) {
		return (wind950.speed < 25 && wind850.speed < 25)
			&& wind950.direction >= 210 && wind950.direction <= 235
			&& wind850.direction >= 225 && wind850.direction <= 255;
	}

	private score defaultScore(double kIndex,double ssi,double tpw, int ept950, int ept850, int eptDiff) {
		score score = new score();
		score.speed950Score = 0;
		score.kIndexScore = kIndex >= 35 ? 1 : 0;
		score.ssiScore = ssi <= 1 ? 1 : 0;
		score.tpwScore = tpw >= 45 ? 1 : 0;
		score.ept950Score = ept950 >= 335 ? 1 : 0;
		score.ept850Score = ept850 >= 335 ? 1 : 0;
		score.eptDiffScore = eptDiff >= 6 ? 2 : eptDiff >= 2 ? 1 : 0;
		return score;
	}

	private score hokubuScore(int speed950, double kIndex,double ssi,double tpw, int ept950, int ept850, int eptDiff) {
		score score = new score();
		score.speed950Score = speed950 >= 40 ? 1 : 0;
		score.kIndexScore = kIndex >= 35 ? 1 : 0;
		score.ssiScore = ssi <= 1 ? 1 : 0;
		score.tpwScore = tpw >= 50 ? 1 : 0;
		score.ept950Score = ept950 >= 340 ? 1 : 0;
		score.ept850Score = ept850 >= 340 ? 1 : 0;
		score.eptDiffScore = eptDiff >= 6 ? 2 : eptDiff >= 2 ? 1 : 0;
		return score;
	}

	private score nairikuScore(double speed950, double kIndex,double ssi,double tpw, int ept950, int ept850, int eptDiff) {
		score score = new score();
		score.speed950Score = speed950 >= 40 ? 1 : 0;
		score.kIndexScore = kIndex >= 35 ? 1 : 0;
		score.ssiScore = ssi <= 1 ? 1 : 0;
		score.tpwScore = tpw >= 45 ? 1 : 0;
		score.ept950Score = ept950 >= 335 ? 1 : 0;
		score.ept850Score = ept850 >= 335 ? 1 : 0;
		score.eptDiffScore = eptDiff >= 6 ? 2 : eptDiff >= 2 ? 1 : 0;
		return score;
	}

	private score kichuScore(double speed950, double kIndex,double ssi,double tpw, int ept950, int ept850, int eptDiff) {
		score score = new score();
		score.speed950Score = speed950 >= 40 ? 1 : 0;
		score.kIndexScore = kIndex >= 35 ? 1 : 0;
		score.ssiScore = ssi <= 1 ? 1 : 0;
		score.tpwScore = tpw >= 45 ? 1 : 0;
		score.ept950Score = ept950 >= 335 ? 1 : 0;
		score.ept850Score = ept850 >= 335 ? 1 : 0;
		score.eptDiffScore = eptDiff >= 6 ? 2 : eptDiff >= 2 ? 1 : 0;
		return score;
	}

	private score enganScore(double speed950, double kIndex,double ssi,double tpw, int ept950, int ept850, int eptDiff) {
		score score = new score();
		score.speed950Score = speed950 >= 15 ? 1 : 0;
		score.kIndexScore = kIndex >= 35 ? 1 : 0;
		score.ssiScore = ssi <= 1 ? 1 : 0;
		score.tpwScore = tpw >= 50 ? 1 : 0;
		score.ept950Score = ept950 >= 345 ? 1 : 0;
		score.ept850Score = ept850 >= 340 ? 1 : 0;
		score.eptDiffScore = eptDiff >= 6 ? 2 : eptDiff >= 2 ? 1 : 0;
		return score;
	}

	private score tanabeScore(double speed950, double kIndex,double ssi,double tpw, int ept950, int ept850, int eptDiff) {
		score score = new score();
		score.speed950Score = speed950 >= 15 ? 1 : 0;
		score.kIndexScore = kIndex >= 35 ? 1 : 0;
		score.ssiScore = ssi <= 1 ? 1 : 0;
		score.tpwScore = tpw >= 50 ? 1 : 0;
		score.ept950Score = ept950 >= 345 ? 1 : 0;
		score.ept850Score = ept850 >= 340 ? 1 : 0;
		score.eptDiffScore = eptDiff >= 6 ? 2 : eptDiff >= 2 ? 1 : 0;
		return score;
	}
}
