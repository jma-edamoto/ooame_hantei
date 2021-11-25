package jp.go.kishou.adess.oswy61.distinction.rain;

import java.math.BigDecimal;

import jp.go.kishou.adess.common.util.MeteorologicalMath;

public class Calc {
	/**
	 * 小数点以下1桁の値に四捨五入
	 * @param value 元の値
	 * @return 四捨五入して小数点以下1桁に丸めた値
	 */
	public static double roundHalfDouble(double value) {
		BigDecimal bd = new BigDecimal(value);
		return bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 整数の値に四捨五入
	 * @param value 元の値
	 * @return 四捨五入して整数に丸めた値
	 */
	public static int roundHalfInt(double value) {
		BigDecimal bd = new BigDecimal(value);
		return bd.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
	}

	/**
	 * kindexの計算
	 * @param t850 850hPa気温（ケルビン）
	 * @param t700 700hPa気温（ケルビン）
	 * @param t500 500hPa気温（ケルビン）
	 * @param rh850 850hPa湿度（パーセント）
	 * @param rh700 700hPa湿度（パーセント）
	 * @return kindex (四捨五入して小数点以下1桁に丸めた値)
	 */
	public static double kIndex(double t850,double t700, double t500, double rh850,double rh700) {
		double t850C = t850 - MeteorologicalMath.ABSTEMP;
		double t700C = t700 - MeteorologicalMath.ABSTEMP;
		double t500C = t500 - MeteorologicalMath.ABSTEMP;
		double ttd850 = MeteorologicalMath.humTtd(t850C,rh850 / 100);
		double ttd700 = MeteorologicalMath.humTtd(t700C,rh700 / 100);
		double kindex = MeteorologicalMath.kIndex(t850C, t500C, ttd850, ttd700);
		return roundHalfDouble(kindex);
	}

	/**
	 * ssiの計算
	 * @param t850 850hPa気温（ケルビン）
	 * @param t500 500hPa気温（ケルビン）
	 * @param rh850 850hPa湿度（パーセント）
	 * @return ssi (四捨五入して小数点以下1桁に丸めた値)
	 */
	public static double ssi(double t850, double t500 ,double rh850) {
		t850 = t850 - MeteorologicalMath.ABSTEMP;
		t500 = t500 - MeteorologicalMath.ABSTEMP;
		double ttd850 = MeteorologicalMath.humTtd(t850, rh850 / 100);
		double ssi = MeteorologicalMath.ssi(t850, ttd850, t500);
		return roundHalfDouble(ssi);
	}

	/**
	 * 相当温位の計算
	 * @param t 気温（ケルビン）
	 * @param rh 湿度（パーセント）
	 * @return 相当温位 (四捨五入して整数に丸めた値)
	 */
	public static int ept(double t, double rh, double pressure) {
		t = t - MeteorologicalMath.ABSTEMP;
		double ttd = MeteorologicalMath.humTtd(t, rh / 100.0);
		return roundHalfInt(MeteorologicalMath.ept(pressure, t, ttd));
	}
}
