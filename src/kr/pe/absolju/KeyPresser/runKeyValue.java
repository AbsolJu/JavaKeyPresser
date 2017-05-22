package kr.pe.absolju.KeyPresser;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import kr.pe.absolju.KeyPresser.KeyValueProtos.KeyData;
import kr.pe.absolju.KeyPresser.KeyValueProtos.KeyInput;


public class runKeyValue {
	
	private static boolean runFlag = false;

	private static class KeydataBuffer {
		private KeyData keydata;
		private boolean empty = true;
		synchronized KeyData get() {
			while(empty) {
				try {
					wait();
				} catch (InterruptedException e) {
					System.out.println("Error: 버퍼에서 값을 가져오는 중, runKeyValue.keydataBuffer.get");
				}
			}
			empty = true;
			notifyAll();
			return keydata;
		}
		synchronized void put(KeyData keydata) {
			while(!empty) {
				try {
					wait();
				} catch (InterruptedException e) {
					System.out.println("Error: 버퍼에 값을 넣는 중, runKeyValue.keydataBuffer.put");
				}
			}
			empty = false;
			this.keydata = keydata;
			notifyAll();
		}
	}
	
	private static class GetKeydata implements Runnable {
		private KeydataBuffer buffer;
		GetKeydata(KeydataBuffer buffer) {this.buffer = buffer;}
		
		@Override
		public void run() {
			ServerSocket ServSocket = null;
			try {
				ServSocket = new ServerSocket(Setting.getSocketNumber());
				while (runFlag) {
					Socket socket = ServSocket.accept();
					try {
						buffer.put(KeyData.parseFrom(new DataInputStream(socket.getInputStream())));
					} catch (IOException e) {
						System.out.println("Error: KeyData 수신 중, runKeyValue.NetworkThread");
					} finally {
						socket.close();
					}
				}
			} catch (IOException e) {
				System.out.println("Error: Socket 생성 중, runKeyValue.NetworkThread");
			} finally {
				try {
					ServSocket.close();
				} catch (IOException e) {
					System.out.println("Error: ServerSocket 닫는 중, runKeyValue.NetworkThread");
				}
			}
			
		}
	}
	
	private static class RunKeydata implements Runnable {
		private KeydataBuffer buffer;
		RunKeydata(KeydataBuffer buffer) {this.buffer = buffer;}

		@Override
		public void run() {
			while(runFlag) {
				KeyData keydata = buffer.get();

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
		
	}
	
	public static void run(boolean on) {
		KeydataBuffer buffer = new KeydataBuffer();
		
		if(on==true) {
			runFlag = true;
			new Thread(new GetKeydata(buffer)).start();
			new Thread(new RunKeydata(buffer)).start();
		} else {
			runFlag = false;
		}
	}
	
}
