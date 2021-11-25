package jp.go.kishou.adess.oswy61.distinction.rain;

/**
 * 判定結果のブラウザ表示に用いる文字列やカラーコードを生成
 * @author JMA70D3
 */

public class Display {

	/**
	 * 型の得点によって型の得点セルに表示させる文字列を決定するメソッド
	 * @param POINT 型の得点
	 * @return 得点を文字列に変換したもの（または空白、「-」）
	 */
	public static String getPoint(int POINT){
		String point;
		if(POINT==9999){
			point="";
		}else if(POINT>=0&&POINT<=8){
			point=Integer.toString(POINT);
		}else{
			point="-";
		}return point;
	}

	/**
	 * 型の得点によって型の得点セルに表示させる文字列を決定するメソッド
	 * @param POINT 型の得点
	 * @param FLAG 判定に用いる要素に抜けがあるかないかを示すフラグ;sw_flagとse_flag
	 * @return 得点を文字列に変換したもの（または空白、「-」）
	 */
	public static String getPoint(int POINT,boolean FLAG){
		String point;
		if(POINT==9999){
			point="";
		}else if(POINT>=0&&POINT<=8){
			if(FLAG==true){
				point=Integer.toString(POINT);
			}else{
				point=Integer.toString(POINT)+")";
			}
		}else{
			point="-";
		}return point;
	}

	/**
	 * 要素の得点によって要素セルに表示させる文字列を決定するメソッド
	 * @param POINT 要素の得点
	 * @param VALUE 要素の値（int）
	 * @return 要素の値を文字列に変換したもの（または空白）
	 */
	public static String getValue(int POINT,int VALUE){
		String value;
		if(POINT==9999){
			value="";
		}else{
			value=Integer.toString(VALUE);
		}return value;
	}

	/**
	 * 要素の得点によって要素セルに表示させる文字列を決定するメソッド
	 * @param POINT 要素の得点
	 * @param VALUE 要素の値（double）
	 * @return 要素の値を文字列に変換したもの（または空白）
	 */
	public static String getValue(int POINT,double VALUE){
		String value;
		if(POINT==9999.){
			value="";
		}else{
			value=Double.toString(VALUE);
		}return value;
	}

	/**
	 * 要素の値によって要素セルに表示させる文字列を決定するメソッド
	 * @param VALUE 要素の値（int）
	 * @return 要素の値を文字列に変換したもの（または空白）
	 */
	public static String getValue(int VALUE){
		String value;
		if(VALUE==9999){
			value="";
		}else{
			value=Integer.toString(VALUE);
		}return value;
	}

	/**
	 * 要素の値によって要素セルに表示させる文字列を決定するメソッド
	 * @param VALUE 要素の値（double）
	 * @return 要素の値を文字列に変換したもの（または空白）
	 */
	public static String getValue(double VALUE){
		String value;
		if(VALUE==9999.){
			value="";
		}else{
			value=Double.toString(VALUE);
		}return value;
	}

	/**
	 * 型の得点によって型の得点セルの背景色を決定するメソッド
	 * @param POINT 型の得点
	 * @param type "SW"または"SE"
	 * @return htmlのカラーコード
	 */
	public static String getBgcolor(int POINT,String type){
		String bgcolor="#ffffff";
		if(POINT==5){
			bgcolor="#ffff00";
		}else if(POINT>=6&&POINT<=8){
			bgcolor="#ff0000";
		}else if(POINT>=0&&POINT<=4){
			if(type.equals("SW")){
				bgcolor="#aaffd5";
			}if(type.equals("SE")){
				bgcolor="#aad5ff";
			}
		}return bgcolor;
	}

	/**
	 * 要素の得点によって要素セルの背景色を決定するメソッド
	 * @param POINT 型の得点
	 * @return htmlのカラーコード
	 */
	public static String getBgcolor(int POINT){
		String bgcolor="#ffffff";
		if(POINT==1){
			bgcolor="#ffffaa";
			//bgcolor="#ffff80";
		}else if(POINT==2){
			bgcolor="#ffd5aa";
			//bgcolor="#ffbf80";
		}return bgcolor;
	}

	/**
	 * 回帰式雨量の値によって回帰式雨量セルの背景色を決定するメソッド
	 * @param R1 回帰式雨量の値
	 * @return htmlのカラーコード
	 */
	public static String getBgcolor(double R1){
		String bgcolor="#ffffff";
		if(R1>=55.&&R1<65.){
			bgcolor="#ffff00";
		}else if(R1!=9999.&&R1>=65.){
			bgcolor="#ff0000";
		}return bgcolor;
	}

	/**
	 * 背景が赤色のセルの文字を白くするメソッド
	 * @param POINT 型の得点
	 * @param type "SW"または"SE"
	 * @return htmlのカラーコード
	 */
	public static String getColor(int POINT,String type){
		String color="#000000";
		if(POINT>=6&&POINT<=8){
			color="#ffffff";
		}return color;
	}

	/**
	 * 背景が赤色のセルの文字を白くするメソッド
	 * @param R1 回帰式雨量の値
	 * @return htmlのカラーコード
	 */
	public static String getColor(double R1){
		String color="#000000";
		if(R1!=9999.&&R1>=65.){
			color="#ffffff";
		}return color;
	}

}
