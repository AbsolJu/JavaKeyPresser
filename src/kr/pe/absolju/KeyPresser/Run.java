package kr.pe.absolju.KeyPresser;

import java.io.IOException;

import com.sun.glass.events.KeyEvent;

public class Run {

	public static void main(String[] args) {
		sendKeyValue key = new sendKeyValue();
		runKeyValue run = new runKeyValue();
		
		/*
		 * �����δ� ������ ���� �����ų� �� ������ ������ �����ؼ� UTP�� ��Ŷ ����.
		 * �޴� ���� �� ������ ������ �����ؼ� �ؼ�.
		 */
		
		try {
			//������ Ű ���� �� ��
			key.single("absolju", KeyEvent.VK_WINDOWS, true);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			key.single("absolju", KeyEvent.VK_WINDOWS, false);
			run.run();
			
			//���
			try { Thread.sleep(1000); } catch (InterruptedException e) { }
			
			//c ���� �� ��
			key.single("absolju", KeyEvent.VK_C, true);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			key.single("absolju", KeyEvent.VK_C, false);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			
			//a ���� �� ��
			key.single("absolju", KeyEvent.VK_A, true);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			key.single("absolju", KeyEvent.VK_A, false);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			
			//l ���� �� ��
			key.single("absolju", KeyEvent.VK_L, true);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			key.single("absolju", KeyEvent.VK_L, false);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			
			//c ���� �� ��
			key.single("absolju", KeyEvent.VK_C, true);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			key.single("absolju", KeyEvent.VK_C, false);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }

			//���
			try { Thread.sleep(1000); } catch (InterruptedException e) { }
			
			//���� Ű ���� �� ��
			key.single("absolju", KeyEvent.VK_ENTER, true);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			key.single("absolju", KeyEvent.VK_ENTER, false);
			run.run();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return;
	}
}
