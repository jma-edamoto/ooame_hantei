package jp.go.kishou.adess.oswy61.distinction.rain;

import java.util.Optional;
import java.util.stream.Stream;

import jp.go.kishou.adess.common.util.MeteorologicalMath;

public class SeJudgeData {
	public double v700;
	public double u850;
	public double kIndex;
	public double ssi;
	public double tpw;
	public int ept950;
	public int ept850;
	public int eptDiff;
	public double r1;

	public boolean isValidData;

	public int higashiScoreSum;
	public int nantouScoreSum;
	public score score;

	public class score{
		public int v700Score;
		public int u850Score;
		public int kIndexScore;
		public int ssiScore;
		public int tpwScore;
		public int ept950Score;
		public int eptDiffScore;

		public int sum() {
			return v700Score + u850Score + kIndexScore + ssiScore + tpwScore + ept950Score + eptDiffScore;
		}
	}

	public SeJudgeData(SeParameter parameter) {
		Optional<Double> u950 = parameter.u950();
		Optional<Double> v950 = parameter.v950();
		Optional<Double> t950 = parameter.t950();
		Optional<Double> rh950 = parameter.rh950();
		Optional<Double> u850 = parameter.u850();
		Optional<Double> v850 = parameter.v850();
		Optional<Double> t850 = parameter.t850();
		Optional<Double> rh850 = parameter.rh850();
		Optional<Double> v700 = parameter.v700();
		Optional<Double> t700 = parameter.t700();
		Optional<Double> rh700 = parameter.rh700();
		Optional<Double> t500 = parameter.t500();
		Optional<Double> rh500 = parameter.rh500();
		Optional<Double> tpw = parameter.tpw();

		Optional<Wind> wind950 = u950.isPresent() && v950.isPresent() ?
			Optional.of(new Wind(u950.get(), v950.get())) : Optional.empty();
		Optional<Wind> wind850 = u850.isPresent() && v850.isPresent() ?
			Optional.of(new Wind(u850.get(), v850.get())) : Optional.empty();
		Optional<Integer> speed850 = wind850.map(w -> w.speed);
		v700 = v700.map(v -> Calc.roundHalfDouble(v * MeteorologicalMath.KNOT_PER_METER));
		u850 = u850.map(u -> Calc.roundHalfDouble(u * MeteorologicalMath.KNOT_PER_METER));
		Optional<Double> kIndex = t850.isPresent() && t700.isPresent() && t500.isPresent() && rh850.isPresent() && rh700.isPresent() ?
			Optional.of(Calc.kIndex(t850.get(), t700.get(), t500.get(), rh850.get(), rh700.get())) : Optional.empty();
		Optional<Double> ssi = t850.isPresent() && t500.isPresent() && rh850.isPresent() ?
			Optional.of(Calc.ssi(t850.get(), t500.get(), rh850.get())) : Optional.empty();
		tpw = tpw.map(data -> Calc.roundHalfDouble(data));
		Optional<Integer> ept950 = t950.isPresent() && rh950.isPresent() ?
			Optional.of(Calc.ept(t950.get(), rh950.get(), 950)) : Optional.empty();
		Optional<Integer> ept850 = t850.isPresent() && rh850.isPresent() ?
			Optional.of(Calc.ept(t850.get(), rh850.get(), 850)) : Optional.empty();
		Optional<Integer> ept500 = t500.isPresent() && rh500.isPresent() ?
			Optional.of(Calc.ept(t500.get(), rh500.get(), 500)) : Optional.empty();
		Optional<Integer> eptDiff = ept950.flatMap(e9 -> ept850.map(e8 -> e9 - e8));
		Optional<Double> r1 = v700.isPresent() && speed850.isPresent() && tpw.isPresent() ?
			Optional.of(r1(v700.get(), speed850.get(), tpw.get())) : Optional.empty();

		isValidData = Stream.of(v700,u850,kIndex,ssi,tpw,ept950,eptDiff).map(Optional::isPresent).allMatch(v -> v == true);

		//Œ^‚Ì”»’è‚Æ“¾“_ŒvŽZ
		higashiScoreSum = 9999;
		nantouScoreSum = 9999;
		score = defaultScore(v700.orElse(0.0), u850.orElse(0.0), kIndex.orElse(0.0), ssi.orElse(9999.0), tpw.orElse(0.0), ept950.orElse(0), eptDiff.orElse(0));

		if(wind950.isPresent() && wind850.isPresent() && ept850.isPresent() && ept500.isPresent()) {
			higashiScoreSum = 2222;
			nantouScoreSum = 2222;
			if(isHigashiPattern(wind950.get(), wind850.get(), ept850.get(), ept500.get())) {
				score = higashiScore(v700.orElse(0.0), u850.orElse(0.0), kIndex.orElse(0.0), ssi.orElse(9999.0), tpw.orElse(0.0), ept950.orElse(0), eptDiff.orElse(0));
				higashiScoreSum = score.sum();
			}
			if(isNantouPattern(wind950.get(), wind850.get(), ept850.get(), ept500.get())) {
				score = nantouScore(v700.orElse(0.0), u850.orElse(0.0), kIndex.orElse(0.0), ssi.orElse(9999.0), tpw.orElse(0.0), ept950.orElse(0), eptDiff.orElse(0));
				nantouScoreSum = score.sum();
			}
		}

		this.v700 = v700.orElse(9999.0);
		this.u850 = u850.orElse(9999.0);
		this.kIndex = kIndex.orElse(9999.0);
		this.ssi = ssi.orElse(9999.0);
		this.tpw = tpw.orElse(9999.0);
		this.ept950 = ept950.orElse(9999);
		this.ept850 = ept850.orElse(9999);
		this.eptDiff = eptDiff.orElse(9999);
		this.r1 = r1.orElse(9999.0);
	}

