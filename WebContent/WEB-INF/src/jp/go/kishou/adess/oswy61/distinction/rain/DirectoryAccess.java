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
 * 基本パターン判別ソフトの作業ディレクトリに関するクラスです
 * @author 大阪管区気象台通信課
 * @version 201/04/28
 */
public class DirectoryAccess {

	/**	GPV種別でMSMを意味する文字列です	*/
	public static final String MSM = "MSM";

	/**	GPV種別でGSMを意味する文字列です	*/
	public static final String GSM = "GSM";

	/**	基本パターン判別ソフトのディレクトリです	*/
	private static final String DISTINCTION_DIR = "distinction";

	/**	作業ディレクトリを取得する官署のIDです	*/
	private static final String OSAKA_TSN_ID = "OSYS34";

	/**
	 * GSM、MSMのCSVファイルをフィルタリングするクラスです
	 */
	private class CSVFilter implements FilenameFilter {
		//	CSVファイル名のパターン（ex:MSM2014042103UTC.csv）
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

	/**	官署ID（ex:OSYS31）	*/
	final String kansyoId;

	/**
	 * コンストラクタ
	 * @param kansyoId 官署ID（例:OSYS31）
	 */
	public DirectoryAccess(String kanshoID){
		this.kansyoId = kanshoID;
	}

	/**
	 * 基本パターン判別ソフトの官署毎の作業ディレクトリを返します<br>
	 * エラー発生時はnullを返します
	 * @return 作業ディレクトリ
	 */
	public String getCSVDirectory(){
		return getWorkDirectory();
	}

	/**
	 * 基本パターン判別ソフトの官署毎の作業ディレクトリを返します<br>
	 * エラー発生時はnullを返します
	 * @return 作業ディレクトリ
	 */
	public String getWorkDirectory(){
		try {
			//	コンストラクタに与えられた官署IDが正しいか確認
			//	異常であれば例外が発生し、nullを返す
			CommonClassFactory.getWorkDirectory(kansyoId.toLowerCase());

			//	作業ディレクトリの作成
			StringBuilder buf = new StringBuilder(CommonClassFactory.getWorkDirectory(OSAKA_TSN_ID));
			buf.append("/").append(DISTINCTION_DIR).append("/").append(kansyoId.toLowerCase());

			//	もし存在しなければ作成
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
	 * 官署CSVファイル名のリストを取得します<br>
	 * 取得したファイル名のリストは日時で降順されています<br>
	 * エラー発生時は、空（Empty）のリストを返します
	 * @param gpv GPV種別
	 * @return CSVファイル名のリスト
	 */
	public List<String> getCSVFileNameList(String gpv){

		try{
			//	ファイル取得
			File f = new File(getWorkDirectory());
			File[] files = f.listFiles(new CSVFilter(gpv));
			List<String> ls = new ArrayList<String>();
			for(File file : files){
				ls.add(file.getName());
			}

			//	降順ソート
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
	 * 官署CSVファイル名のリストを取得します<br>
	 * 取得したファイル名のリストは日時で降順されています<br>
	 * エラー発生時は、空（Empty）のリストを返します
	 * @param gpv GPV種別
	 * @param max 最大数取得数
	 * @return CSVファイル名のリスト
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
	 * 官署CSVファイル名のリストを取得します<br>
	 * 取得したファイル名のリストは日時で降順されています<br>
	 * エラー発生時は、空（Empty）のリストを返します
	 * @param gpv GPV種別
	 * @param period 取得する期間（日）
	 * @param initial 取得する初期時刻の配列
	 * @return CSVファイル名のリスト
	 */
	public List<String> getCSVFileNameList(String gpv, int period, int[] initial){
		final SimpleDateFormat dateFmt = new SimpleDateFormat("'.*'yyyyMMdd'.*'");
		dateFmt.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		final DecimalFormat endFmt = new DecimalFormat("00'UTC.csv'");
		List<String> ls = getCSVFileNameList(gpv);
		if(!ls.isEmpty()){
			//	対象日のパターンを作成
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

			//	該当する初期時刻のパターンを作成
			sb = new StringBuilder();
			for(int ini : initial){
				if(sb.length()!=0){
					sb.append("|");
				}
				sb.append(endFmt.format(ini));
			}
			String endPattern = sb.toString();

			//	取得したファイル名が初期時刻のパターンに該当するか
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
