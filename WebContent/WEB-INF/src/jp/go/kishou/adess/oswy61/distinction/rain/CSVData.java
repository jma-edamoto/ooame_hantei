package jp.go.kishou.adess.oswy61.distinction.rain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CSVData {
	private ArrayList<String> valueError;	//GPVÉfÅ[É^Ç…valueÇ™Ç»Ç¢Ç∆Ç´ÅAÇªÇÃkeyÇäiî[
	private Hashtable<String, String> hashtable;
	private String filename;
	private int ft;

	public CSVData(String path) {
		hashtable = new Hashtable<String,String>();
		valueError = new  ArrayList<String>();
		this.filename = new File(path).getName();

		try(Stream<String> stream = Files.lines(Paths.get(path))){
			stream.forEach(line -> {
				String[] strs = line.split(",");
				if(strs.length == 2 && !strs[0].equals("")){
					this.hashtable.put(strs[0], strs[1]);
				}else if(strs.length == 1){
					valueError.add(strs[0]);
				}
			});
		}catch(IOException e){
			e.printStackTrace();
	    }

		ft = hashtable.keySet().stream().map(key -> {
			try {
				return Integer.parseInt(key.substring(key.length() - 2));
			}catch(Exception e) {
				return 0;
			}
		}).max(Integer::compareTo).orElse(0);
	}

	public String filename() {
		return filename;
	}

	public String[] jst() {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHH").withZone(ZoneId.of("UTC"));
		ZonedDateTime time = ZonedDateTime.parse(filename.substring(3,13), inputFormatter);
		ZonedDateTime jstTime = time.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd HH");
		return IntStream.range(0, ft + 1)
			.mapToObj(i -> jstTime.plusHours(i).format(outputFormatter))
			.collect(Collectors.toList()).toArray(new String[ft]);
	}

	public int ft() {
		return ft;
	}

	public ArrayList<String> valueError(){
		return valueError;
	}

	public Stream<SwParameter> swParameters() {
		return IntStream.range(0, ft + 1).mapToObj(i -> {
			String FT = "0" + String.valueOf(i);
			FT = FT.substring(FT.length() - 2);
			return  new SwParameter(
				getHashData("U950WaSW" + FT),
				getHashData("V950WaSW" + FT),
				getHashData("T950WaSW"+ FT),
				getHashData("RH950WaSW" + FT),
				getHashData("U850WaSW" + FT),
				getHashData("V850WaSW" + FT),
				getHashData("T850WaSW"+ FT),
				getHashData("RH850WaSW" + FT),
				getHashData("T700WaSW"+ FT),
				getHashData("RH700WaSW" + FT),
				getHashData("T500WaSW"+ FT),
				getHashData("TPWSURFWaSW" + FT)
			);
		});
	}

	public Stream<SeParameter> seParameters(){
		return IntStream.range(0, ft + 1).mapToObj(i -> {
			String FT = "0" + String.valueOf(i);
			FT = FT.substring(FT.length() - 2);
			return new SeParameter(
				getHashData("U950WaSE" + FT),
				getHashData("V950WaSE" + FT),
				getHashData("T950WaSE"+ FT),
				getHashData("RH950WaSE" + FT),
				getHashData("U850WaSE" + FT),
				getHashData("V850WaSE" + FT),
				getHashData("T850WaSE"+ FT),
				getHashData("RH850WaSE" + FT),
				getHashData("V700WaSE" + FT),
				getHashData("T700WaSE"+ FT),
				getHashData("RH700WaSE" + FT),
				getHashData("T500WaSE"+ FT),
				getHashData("RH500WaSE" + FT),
				getHashData("TPWSURFWaSE" + FT)

			);
		});
	}

	private Optional<Double> getHashData(String key) {
		try {
			return Optional.of(Double.valueOf(hashtable.get(key)));
		}catch(Exception e){
			return Optional.empty();
		}
	}
}
