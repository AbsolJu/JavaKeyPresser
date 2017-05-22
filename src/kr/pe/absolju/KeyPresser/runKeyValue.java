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
					System.out.println("Error: ���ۿ��� ���� �������� ��, runKeyValue.keydataBuffer.get");
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
					System.out.println("Error: ���ۿ� ���� �ִ� ��, runKeyValue.keydataBuffer.put");
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
						System.out.println("Error: KeyData ���� ��, runKeyValue.NetworkThread");
					} finally {
						socket.close();
					}
				}
			} catch (IOException e) {
				System.out.println("Error: Socket ���� ��, runKeyValue.NetworkThread");
			} finally {
				try {
					ServSocket.close();
				} catch (IOException e) {
					System.out.println("Error: ServerSocket �ݴ� ��, runKeyValue.NetworkThread");
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