	private double r1(double v700, double speed850, double tpw) {
		double r1 = 0.494 * Calc.roundHalfDouble(tpw) + 0.345 * Calc.roundHalfDouble(v700) - 0.734 * speed850 + 38.2;
		return Calc.roundHalfDouble(r1);
	}

	private boolean isHigashiPattern(Wind wind950, Wind wind850, int ept850, int ept500) {
		return wind950.direction >= 35 && wind950.direction < 100
			&& wind850.direction >= 80 && wind850.direction < 123
			&& (wind950.direction < 60 || wind850.direction < 102 || ept850 - ept500 < 4);
	}

	private boolean isNantouPattern(Wind wind950, Wind wind850, int ept850, int ept500) {
		return wind950.direction >= 60 && wind950.direction < 136
			&& wind850.direction >= 102 && wind850.direction < 180
			&& (wind950.direction >= 100 || wind850.direction >= 123 || ept850 - ept500 >= 4);
	}

	private score defaultScore(double v700, double u850, double kIndex, double ssi, double tpw, int ept950, int eptDiff) {
		score score = new score();
		score.v700Score = v700 >= 15 ? 1 : 0;
		score.u850Score = u850 >= -15 ? 1 : 0;
		score.kIndexScore = kIndex >= 35 ? 1 : 0;
		score.ssiScore = ssi <= 1 ? 1 : 0;
		score.tpwScore = tpw >= 55 ? 1 : 0;
		score.ept950Score = ept950 >= 350 ? 1 : 0;
		score.eptDiffScore = eptDiff >= 4 ? 1 : 0;
		return score;
	}

	private score higashiScore(double v700, double u850, double kIndex, double ssi, double tpw, int ept950, int eptDiff) {
		score score = new score();
		score.v700Score = v700 >= 15 ? 1 : 0;
		score.u850Score = u850 >= -15 ? 1 : 0;
		score.kIndexScore = kIndex >= 35 ? 1 : 0;
		score.ssiScore = ssi <= 1 ? 1 : 0;
		score.tpwScore = tpw >= 55 ? 1 : 0;
		score.ept950Score = ept950 >= 350 ? 1 : 0;
		score.eptDiffScore = eptDiff >= 4 ? 1 : 0;
		return score;
	}

	private score nantouScore(double v700, double u850, double kIndex, double ssi, double tpw, int ept950, int eptDiff) {
		score score = new score();
		score.v700Score = v700 >= 15 ? 1 : 0;
		score.u850Score = u850 >= -15 ? 1 : 0;
		score.kIndexScore = kIndex >= 35 ? 1 : 0;
		score.ssiScore = ssi <= 1 ? 1 : 0;
		score.tpwScore = tpw >= 55 ? 1 : 0;
		score.ept950Score = ept950 >= 350 ? 1 : 0;
		score.eptDiffScore = eptDiff >= 4 ? 1 : 0;
		return score;
	}
}
