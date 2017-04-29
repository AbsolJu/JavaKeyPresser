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
		
		//--���ε�����
		KeyInput.Builder keyinput = KeyInput.newBuilder();
		keyinput.setValue(keyValue);
		keyinput.setPress(isPress);
		//--��
		
		keydata.addKeyinput(keyinput);
		
		//--(�ӽ� �׽�Ʈ. ���� ���常 ��)
		
		FileOutputStream output = new FileOutputStream("output.prbuf");
		keydata.build().writeTo(output);
		output.close();
	}
	
	public void multi() {
		//���� ����. ���� Ű�� �޾� �� ���� ó��, ��ũ��?
	}
}
