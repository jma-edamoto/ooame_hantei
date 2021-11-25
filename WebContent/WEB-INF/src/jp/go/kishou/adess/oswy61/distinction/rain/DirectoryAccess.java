package jp.go.kishou.adess.oswy61.distinction.rain;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import jp.go.kishou.adess.common.CommonClassFactory;

/**
 * ��{�p�^�[�����ʃ\�t�g�̍�ƃf�B���N�g���Ɋւ���N���X�ł�
 * @author ���ǋ�C�ۑ�ʐM��
 * @version 201/04/28
 */
public class DirectoryAccess {

	/**	GPV��ʂ�MSM���Ӗ����镶����ł�	*/
	public static final String MSM = "MSM";

	/**	GPV��ʂ�GSM���Ӗ����镶����ł�	*/
	public static final String GSM = "GSM";

	/**	��{�p�^�[�����ʃ\�t�g�̃f�B���N�g���ł�	*/
	private static final String DISTINCTION_DIR = "distinction";

	/**	��ƃf�B���N�g�����擾���銯����ID�ł�	*/
	private static final String OSAKA_TSN_ID = "OSYS34";

	/**
	 * GSM�AMSM��CSV�t�@�C�����t�B���^�����O����N���X�ł�
	 */
	private class CSVFilter implements FilenameFilter {
		//	CSV�t�@�C�����̃p�^�[���iex:MSM2014042103UTC.csv�j
		final String PATTERN = "[A-Z]{3}[0-9]{10}UTC[.]csv";
		final String gpv;
		public CSVFilter(String gpv){
			this.gpv = gpv;
		}
		@Override
		public boolean accept(File dir, String name) {
			if(name.matches(PATTERN) && name.startsWith(gpv)){
				return true;
			}else{
				return false;
			}
		}
	}

	/**	����ID�iex:OSYS31�j	*/
	final String kansyoId;

	/**
	 * �R���X�g���N�^
	 * @param kansyoId ����ID�i��:OSYS31�j
	 */
	public DirectoryAccess(String kanshoID){
		this.kansyoId = kanshoID;
	}

	/**
	 * ��{�p�^�[�����ʃ\�t�g�̊������̍�ƃf�B���N�g����Ԃ��܂�<br>
	 * �G���[��������null��Ԃ��܂�
	 * @return ��ƃf�B���N�g��
	 */
	public String getCSVDirectory(){
		return getWorkDirectory();
	}

	/**
	 * ��{�p�^�[�����ʃ\�t�g�̊������̍�ƃf�B���N�g����Ԃ��܂�<br>
	 * �G���[��������null��Ԃ��܂�
	 * @return ��ƃf�B���N�g��
	 */
	public String getWorkDirectory(){
		try {
			//	�R���X�g���N�^�ɗ^����ꂽ����ID�����������m�F
			//	�ُ�ł���Η�O���������Anull��Ԃ�
			CommonClassFactory.getWorkDirectory(kansyoId.toLowerCase());

			//	��ƃf�B���N�g���̍쐬
			StringBuilder buf = new StringBuilder(CommonClassFactory.getWorkDirectory(OSAKA_TSN_ID));
			buf.append("/").append(DISTINCTION_DIR).append("/").append(kansyoId.toLowerCase());

			//	�������݂��Ȃ���΍쐬
			File f = new File(buf.toString());
			if(!f.exists()){
				f.mkdirs();
			}

			return buf.toString();

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ����CSV�t�@�C�����̃��X�g���擾���܂�<br>
	 * �擾�����t�@�C�����̃��X�g�͓����ō~������Ă��܂�<br>
	 * �G���[�������́A��iEmpty�j�̃��X�g��Ԃ��܂�
	 * @param gpv GPV���
	 * @return CSV�t�@�C�����̃��X�g
	 */
	public List<String> getCSVFileNameList(String gpv){

		try{
			//	�t�@�C���擾
			File f = new File(getWorkDirectory());
			File[] files = f.listFiles(new CSVFilter(gpv));
			List<String> ls = new ArrayList<String>();
			for(File file : files){
				ls.add(file.getName());
			}

			//	�~���\�[�g
			Collections.sort(ls, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2)*-1;
				}
			});
			return ls;
		}catch(Exception e){
			return Collections.emptyList();
		}
	}

	/**
	 * ����CSV�t�@�C�����̃��X�g���擾���܂�<br>
	 * �擾�����t�@�C�����̃��X�g�͓����ō~������Ă��܂�<br>
	 * �G���[�������́A��iEmpty�j�̃��X�g��Ԃ��܂�
	 * @param gpv GPV���
	 * @param max �ő吔�擾��
	 * @return CSV�t�@�C�����̃��X�g
	 */
	public List<String> getCSVFileNameList(String gpv, int max){
		List<String> ls = getCSVFileNameList(gpv);
		if(ls.size()>max){
			for(int i=max; i<ls.size();){
				ls.remove(max);
			}
		}
		return ls;
	}

	/**
	 * ����CSV�t�@�C�����̃��X�g���擾���܂�<br>
	 * �擾�����t�@�C�����̃��X�g�͓����ō~������Ă��܂�<br>
	 * �G���[�������́A��iEmpty�j�̃��X�g��Ԃ��܂�
	 * @param gpv GPV���
	 * @param period �擾������ԁi���j
	 * @param initial �擾���鏉�������̔z��
	 * @return CSV�t�@�C�����̃��X�g
	 */
	public List<String> getCSVFileNameList(String gpv, int period, int[] initial){
		final SimpleDateFormat dateFmt = new SimpleDateFormat("'.*'yyyyMMdd'.*'");
		dateFmt.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		final DecimalFormat endFmt = new DecimalFormat("00'UTC.csv'");
		List<String> ls = getCSVFileNameList(gpv);
		if(!ls.isEmpty()){
			//	�Ώۓ��̃p�^�[�����쐬
			StringBuilder sb = new StringBuilder();
			Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("Etc/UTC"));
			for(int d=0; d<period; d++){
				if(sb.length()!=0){
					sb.append("|");
				}
				sb.append(dateFmt.format(cal.getTime()));
				cal.add(Calendar.DAY_OF_MONTH, -1);
			}
			String dayPttern = sb.toString();

			//	�Y�����鏉�������̃p�^�[�����쐬
			sb = new StringBuilder();
			for(int ini : initial){
				if(sb.length()!=0){
					sb.append("|");
				}
				sb.append(endFmt.format(ini));
			}
			String endPattern = sb.toString();

			//	�擾�����t�@�C���������������̃p�^�[���ɊY�����邩
			List<String> ls2 = new ArrayList<String>();
			for(Iterator<String> ite = ls.iterator(); ite.hasNext();){
				String fileName = ite.next();
				String endStr = fileName.substring(11);
				if(fileName.matches(dayPttern) && endStr.matches(endPattern)){
					ls2.add(fileName);
				}
			}
			return ls2;
		}else{
			return ls;
		}
	}
}
