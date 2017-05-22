package kr.pe.absolju.KeyPresser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Setting {
	
	// == Value ��Ʈ ==
	private static String fileName = "JKPset.dat"; //���� ���� ��� �� �̸�
	public static String getFileName() {
		return fileName;
	}

	private static int socketNumber = 8080; //�ʱⰪ
	public static int getSocketNumber() {
		return socketNumber;
	}
	public static void setSocketNumber(int socketNumber) {
		Setting.socketNumber = socketNumber;
		SaveFile(socketNumber);
	}
	
	
	
	
	// == IO ��Ʈ ==
	public static void LoadFile() {
		int loadTempInteger = 0;
		String loadTempString;
		BufferedReader input = null;
		
		//������ �о ������ �޾ƿ�
		try {
			input = new BufferedReader(new FileReader(Setting.getFileName()));
			loadTempString = input.readLine();
			loadTempInteger = Integer.parseInt(loadTempString);
		} catch (IOException e) {
			System.out.println("Error: ���� ������ ���ų� �д� �� ����, �ʱ�ȭ �ʿ�, SettingIO.Load");
			SaveFile(socketNumber);
		} finally {
			if(input!=null) {
				try {
					input.close();
				} catch (IOException e) {
					System.out.println("Error: ���� �ݴ� �� ����, SettingIO.Load");
				}
			}
		}
		
		//���� ���� Ȯ��
		if(loadTempInteger > 0 && loadTempInteger <= 65565) {
			socketNumber = loadTempInteger;
		} else {
			System.out.println("Error: Port ���� �ʰ�, �ʱⰪ ���, SettingIO.Load");
		}
	}
	
	public static void SaveFile(int socketPort) {
		//���� ���� Ȯ�� �� ����
		if(socketPort > 0 && socketPort <= 65565) {
			FileWriter output = null;
			
			try {
				output = new FileWriter(Setting.getFileName());
				output.write(String.valueOf(socketPort));
			} catch (FileNotFoundException e) {
				System.out.println("Error: ���� ���� �� ����, SettingIO.Save");
			} catch (IOException e) {
				System.out.println("Error: ���� ���� �� ����, SettingIO.Save");
				//�������� ���ߴٴ� �˸�â �ʿ�
			} finally {
				if(output!=null) {
					try {
						output.close();
					} catch (IOException e) {
						System.out.println("Error: ���� �ݴ� �� ����, SettingIO.Save");
					}
				}
			}
		} else {
			System.out.println("Error: Port ���� �ʰ�, SettingIO.Save");
		}
	}
	
}
