package kr.pe.absolju.KeyPresser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Setting {
	
	// == Value 파트 ==
	private static String fileName = "JKPset.dat"; //저장 파일 경로 및 이름
	public static String getFileName() {
		return fileName;
	}

	private static int socketNumber = 8080; //초기값
	public static int getSocketNumber() {
		return socketNumber;
	}
	public static void setSocketNumber(int socketNumber) {
		Setting.socketNumber = socketNumber;
		SaveFile(socketNumber);
	}
	
	
	
	
	// == IO 파트 ==
	public static void LoadFile() {
		int loadTempInteger = 0;
		String loadTempString;
		BufferedReader input = null;
		
		//파일을 읽어서 정수로 받아옴
		try {
			input = new BufferedReader(new FileReader(Setting.getFileName()));
			loadTempString = input.readLine();
			loadTempInteger = Integer.parseInt(loadTempString);
		} catch (IOException e) {
			System.out.println("Error: 설정 파일이 없거나 읽는 중 오류, 초기화 필요, SettingIO.Load");
			SaveFile(socketNumber);
		} finally {
			if(input!=null) {
				try {
					input.close();
				} catch (IOException e) {
					System.out.println("Error: 파일 닫는 중 오류, SettingIO.Load");
				}
			}
		}
		
		//정상 범위 확인
		if(loadTempInteger > 0 && loadTempInteger <= 65565) {
			socketNumber = loadTempInteger;
		} else {
			System.out.println("Error: Port 범위 초과, 초기값 사용, SettingIO.Load");
		}
	}
	
	public static void SaveFile(int socketPort) {
		//정상 범위 확인 후 저장
		if(socketPort > 0 && socketPort <= 65565) {
			FileWriter output = null;
			
			try {
				output = new FileWriter(Setting.getFileName());
				output.write(String.valueOf(socketPort));
			} catch (FileNotFoundException e) {
				System.out.println("Error: 파일 생성 중 오류, SettingIO.Save");
			} catch (IOException e) {
				System.out.println("Error: 파일 쓰는 중 오류, SettingIO.Save");
				//저장하지 못했다는 알림창 필요
			} finally {
				if(output!=null) {
					try {
						output.close();
					} catch (IOException e) {
						System.out.println("Error: 파일 닫는 중 오류, SettingIO.Save");
					}
				}
			}
		} else {
			System.out.println("Error: Port 범위 초과, SettingIO.Save");
		}
	}
	
}
