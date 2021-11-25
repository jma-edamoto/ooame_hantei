package jp.go.kishou.adess.oswy61.distinction.rain.bean;

import java.util.List;
import java.util.stream.Collectors;

import jp.go.kishou.adess.oswy61.distinction.rain.CSVData;
import jp.go.kishou.adess.oswy61.distinction.rain.SeJudgeData;
import jp.go.kishou.adess.oswy61.distinction.rain.SwJudgeData;

/**
 * 基本パターン判定に必要なデータを格納するためのJavaBeans
 * @author JMA8000
 */
public class JudgeDataBean {
	CSVData csvData;
	List<SwJudgeData> swJudgeData;
	List<SeJudgeData> seJudgeData;

	public void init(String path){
		csvData = new CSVData(path);
		swJudgeData = csvData.swParameters().map(parameter -> new SwJudgeData(parameter)).collect(Collectors.toList());
		seJudgeData = csvData.seParameters().map(parameter -> new SeJudgeData(parameter)).collect(Collectors.toList());
	}

	public String	getFname(){
		return csvData.filename();
	}

	public int getFt() {
		return csvData.ft();
	}

	public String[]	getJst(){
		return csvData.jst();
	}

	//南西風系用データ
	public int[] getSw_ws950() {
		return swJudgeData.stream().mapToInt(data -> data.speed950).toArray();
	}

	public double[]	getSw_kindex(){
		return swJudgeData.stream().mapToDouble(data -> data.kIndex).toArray();
	}

	public double[]	getSw_ssi(){
		return swJudgeData.stream().mapToDouble(data -> data.ssi).toArray();
	}

	public double[]	getSw_tpw(){
		return swJudgeData.stream().mapToDouble(data -> data.tpw).toArray();
	}

	public int[] getSw_ept950(){
		return swJudgeData.stream().mapToInt(data -> data.ept950).toArray();
	}

	public int[] getSw_ept850(){
		return swJudgeData.stream().mapToInt(data -> data.ept850).toArray();
	}

	public int	[] getSw_eptdif(){
		return swJudgeData.stream().mapToInt(data -> data.eptDiff).toArray();
	}

	public double[] getSw_r1(){
		return swJudgeData.stream().mapToDouble(data -> data.r1).toArray();
	}

	public int[] getSwSpeed950Score() {
		return swJudgeData.stream().mapToInt(data -> data.score.speed950Score).toArray();
	};

	public int[] getSwKIndexScore() {
		return swJudgeData.stream().mapToInt(data -> data.score.kIndexScore).toArray();
	};

	public int[] getSwSsiScore() {
		return swJudgeData.stream().mapToInt(data -> data.score.ssiScore).toArray();
	};

	public int[] getSwTpwScore() {
		return swJudgeData.stream().mapToInt(data -> data.score.tpwScore).toArray();
	};

	public int[] getSwEpt950Score() {
		return swJudgeData.stream().mapToInt(data -> data.score.ept950Score).toArray();
	};

	public int[] getSwEpt850Score() {
		return swJudgeData.stream().mapToInt(data -> data.score.ept850Score).toArray();
	};

	public int[] getSwEptDiffScore() {
		return swJudgeData.stream().mapToInt(data -> data.score.eptDiffScore).toArray();
	};

	public int[] getSw_pNairiku(){
		return swJudgeData.stream().mapToInt(data -> data.nairikuScoreSum).toArray();
	}

	public int[] getSw_pKichu(){
		return swJudgeData.stream().mapToInt(data -> data.kichuScoreSum).toArray();
	}

	public int[] getSw_pHokubu(){
		return swJudgeData.stream().mapToInt(data -> data.hokubuScoreSum).toArray();
	}

	public int[] getSw_pEngan(){
		return swJudgeData.stream().mapToInt(data -> data.enganScoreSum).toArray();
	}

	public int[] getSw_pTanabe(){
		return swJudgeData.stream().mapToInt(data -> data.tanabeScoreSum).toArray();
	}

	public Boolean[] getSw_flag(){
		return swJudgeData.stream().map(data -> data.isValidData).toArray(Boolean[]::new);
	}

	//南東風系用データ
	public double[]	getSe_v700(){
		return seJudgeData.stream().mapToDouble(data -> data.v700).toArray();
	}

	public double[]	getSe_u850(){
		return seJudgeData.stream().mapToDouble(data -> data.u850).toArray();
	}

	public double[] getSe_kindex(){
		return seJudgeData.stream().mapToDouble(data -> data.kIndex).toArray();
	}

	public double[]	getSe_ssi(){
		return seJudgeData.stream().mapToDouble(data -> data.ssi).toArray();
	}

	public double[] getSe_tpw(){
		return seJudgeData.stream().mapToDouble(data -> data.tpw).toArray();
	}

	public int[] getSe_ept950(){
		return seJudgeData.stream().mapToInt(data -> data.ept950).toArray();
	}

	public int[] getSe_ept850(){
		return seJudgeData.stream().mapToInt(data -> data.ept850).toArray();
	}

	public int[] getSe_eptdif(){
		return seJudgeData.stream().mapToInt(data -> data.eptDiff).toArray();
	}

	public double[] getSe_r1(){
		return seJudgeData.stream().mapToDouble(data -> data.r1).toArray();
	}

	public int[] getSe_pHigashi(){
		return seJudgeData.stream().mapToInt(data -> data.higashiScoreSum).toArray();
	}

	public int[] getSe_pNanto(){
		return seJudgeData.stream().mapToInt(data -> data.nantouScoreSum).toArray();
	}

	public Boolean[]	getSe_flag(){
		return seJudgeData.stream().map(data -> data.isValidData).toArray(Boolean[]::new);
	}

	public int[] getSeV700Score() {
		return seJudgeData.stream().mapToInt(data -> data.score.v700Score).toArray();
	}

	public int[] getSeU850Score() {
		return seJudgeData.stream().mapToInt(data -> data.score.u850Score).toArray();
	}

	public int[] getSeKIndexScore() {
		return seJudgeData.stream().mapToInt(data -> data.score.kIndexScore).toArray();
	}

	public int[] getSeSsiScore() {
		return seJudgeData.stream().mapToInt(data -> data.score.ssiScore).toArray();
	}

	public int[] getSeTpwScore() {
		return seJudgeData.stream().mapToInt(data -> data.score.tpwScore).toArray();
	};

	public int[] getSeEpt950Score() {
		return seJudgeData.stream().mapToInt(data -> data.score.ept950Score).toArray();
	};

	public int[] getSeEptDiffScore() {
		return seJudgeData.stream().mapToInt(data -> data.score.eptDiffScore).toArray();
	};
}
