package kr.pe.absolju.KeyPresser;

import java.io.FileOutputStream;
import java.io.IOException;

import kr.pe.absolju.KeyPresser.KeyValueProtos.KeyData;
import kr.pe.absolju.KeyPresser.KeyValueProtos.KeyInput;

public class sendKeyValue {

	public void single(String senderid, int keyValue, boolean isPress) throws IOException {
		
		KeyData.Builder keydata = KeyData.newBuilder();
		
		keydata.setSenderid(senderid);
		keydata.setMacro(false);
		
		//--내부데이터
		KeyInput.Builder keyinput = KeyInput.newBuilder();
		keyinput.setValue(keyValue);
		keyinput.setPress(isPress);
		//--끝
		
		keydata.addKeyinput(keyinput);
		
		//--(임시 테스트. 파일 저장만 함)
		
		FileOutputStream output = new FileOutputStream("output.prbuf");
		keydata.build().writeTo(output);
		output.close();
	}
	
	public void multi() {
		//내용 없음. 여러 키를 받아 한 번에 처리, 매크로?
	}
}
