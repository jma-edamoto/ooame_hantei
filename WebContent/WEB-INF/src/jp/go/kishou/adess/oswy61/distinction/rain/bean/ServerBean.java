package jp.go.kishou.adess.oswy61.distinction.rain.bean;

import java.util.List;

import jp.go.kishou.adess.oswy61.distinction.rain.DirectoryAccess;

public class ServerBean {
	private List<String> fileNameList;
	private String dir;

	public ServerBean() {
		DirectoryAccess DA=new DirectoryAccess("OSWY61");//ˆø”‚É‚ÍŠ¯ID
		dir=DA.getCSVDirectory()+"/";
		int max=8;
		fileNameList=DA.getCSVFileNameList(DirectoryAccess.MSM, max);
	}

	public String[] getFileNameList() {
		return fileNameList.toArray(new String[fileNameList.size()]);
	}

	public String getDir() {
		return dir;
	}
}
