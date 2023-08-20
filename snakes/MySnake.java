package com.snakes;

import javax.swing.JFrame;

public class MySnake {

	public static void main(String[] args) {

		// 创建一个窗口
		JFrame frame = new JFrame();
		// 设置窗口大小
		frame.setBounds(600, 100, 700, 900);
		// 设置窗口收缩
		frame.setResizable(false);
		// 设置退出按钮
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 添加画布
		frame.add(new MyPanel());
		
		// 设置显示
		frame.setVisible(true);

	}

}
