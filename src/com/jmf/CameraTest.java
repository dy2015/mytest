package com.jmf;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;

import javax.media.CannotRealizeException;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.JFrame;

public class CameraTest extends JFrame {
	private static final long serialVersionUID = -3312135611300259285L;

	public CameraTest() {
		this.setTitle("MyCapture");
		this.setBounds(500, 100, 800, 500);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void start() {
		String str1 = "vfw:logitech usb video camera:0";
		String str2 = "vfw:Microsoft WDM Image Capture (Win32):0";
		CaptureDeviceInfo di = null;
		MediaLocator ml = null;
		Player player = null;

		di = CaptureDeviceManager.getDevice(str2);
		ml = di.getLocator();
		System.out.println(di);
		System.out.println(ml);
		try {
			player = Manager.createRealizedPlayer(ml);
		} catch (NoPlayerException e) {
			e.printStackTrace();
		} catch (CannotRealizeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (player != null)
			player.start();
		Component comp = null;
		if ((comp = player.getVisualComponent()) != null)
			add(comp, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		CameraTest mc = new CameraTest();
		mc.start();
		System.out.println("sss");
	}
}