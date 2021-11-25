package jp.go.kishou.adess.oswy61.distinction.rain.servlet;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.LogManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jp.go.kishou.adess.oswy61.distinction.rain.Display;
import jp.go.kishou.adess.oswy61.distinction.rain.Version;
import jp.go.kishou.adess.oswy61.distinction.rain.bean.JudgeDataBean;


/**
 * 判定結果の帳票をPDF化するサーブレット
 * @author JMA70D3(JMA304Aのソースを一部改変)
 * MSMの予報時間が変更された場合はテーブルの列数（117行目及び286行目の式の右辺の引数）を変更するか、
 * テーブルの列幅（123行目及び292行目の式の右辺）と文字サイズ（75行目の式の右辺の最後の引数）を変更してください。
 */

public class PDFCreatorServlet extends HttpServlet {

	protected LogManager log;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OutputStream out;
		String fileName="result.pdf";

		//出力用のStreamをインスタンス化します。
		ByteArrayOutputStream byteout = new ByteArrayOutputStream();

		//文書オブジェクトを生成
		//ページサイズを設定します。
		Document doc = new Document(PageSize.A4.rotate(), 10, 10, 30, 10);

		try {
			PdfWriter.getInstance(doc, byteout);

			/* フォント設定部 */
			//ゴシック15pt下線付き
			Font font_underline_g15 = new Font(BaseFont.createFont("HeiseiKakuGo-W5", "UniJIS-UCS2-HW-H",BaseFont.NOT_EMBEDDED),15,Font.UNDERLINE);
			//ゴシック10pt
			Font font_g10 = new Font(BaseFont.createFont("HeiseiKakuGo-W5","UniJIS-UCS2-H",BaseFont.NOT_EMBEDDED),10);
			//明朝10pt
			Font font_m10 = new Font(BaseFont.createFont("HeiseiMin-W3", "UniJIS-UCS2-HW-H",BaseFont.NOT_EMBEDDED),10);
			//明朝5.5pt
			Font font_m06 = new Font(BaseFont.createFont("HeiseiMin-W3", "UniJIS-UCS2-HW-H",BaseFont.NOT_EMBEDDED),5.5f);
			//空白セル用フォント(非表示)
			Font font_empty = new Font(BaseFont.createFont("HeiseiKakuGo-W5","UniJIS-UCS2-H",BaseFont.NOT_EMBEDDED),9);
			font_empty.setColor(new Color(255,255,255));

			//出力するPDFに説明を付与します。
			doc.addAuthor("和歌山地方気象台");
			doc.addSubject("和歌山県大雨基本パターン判定ツール");

			//stage02.jspでsessionスコープとなったinfoを取り出します。
			HttpSession session = request.getSession();
			JudgeDataBean info = (JudgeDataBean)session.getAttribute("info");

			//ファイル名の設定をします。
			fileName = info.getFname().replaceFirst(".csv", ".pdf");


			//ヘッダーの設定をします。
			HeaderFooter header = new HeaderFooter(new Phrase(info.getFname().substring(0,16), font_underline_g15), false);
			header.setAlignment(Element.ALIGN_CENTER);
			header.setBorder(Rectangle.NO_BORDER);
			doc.setHeader(header);
			//フッターの設定をします。
			HeaderFooter footer = new HeaderFooter(new Phrase("和歌山県大雨基本パターン判定ツール ver."+Version.getVersion(),font_m10),false);
			footer.setAlignment(Element.ALIGN_RIGHT);
			footer.setBorder(Rectangle.NO_BORDER);

			doc.setFooter(footer);

			//文章の出力を開始します。
			doc.open();

			int ft = info.getFt() + 1;

			doc.add(new Paragraph("南西風系判定結果",font_g10));//タイトル文字
			doc.add(new Paragraph(" "));

			//南西風系テーブルの作成、初期設定
			PdfPTable tbl_sw = new PdfPTable(ft+1);
			tbl_sw.setWidthPercentage(100);
			//テーブル各列の幅をパーセンテージで設定します。
			float table_sw_width[] = new float[ft+1];
			table_sw_width[0]=12;
			for(int n=0; n<ft; n++){
				table_sw_width[n+1]=2.2f;
			}
			tbl_sw.setWidths(table_sw_width);

			//以下、セルの作成
			PdfPCell cell1=new PdfPCell(new Phrase("FT", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Integer.toString(n), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(cell);
			}
			 cell1=new PdfPCell(new Phrase("JST", font_m06));
			 cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			 cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(info.getJst()[n], font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(cell);
			}
			cell1=new PdfPCell(new Phrase("内陸型", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getPoint(info.getSw_pNairiku()[n],info.getSw_flag()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorPoint(cell,info.getSw_pNairiku()[n]));
			}
			cell1=new PdfPCell(new Phrase("紀中、田辺・西牟婁型", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getPoint(info.getSw_pKichu()[n],info.getSw_flag()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorPoint(cell,info.getSw_pKichu()[n]));
			}
			cell1=new PdfPCell(new Phrase("北部型", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getPoint(info.getSw_pHokubu()[n],info.getSw_flag()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorPoint(cell,info.getSw_pHokubu()[n]));
			}
			cell1=new PdfPCell(new Phrase("沿岸型", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getPoint(info.getSw_pEngan()[n],info.getSw_flag()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorPoint(cell,info.getSw_pEngan()[n]));
			}
			cell1=new PdfPCell(new Phrase("田辺・西牟婁沿岸型", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getPoint(info.getSw_pTanabe()[n],info.getSw_flag()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorPoint(cell,info.getSw_pTanabe()[n]));
			}
			cell1=new PdfPCell(new Phrase("950hPa風速[kt]", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSw_ws950()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorValue(cell,info.getSwSpeed950Score()[n]));
			}
			cell1=new PdfPCell(new Phrase("K-index", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSw_kindex()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorValue(cell,info.getSwKIndexScore()[n]));
			}
			cell1=new PdfPCell(new Phrase("SSI", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSw_ssi()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorValue(cell,info.getSwSsiScore()[n]));
			}
			cell1=new PdfPCell(new Phrase("可降水量", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSw_tpw()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorValue(cell,info.getSwTpwScore()[n]));
			}
			cell1=new PdfPCell(new Phrase("850hPa相当温位", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSw_ept850()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorValue(cell,info.getSwEpt850Score()[n]));
			}
			cell1=new PdfPCell(new Phrase("950hPa相当温位", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSw_ept950()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorValue(cell,info.getSwEpt950Score()[n]));
			}
			cell1=new PdfPCell(new Phrase("相当温位差950hPa-850hPa", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSw_eptdif()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorValue(cell,info.getSwEptDiffScore()[n]));
			}
			cell1=new PdfPCell(new Phrase("回帰式雨量", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSw_r1()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorR1(cell,info.getSw_r1()[n]));
			}
			//南西風系テーブルを追加
			doc.add(tbl_sw);

			doc.add(new Paragraph(" "));
			doc.add(new Paragraph("南東風系判定結果",font_g10));//タイトル文字
			doc.add(new Paragraph(" "));

			//南東風系テーブルの作成、初期設定
			PdfPTable tbl_se = new PdfPTable(ft+1);
			tbl_se.setWidthPercentage(100);
			//テーブル各列の幅をパーセンテージで設定します。
			float table_se_width[] = new float[ft+1];
			table_se_width[0]=12;
			for(int n=0; n<ft; n++){
				table_se_width[n+1]=2.2f;
			}
			tbl_se.setWidths(table_se_width);

			//以下、セルの作成
			 PdfPCell cell2=new PdfPCell(new Phrase("FT", font_m06));
			 cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			 cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Integer.toString(n), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(cell);
			}
			 cell2=new PdfPCell(new Phrase("JST", font_m06));
			 cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			 cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(info.getJst()[n], font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(cell);
			}
			cell2=new PdfPCell(new Phrase("東型", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getPoint(info.getSe_pHigashi()[n],info.getSe_flag()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorPoint(cell,info.getSe_pHigashi()[n]));
			}
			cell2=new PdfPCell(new Phrase("南東型", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getPoint(info.getSe_pNanto()[n],info.getSe_flag()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorPoint(cell,info.getSe_pNanto()[n]));
			}
			cell2=new PdfPCell(new Phrase("700hPa南北風[kt]", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSe_v700()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorValue(cell,info.getSeV700Score()[n]));
			}
			cell2=new PdfPCell(new Phrase("850hPa東西風[kt]", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSe_u850()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorValue(cell,info.getSeU850Score()[n]));
			}
			cell2=new PdfPCell(new Phrase("K-index", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSe_kindex()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorValue(cell,info.getSeKIndexScore()[n]));
			}
			cell2=new PdfPCell(new Phrase("SSI", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSe_ssi()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorValue(cell,info.getSeSsiScore()[n]));
			}
			cell2=new PdfPCell(new Phrase("可降水量", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSe_tpw()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorValue(cell,info.getSeTpwScore()[n]));
			}
			cell2=new PdfPCell(new Phrase("850hPa相当温位", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSe_ept850()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(cell);
			}
			cell2=new PdfPCell(new Phrase("950hPa相当温位", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSe_ept950()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorValue(cell,info.getSeEpt950Score()[n]));
			}
			cell2=new PdfPCell(new Phrase("相当温位差950hPa-850hPa", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSe_eptdif()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorValue(cell,info.getSeEptDiffScore()[n]));
			}
			cell2=new PdfPCell(new Phrase("回帰式雨量", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSe_r1()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorR1(cell,info.getSe_r1()[n]));
			}
			//南東風系テーブルを追加
			doc.add(tbl_se);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 出力を終了します
		doc.close();

		//	PDFを出力
	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename="+fileName);
	    response.setContentLength(byteout.size());
	    out = response.getOutputStream();
	    out.write(byteout.toByteArray());
	    out.close();
	}

	/**
	 * 得点セルの背景色を設定するメソッド
	 * @param cell 色を設定するセル
	 * @param POINT 得点
	 * @return 背景色設定済みのセル
	 */
	private PdfPCell setColorPoint(PdfPCell cell, int POINT) {
		if(POINT==5){
			cell.setBackgroundColor(Color.YELLOW);
		}else if(POINT>=6&&POINT<=8){
			cell.setBackgroundColor(Color.PINK);
		}
		return cell;
	}
	/**
	 * 要素セルの背景色を設定するメソッド
	 * @param cell 色を設定するセル
	 * @param VALUE 要素の値
	 * @return 背景色設定済みのセル
	 */
	private PdfPCell setColorValue(PdfPCell cell, int VALUE) {
		if(VALUE==1){
			cell.setBackgroundColor(Color.YELLOW);
		}else if(VALUE==2){
			cell.setBackgroundColor(Color.ORANGE);
		}
		return cell;
	}
	/**
	 * 回帰式雨量セルの背景色を設定するメソッド
	 * @param cell 色を設定するセル
	 * @param R1 回帰式雨量の値
	 * @return 背景色設定済みのセル
	 */
	private PdfPCell setColorR1(PdfPCell cell, double R1) {
		if(R1>=55.&&R1<65.){
			cell.setBackgroundColor(Color.YELLOW);
		}else if(R1!=9999.&&R1>=65.){
			cell.setBackgroundColor(Color.PINK);
		}
		return cell;
	}
}