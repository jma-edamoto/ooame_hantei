package jp.go.kishou.adess.oswy61.distinction.rain;

/**
 * ���茋�ʂ̃u���E�U�\���ɗp���镶�����J���[�R�[�h�𐶐�
 * @author JMA70D3
 */

public class Display {

	/**
	 * �^�̓��_�ɂ���Č^�̓��_�Z���ɕ\�������镶��������肷�郁�\�b�h
	 * @param POINT �^�̓��_
	 * @return ���_�𕶎���ɕϊ��������́i�܂��͋󔒁A�u-�v�j
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
	 * �^�̓��_�ɂ���Č^�̓��_�Z���ɕ\�������镶��������肷�郁�\�b�h
	 * @param POINT �^�̓��_
	 * @param FLAG ����ɗp����v�f�ɔ��������邩�Ȃ����������t���O;sw_flag��se_flag
	 * @return ���_�𕶎���ɕϊ��������́i�܂��͋󔒁A�u-�v�j
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
	 * �v�f�̓��_�ɂ���ėv�f�Z���ɕ\�������镶��������肷�郁�\�b�h
	 * @param POINT �v�f�̓��_
	 * @param VALUE �v�f�̒l�iint�j
	 * @return �v�f�̒l�𕶎���ɕϊ��������́i�܂��͋󔒁j
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
	 * �v�f�̓��_�ɂ���ėv�f�Z���ɕ\�������镶��������肷�郁�\�b�h
	 * @param POINT �v�f�̓��_
	 * @param VALUE �v�f�̒l�idouble�j
	 * @return �v�f�̒l�𕶎���ɕϊ��������́i�܂��͋󔒁j
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
	 * �v�f�̒l�ɂ���ėv�f�Z���ɕ\�������镶��������肷�郁�\�b�h
	 * @param VALUE �v�f�̒l�iint�j
	 * @return �v�f�̒l�𕶎���ɕϊ��������́i�܂��͋󔒁j
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
	 * �v�f�̒l�ɂ���ėv�f�Z���ɕ\�������镶��������肷�郁�\�b�h
	 * @param VALUE �v�f�̒l�idouble�j
	 * @return �v�f�̒l�𕶎���ɕϊ��������́i�܂��͋󔒁j
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
	 * �^�̓��_�ɂ���Č^�̓��_�Z���̔w�i�F�����肷�郁�\�b�h
	 * @param POINT �^�̓��_
	 * @param type "SW"�܂���"SE"
	 * @return html�̃J���[�R�[�h
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
	 * �v�f�̓��_�ɂ���ėv�f�Z���̔w�i�F�����肷�郁�\�b�h
	 * @param POINT �^�̓��_
	 * @return html�̃J���[�R�[�h
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
	 * ��A���J�ʂ̒l�ɂ���ĉ�A���J�ʃZ���̔w�i�F�����肷�郁�\�b�h
	 * @param R1 ��A���J�ʂ̒l
	 * @return html�̃J���[�R�[�h
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
	 * �w�i���ԐF�̃Z���̕����𔒂����郁�\�b�h
	 * @param POINT �^�̓��_
	 * @param type "SW"�܂���"SE"
	 * @return html�̃J���[�R�[�h
	 */
	public static String getColor(int POINT,String type){
		String color="#000000";
		if(POINT>=6&&POINT<=8){
			color="#ffffff";
		}return color;
	}

	/**
	 * �w�i���ԐF�̃Z���̕����𔒂����郁�\�b�h
	 * @param R1 ��A���J�ʂ̒l
	 * @return html�̃J���[�R�[�h
	 */
	public static String getColor(double R1){
		String color="#000000";
		if(R1!=9999.&&R1>=65.){
			color="#ffffff";
		}return color;
	}

}
