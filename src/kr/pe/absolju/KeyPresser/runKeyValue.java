package kr.pe.absolju.KeyPresser;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import kr.pe.absolju.KeyPresser.KeyValueProtos.KeyData;
import kr.pe.absolju.KeyPresser.KeyValueProtos.KeyInput;

public class runKeyValue {
	
	public void run() throws IOException {
		
		KeyData keydata = KeyData.parseFrom(new FileInputStream("output.prbuf"));
		
		
		//�ӽ� ���� �ϵ��ڵ�
		String allowSenderId = "absolju";
		
		String inputSenderId = keydata.getSenderid();
		
		if(inputSenderId.toString().equals(allowSenderId.toString())) {
			System.out.println("���� �����: " + keydata.getSenderid());
		} else {
			System.out.println("������ ���� �����: " + keydata.getSenderid());
			return;
		}
		
		
		if(keydata.getMacro() == true) { //��ũ�� ����� �Է�
			//���� ����
		} else if(keydata.getKeyinputCount() > 0) { //Ű �Է��� ���� ���
			try {
				List<KeyInput> keyList = keydata.getKeyinputList();
				Robot robot = new Robot();
				
				for(KeyInput nowKey : keyList) {
					if(nowKey.getPress()) {
						robot.keyPress(nowKey.getValue());
					} else {
						robot.keyRelease(nowKey.getValue());
					}
				}
			} catch (AWTException e) {
				e.printStackTrace();
			}
		} else { //��Ÿ, ��ũ�ΰ� �ƴϸ� Ű �Էµ� ����
			System.out.println("�Է� ����");
		}
		
	}
}
