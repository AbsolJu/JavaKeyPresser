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
		
		
		//임시 변수 하드코딩
		String allowSenderId = "absolju";
		
		String inputSenderId = keydata.getSenderid();
		
		if(inputSenderId.toString().equals(allowSenderId.toString())) {
			System.out.println("허용된 사용자: " + keydata.getSenderid());
		} else {
			System.out.println("허용되지 않은 사용자: " + keydata.getSenderid());
			return;
		}
		
		
		if(keydata.getMacro() == true) { //매크로 저장된 입력
			//내용 없음
		} else if(keydata.getKeyinputCount() > 0) { //키 입력이 있을 경우
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
		} else { //기타, 매크로가 아니며 키 입력도 없음
			System.out.println("입력 없음");
		}
		
	}
}
