﻿import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class Sclient{
	private PrintWriter pw;
	private JFrame frame;
	private JPanel pane_buttom;
	private JSplitPane pane_center;

	//显示内容的文本框，输入内容的文本框,发送内容按钮
	private JScrollPane pane_showWindow;
	private JScrollPane pane_inputWindow;
	private JTextArea area_showWindow;
	private JTextArea area_inputWindow;
	private JButton btn_send;
	private Dimension dimension;//用于设置area_showWindow可拖拉的大小

	//初始化
	public Sclient() {
		frame = new JFrame();
		pane_buttom = new JPanel();
		pane_showWindow = new JScrollPane();
		pane_inputWindow = new JScrollPane();
		area_showWindow = new JTextArea();
		area_inputWindow = new JTextArea();
		pane_center = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, pane_showWindow, pane_inputWindow);
		btn_send = new JButton("发送");

		dimension = new Dimension(50, 300);
	}

	//调用方法显示窗口
	public void showFrame(){
		initFrame();
		initChatTextArea();
		initButton();
		btn_send();
		socket();
	}

	//主窗体
	public void initFrame(){
		frame.setTitle("客户端逗逼B");
		int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		frame.setBounds(width / 2, height / 2, 400, 450);
		frame.setVisible(true);
	}

	//内容显示文本框和输入内容文本框
	private void initChatTextArea(){
		//取得视图焦点
		pane_showWindow.getViewport().add(area_showWindow);
		pane_inputWindow.getViewport().add(area_inputWindow);
		//将显示文本域设置为不可编辑
		area_showWindow.setEditable(false);
		//设置显示文本域可拖拉的大小 
		pane_showWindow.setMinimumSize(dimension);
		frame.add(pane_center, BorderLayout.CENTER);
	}

	//发送文件，发送内容按钮
	public void initButton(){
		pane_buttom.add(btn_send);
		frame.add(pane_buttom, BorderLayout.SOUTH);
	}

	private void btn_send(){
		btn_send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String info = area_inputWindow.getText();
				area_showWindow.append("客户端逗逼B： "+info+"\r\n");
				pw.println(info);
				area_inputWindow.setText("");
			}
		});
	}
	
	private void socket(){
		try {
			Socket s = new Socket("127.0.0.1",9988);
			InputStreamReader isr=new InputStreamReader(s.getInputStream());
			BufferedReader br=new BufferedReader(isr);
			pw=new PrintWriter(s.getOutputStream(),true);
			while(true){
				//不停地读取从服务器端发来的信息
				String info=br.readLine();
				area_showWindow.append("服务端逗逼A： "+info+"\r\n");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Sclient chat = new Sclient();
		chat.showFrame();
	}
}
