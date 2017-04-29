package kr.pe.absolju.KeyPresser;

import java.io.IOException;

import com.sun.glass.events.KeyEvent;

public class Run {

	public static void main(String[] args) {
		sendKeyValue key = new sendKeyValue();
		runKeyValue run = new runKeyValue();
		
		/*
		 * 실제로는 보내는 쪽은 누르거나 땔 때마다 스레드 생성해서 UTP로 패킷 날림.
		 * 받는 쪽은 올 때마다 스레드 생성해서 해석.
		 */
		
		try {
			//윈도우 키 누른 후 땜
			key.single("absolju", KeyEvent.VK_WINDOWS, true);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			key.single("absolju", KeyEvent.VK_WINDOWS, false);
			run.run();
			
			//대기
			try { Thread.sleep(1000); } catch (InterruptedException e) { }
			
			//c 누른 후 땜
			key.single("absolju", KeyEvent.VK_C, true);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			key.single("absolju", KeyEvent.VK_C, false);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			
			//a 누른 후 땜
			key.single("absolju", KeyEvent.VK_A, true);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			key.single("absolju", KeyEvent.VK_A, false);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			
			//l 누른 후 땜
			key.single("absolju", KeyEvent.VK_L, true);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			key.single("absolju", KeyEvent.VK_L, false);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			
			//c 누른 후 땜
			key.single("absolju", KeyEvent.VK_C, true);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }
			key.single("absolju", KeyEvent.VK_C, false);
			run.run();
			try { Thread.sleep(100); } catch (InterruptedException e) { }

			//대기
			try { Thread.sleep(1000); } catch (InterruptedException e) { }
			
			//엔터 키 누른 후 땜
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
