package jp.go.kishou.adess.oswy61.distinction.rain;

import java.util.Optional;

public class SwParameter {
	private final Optional<Double> _u950; //メートル
	private final Optional<Double> _v950; //メートル
	private final Optional<Double> _u850; //メートル
	private final Optional<Double> _v850; //メートル
	private final Optional<Double> _t850; //ケルビン
	private final Optional<Double> _t700; //ケルビン
	private final Optional<Double> _t500; //ケルビン
	private final Optional<Double> _t950; //ケルビン
	private final Optional<Double> _rh700;
	private final Optional<Double> _rh850;
	private final Optional<Double> _tpw;
	private final Optional<Double> _rh950;

	public SwParameter(
		Optional<Double> u950,
		Optional<Double> v950,
		Optional<Double> t950,
		Optional<Double> rh950,
		Optional<Double> u850,
		Optional<Double> v850,
		Optional<Double> t850,
		Optional<Double> rh850,
		Optional<Double> t700,
		Optional<Double> rh700,
		Optional<Double> t500,
		Optional<Double> tpw
	) {
		_u950 = u950;
		_v950 = v950;
		_t950 = t950;
		_rh950 = rh950;
		_u850 = u850;
		_v850 = v850;
		_t850 = t850;
		_rh850 = rh850;
		_t700 = t700;
		_rh700 = rh700;
		_t500 = t500;
		_tpw = tpw;
	}

	public Optional<Double> u950(){return _u950;}
	public Optional<Double> v950(){return _v950;}
	public Optional<Double> t950(){return _t950;}
	public Optional<Double> rh950(){return _rh950;}
	public Optional<Double> u850(){return _u850;}
	public Optional<Double> v850(){return _v850;}
	public Optional<Double> t850(){return _t850;}
	public Optional<Double> rh850(){return _rh850;}
	public Optional<Double> t700(){return _t700;}
	public Optional<Double> rh700(){return _rh700;}
	public Optional<Double> t500(){return _t500;}
	public Optional<Double> tpw(){return _tpw;}
}
