package jp.go.kishou.adess.oswy61.distinction.rain;

import jp.go.kishou.adess.common.util.MeteorologicalMath;;

public class Wind {
	public int speed;
	public int direction;

	public Wind(double u, double v) {
		double direction;
		double speed = MeteorologicalMath.KNOT_PER_METER*Math.sqrt(Math.pow(u,2)+Math.pow(v,2));
		if(u > 0.0) {
			direction = 270.0 - Math.toDegrees(Math.atan(v / u));
		}else if(u == 0.0){
			if(v >= 0.0){
				direction = 180.0;
			}else{
				direction = 360.0;
			}
		}else{
			direction = 90.0 - Math.toDegrees(Math.atan(v / u));
		}
		this.speed = Calc.roundHalfInt(speed);
		this.direction = Calc.roundHalfInt(direction);
	}
}
