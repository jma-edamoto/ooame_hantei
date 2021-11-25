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
 * ���茋�ʂ̒��[��PDF������T�[�u���b�g
 * @author JMA70D3(JMA304A�̃\�[�X���ꕔ����)
 * MSM�̗\�񎞊Ԃ��ύX���ꂽ�ꍇ�̓e�[�u���̗񐔁i117�s�ڋy��286�s�ڂ̎��̉E�ӂ̈����j��ύX���邩�A
 * �e�[�u���̗񕝁i123�s�ڋy��292�s�ڂ̎��̉E�Ӂj�ƕ����T�C�Y�i75�s�ڂ̎��̉E�ӂ̍Ō�̈����j��ύX���Ă��������B
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

		//�o�͗p��Stream���C���X�^���X�����܂��B
		ByteArrayOutputStream byteout = new ByteArrayOutputStream();

		//�����I�u�W�F�N�g�𐶐�
		//�y�[�W�T�C�Y��ݒ肵�܂��B
		Document doc = new Document(PageSize.A4.rotate(), 10, 10, 30, 10);

		try {
			PdfWriter.getInstance(doc, byteout);

			/* �t�H���g�ݒ蕔 */
			//�S�V�b�N15pt�����t��
			Font font_underline_g15 = new Font(BaseFont.createFont("HeiseiKakuGo-W5", "UniJIS-UCS2-HW-H",BaseFont.NOT_EMBEDDED),15,Font.UNDERLINE);
			//�S�V�b�N10pt
			Font font_g10 = new Font(BaseFont.createFont("HeiseiKakuGo-W5","UniJIS-UCS2-H",BaseFont.NOT_EMBEDDED),10);
			//����10pt
			Font font_m10 = new Font(BaseFont.createFont("HeiseiMin-W3", "UniJIS-UCS2-HW-H",BaseFont.NOT_EMBEDDED),10);
			//����5.5pt
			Font font_m06 = new Font(BaseFont.createFont("HeiseiMin-W3", "UniJIS-UCS2-HW-H",BaseFont.NOT_EMBEDDED),5.5f);
			//�󔒃Z���p�t�H���g(��\��)
			Font font_empty = new Font(BaseFont.createFont("HeiseiKakuGo-W5","UniJIS-UCS2-H",BaseFont.NOT_EMBEDDED),9);
			font_empty.setColor(new Color(255,255,255));

			//�o�͂���PDF�ɐ�����t�^���܂��B
			doc.addAuthor("�a�̎R�n���C�ۑ�");
			doc.addSubject("�a�̎R����J��{�p�^�[������c�[��");

			//stage02.jsp��session�X�R�[�v�ƂȂ���info�����o���܂��B
			HttpSession session = request.getSession();
			JudgeDataBean info = (JudgeDataBean)session.getAttribute("info");

			//�t�@�C�����̐ݒ�����܂��B
			fileName = info.getFname().replaceFirst(".csv", ".pdf");


			//�w�b�_�[�̐ݒ�����܂��B
			HeaderFooter header = new HeaderFooter(new Phrase(info.getFname().substring(0,16), font_underline_g15), false);
			header.setAlignment(Element.ALIGN_CENTER);
			header.setBorder(Rectangle.NO_BORDER);
			doc.setHeader(header);
			//�t�b�^�[�̐ݒ�����܂��B
			HeaderFooter footer = new HeaderFooter(new Phrase("�a�̎R����J��{�p�^�[������c�[�� ver."+Version.getVersion(),font_m10),false);
			footer.setAlignment(Element.ALIGN_RIGHT);
			footer.setBorder(Rectangle.NO_BORDER);

			doc.setFooter(footer);

			//���͂̏o�͂��J�n���܂��B
			doc.open();

			int ft = info.getFt() + 1;

			doc.add(new Paragraph("�쐼���n���茋��",font_g10));//�^�C�g������
			doc.add(new Paragraph(" "));

			//�쐼���n�e�[�u���̍쐬�A�����ݒ�
			PdfPTable tbl_sw = new PdfPTable(ft+1);
			tbl_sw.setWidthPercentage(100);
			//�e�[�u���e��̕����p�[�Z���e�[�W�Őݒ肵�܂��B
			float table_sw_width[] = new float[ft+1];
			table_sw_width[0]=12;
			for(int n=0; n<ft; n++){
				table_sw_width[n+1]=2.2f;
			}
			tbl_sw.setWidths(table_sw_width);

			//�ȉ��A�Z���̍쐬
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
			cell1=new PdfPCell(new Phrase("�����^", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getPoint(info.getSw_pNairiku()[n],info.getSw_flag()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorPoint(cell,info.getSw_pNairiku()[n]));
			}
			cell1=new PdfPCell(new Phrase("�I���A�c�ӁE�����K�^", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getPoint(info.getSw_pKichu()[n],info.getSw_flag()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorPoint(cell,info.getSw_pKichu()[n]));
			}
			cell1=new PdfPCell(new Phrase("�k���^", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getPoint(info.getSw_pHokubu()[n],info.getSw_flag()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorPoint(cell,info.getSw_pHokubu()[n]));
			}
			cell1=new PdfPCell(new Phrase("���݌^", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getPoint(info.getSw_pEngan()[n],info.getSw_flag()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorPoint(cell,info.getSw_pEngan()[n]));
			}
			cell1=new PdfPCell(new Phrase("�c�ӁE�����K���݌^", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getPoint(info.getSw_pTanabe()[n],info.getSw_flag()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorPoint(cell,info.getSw_pTanabe()[n]));
			}
			cell1=new PdfPCell(new Phrase("950hPa����[kt]", font_m06));
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
			cell1=new PdfPCell(new Phrase("�~����", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSw_tpw()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorValue(cell,info.getSwTpwScore()[n]));
			}
			cell1=new PdfPCell(new Phrase("850hPa��������", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSw_ept850()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorValue(cell,info.getSwEpt850Score()[n]));
			}
			cell1=new PdfPCell(new Phrase("950hPa��������", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSw_ept950()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorValue(cell,info.getSwEpt950Score()[n]));
			}
			cell1=new PdfPCell(new Phrase("�������ʍ�950hPa-850hPa", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSw_eptdif()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorValue(cell,info.getSwEptDiffScore()[n]));
			}
			cell1=new PdfPCell(new Phrase("��A���J��", font_m06));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_sw.addCell(cell1);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSw_r1()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_sw.addCell(setColorR1(cell,info.getSw_r1()[n]));
			}
			//�쐼���n�e�[�u����ǉ�
			doc.add(tbl_sw);

			doc.add(new Paragraph(" "));
			doc.add(new Paragraph("�쓌���n���茋��",font_g10));//�^�C�g������
			doc.add(new Paragraph(" "));

			//�쓌���n�e�[�u���̍쐬�A�����ݒ�
			PdfPTable tbl_se = new PdfPTable(ft+1);
			tbl_se.setWidthPercentage(100);
			//�e�[�u���e��̕����p�[�Z���e�[�W�Őݒ肵�܂��B
			float table_se_width[] = new float[ft+1];
			table_se_width[0]=12;
			for(int n=0; n<ft; n++){
				table_se_width[n+1]=2.2f;
			}
			tbl_se.setWidths(table_se_width);

			//�ȉ��A�Z���̍쐬
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
			cell2=new PdfPCell(new Phrase("���^", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getPoint(info.getSe_pHigashi()[n],info.getSe_flag()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorPoint(cell,info.getSe_pHigashi()[n]));
			}
			cell2=new PdfPCell(new Phrase("�쓌�^", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getPoint(info.getSe_pNanto()[n],info.getSe_flag()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorPoint(cell,info.getSe_pNanto()[n]));
			}
			cell2=new PdfPCell(new Phrase("700hPa��k��[kt]", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSe_v700()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorValue(cell,info.getSeV700Score()[n]));
			}
			cell2=new PdfPCell(new Phrase("850hPa������[kt]", font_m06));
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
			cell2=new PdfPCell(new Phrase("�~����", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSe_tpw()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorValue(cell,info.getSeTpwScore()[n]));
			}
			cell2=new PdfPCell(new Phrase("850hPa��������", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSe_ept850()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(cell);
			}
			cell2=new PdfPCell(new Phrase("950hPa��������", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSe_ept950()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorValue(cell,info.getSeEpt950Score()[n]));
			}
			cell2=new PdfPCell(new Phrase("�������ʍ�950hPa-850hPa", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSe_eptdif()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorValue(cell,info.getSeEptDiffScore()[n]));
			}
			cell2=new PdfPCell(new Phrase("��A���J��", font_m06));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tbl_se.addCell(cell2);
			for(int n=0; n<ft; n++){
				PdfPCell cell = new PdfPCell(new Phrase(Display.getValue(info.getSe_r1()[n]), font_m06));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tbl_se.addCell(setColorR1(cell,info.getSe_r1()[n]));
			}
			//�쓌���n�e�[�u����ǉ�
			doc.add(tbl_se);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// �o�͂��I�����܂�
		doc.close();

		//	PDF���o��
	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename="+fileName);
	    response.setContentLength(byteout.size());
	    out = response.getOutputStream();
	    out.write(byteout.toByteArray());
	    out.close();
	}

	/**
	 * ���_�Z���̔w�i�F��ݒ肷�郁�\�b�h
	 * @param cell �F��ݒ肷��Z��
	 * @param POINT ���_
	 * @return �w�i�F�ݒ�ς݂̃Z��
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
	 * �v�f�Z���̔w�i�F��ݒ肷�郁�\�b�h
	 * @param cell �F��ݒ肷��Z��
	 * @param VALUE �v�f�̒l
	 * @return �w�i�F�ݒ�ς݂̃Z��
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
	 * ��A���J�ʃZ���̔w�i�F��ݒ肷�郁�\�b�h
	 * @param cell �F��ݒ肷��Z��
	 * @param R1 ��A���J�ʂ̒l
	 * @return �w�i�F�ݒ�ς݂̃Z��
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