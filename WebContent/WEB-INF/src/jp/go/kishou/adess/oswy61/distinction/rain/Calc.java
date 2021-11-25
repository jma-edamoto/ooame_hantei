package jp.go.kishou.adess.oswy61.distinction.rain;

import java.math.BigDecimal;

import jp.go.kishou.adess.common.util.MeteorologicalMath;

public class Calc {
	/**
	 * �����_�ȉ�1���̒l�Ɏl�̌ܓ�
	 * @param value ���̒l
	 * @return �l�̌ܓ����ď����_�ȉ�1���Ɋۂ߂��l
	 */
	public static double roundHalfDouble(double value) {
		BigDecimal bd = new BigDecimal(value);
		return bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * �����̒l�Ɏl�̌ܓ�
	 * @param value ���̒l
	 * @return �l�̌ܓ����Đ����Ɋۂ߂��l
	 */
	public static int roundHalfInt(double value) {
		BigDecimal bd = new BigDecimal(value);
		return bd.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
	}

	/**
	 * kindex�̌v�Z
	 * @param t850 850hPa�C���i�P���r���j
	 * @param t700 700hPa�C���i�P���r���j
	 * @param t500 500hPa�C���i�P���r���j
	 * @param rh850 850hPa���x�i�p�[�Z���g�j
	 * @param rh700 700hPa���x�i�p�[�Z���g�j
	 * @return kindex (�l�̌ܓ����ď����_�ȉ�1���Ɋۂ߂��l)
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
	 * ssi�̌v�Z
	 * @param t850 850hPa�C���i�P���r���j
	 * @param t500 500hPa�C���i�P���r���j
	 * @param rh850 850hPa���x�i�p�[�Z���g�j
	 * @return ssi (�l�̌ܓ����ď����_�ȉ�1���Ɋۂ߂��l)
	 */
	public static double ssi(double t850, double t500 ,double rh850) {
		t850 = t850 - MeteorologicalMath.ABSTEMP;
		t500 = t500 - MeteorologicalMath.ABSTEMP;
		double ttd850 = MeteorologicalMath.humTtd(t850, rh850 / 100);
		double ssi = MeteorologicalMath.ssi(t850, ttd850, t500);
		return roundHalfDouble(ssi);
	}

	/**
	 * �������ʂ̌v�Z
	 * @param t �C���i�P���r���j
	 * @param rh ���x�i�p�[�Z���g�j
	 * @return �������� (�l�̌ܓ����Đ����Ɋۂ߂��l)
	 */
	public static int ept(double t, double rh, double pressure) {
		t = t - MeteorologicalMath.ABSTEMP;
		double ttd = MeteorologicalMath.humTtd(t, rh / 100.0);
		return roundHalfInt(MeteorologicalMath.ept(pressure, t, ttd));
	}
}
