//참고: https://docs.oracle.com/javase/tutorial/uiswing/misc/systemtray.html

package kr.pe.absolju.KeyPresser;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AppUI {
	
	final static Image iconGIF = Toolkit.getDefaultToolkit().getImage("img/icon2.gif");
	
	public static void create() {
        if (!SystemTray.isSupported()) {
            System.out.println("Error: SystemTray가 지원되지 않음, TrayUI.TrayUI");
            return;
        }
        
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(iconGIF, "JavaKeyPresser");
        trayIcon.setImageAutoSize(true);
        final SystemTray tray = SystemTray.getSystemTray();
       
        MenuItem about = new MenuItem("About..");
        MenuItem setting = new MenuItem("Setting..");
        MenuItem start = new MenuItem("Start");
        MenuItem stop = new MenuItem("Stop");
        MenuItem exit = new MenuItem("Exit");
        
        popup.add(about);
        popup.add(setting);
        popup.addSeparator();
        popup.add(start);
        popup.add(stop);
        popup.addSeparator();
        popup.add(exit);
        
        stop.setEnabled(false);
       
        trayIcon.setPopupMenu(popup);
       
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("Error: Tray 아이콘 생성 중, TrayUI.TrayUI");
        }
        
        about.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AboutDialog();
        	}
        });
        setting.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		SettingFrame();
        	}
        });
        
        start.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		start.setEnabled(false);
        		stop.setEnabled(true);
        		runKeyValue.run(true);
        	}
        });
        stop.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		start.setEnabled(true);
        		stop.setEnabled(false);
        		runKeyValue.run(false);
        	}
        });
        
        exit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		runKeyValue.run(false);
        		tray.remove(trayIcon);
        		System.exit(0);
        	}
        });
	}
	
	public static void AboutDialog() {
		JDialog aboutDialog = new JDialog();
		aboutDialog.setTitle("About..");
		aboutDialog.setSize(380, 150);
		aboutDialog.setIconImage(iconGIF);
		aboutDialog.setResizable(false);
		aboutDialog.setLocationRelativeTo(null);
		
		Label M1 = new Label("Protocol Buffer");
		Label M2 = new Label("Java KeyPresser&Sender");
		M1.setBounds(10,10,1000,20);
		M2.setBounds(10,30,1000,20);
		aboutDialog.add(M1);
		aboutDialog.add(M2);
		
		aboutDialog.setVisible(true);
	}
	
	public static void SettingFrame() {
		JFrame settingFrame = new JFrame();
		settingFrame.setTitle("Settings");
		settingFrame.setSize(260, 100);
		settingFrame.setIconImage(iconGIF);
		settingFrame.setResizable(false);
		settingFrame.setLocationRelativeTo(null);
		
		settingFrame.setLayout(new BorderLayout());
		
		//설정 목록이 들어가 있는 Panel 생성
		JPanel optionList = new JPanel();
		JLabel portLabel = new JLabel("Port Number:");
		JTextField portField = new JTextField(10);
		optionList.add(portLabel);
		optionList.add(portField);
		
		//확인 버튼
		JButton ok = new JButton("OK");
		
		//설정 목록과 확인 버튼 삽입
		settingFrame.add(optionList, BorderLayout.CENTER);
		settingFrame.add(ok, BorderLayout.PAGE_END);
		
		settingFrame.setVisible(true);
	}
	
}
