package com.snakes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * panel 面板
 * @ClassName: MyPanel
 * @Description: 画布
 * @author: 墨汐
 * @date: 2023年8月20日 上午1:18:39
 */
public class MyPanel extends JPanel implements KeyListener, ActionListener{
	
	// 声明图片对象
	ImageIcon body = new ImageIcon("image/body.png");
	ImageIcon top = new ImageIcon("image/top.png");
	ImageIcon bottom = new ImageIcon("image/bottom.png");
	ImageIcon left = new ImageIcon("image/left.png");
	ImageIcon right = new ImageIcon("image/right.png");
	
	
	// 声明蛇头方向
	Direction direction = Direction.right;
	// 定义蛇的初始长度
	int len = 3;
	// 定义两个数组，存放蛇的X、Y的坐标,坐标连续起来就是一条蛇
	int[] snakeX = new int[1005];
	int[] snakeY = new int[1005];

	// 游戏状态标志
	boolean isStart = false;
	
	// 创建一个定时器对象
	Timer timer = new Timer(400, this); // 每400毫秒会刷新执行一次程序
	
	// 定义食物变量的X、Y坐标位置
	int foodX;
	int foodY;
	// 声明一个随机数，来随机产生食物位置
	Random random = new Random();
	// 声明食物图片对象
	ImageIcon food = new ImageIcon("image/food.png");
	
	public MyPanel() {
		// 初始化蛇的长度和位置信息，放在构造器中，创建画布对象的同时初始化，且只执行一次
		// 蛇的初始位置，并将蛇头存放在数组的第一个位置，其余放蛇的身体标记位置
		// 蛇头
		snakeX[0] = 100;
		snakeY[0] = 100;
		// 第一节身体位置
		snakeX[1] = 75;
		snakeY[1] = 100;
		// 第二节身体位置
		snakeX[2] = 50;
		snakeY[2] = 100;
		
		// 开启获取焦点，也就是获取键盘值
		this.setFocusable(true);
		// 添加监听事件
		this.addKeyListener(this);
		
		// 启动定时器
		timer.start();
		
		// 初始化，产生食物的随机位置
		foodX = 25 + 25 * random.nextInt(20);
		foodY = 25 + 25 * random.nextInt(20);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// 设置画布背景颜色
		this.setBackground(Color.blue);
		// 设置游戏区
		g.fillRect(0, 0, 700, 900);
		
		// 添加蛇头的图片
//		right.paintIcon(this, g, 100, 100);
		// 用枚举改造蛇头
		switch (direction) {
			case top :
				top.paintIcon(this, g, snakeX[0], snakeY[0]);
				break;
			case bottom:
				bottom.paintIcon(this, g, snakeX[0], snakeY[0]);
				break;
			case right:
				right.paintIcon(this, g, snakeX[0], snakeY[0]);
				break;
			case left:
				left.paintIcon(this, g, snakeX[0], snakeY[0]);
				break;
			default :
				System.out.println("输入错误……");	
				break;
		}
		
		
		// 添加蛇的两节身体
//		body.paintIcon(this, g, 75, 100);
//		body.paintIcon(this, g, 50, 100);
		// 改造蛇身，输出蛇身位置
		for (int i = 1; i < len; i++) {
			body.paintIcon(this, g, snakeX[i], snakeY[i]);
		}
		
		// 开始添加提示
//		g.setColor(Color.white);
//		g.setFont(new Font("宋体", Font.BOLD, 50));
//		g.drawString("请按空格键开始游戏", 100, 400);
		// 改造提示
		if (!isStart) {
			g.setColor(Color.white);
			g.setFont(new Font("宋体", Font.BOLD, 50));
			g.drawString("请按空格键开始游戏", 100, 400);
		}
		
		// 添加食物
		food.paintIcon(this, g, foodX, foodY);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == 32) { // 空格键位32
			isStart = !isStart; // 状态取反即可，不用来回判断
			// 重新画组件
			repaint();
		} else if (keyCode == KeyEvent.VK_UP) {// 实现蛇头转向的控制
			direction = Direction.top;
		} else if (keyCode == KeyEvent.VK_DOWN) {
			direction = Direction.bottom;
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			direction = Direction.right;
		} else if (keyCode == KeyEvent.VK_LEFT) {
			direction = Direction.left;
		} else {
			System.out.println("按键错误……");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 实现暂停
		if (isStart) {
			// 移动身体
			for (int i = len - 1; i > 0; i--) {
				/*
				 * 如果移动了，那么蛇会逐渐加载，蛇身每一节的坐标会随蛇头的变化而变化
				 * 当len增加后，蛇头随时间向前移动，会将前一节身体的坐标赋值给后一节身体，达到的效果就是增加了一节身体
				 */
				snakeX[i] = snakeX[i - 1];
				snakeY[i] = snakeY[i - 1];
			}
			
//			// 蛇头水平右移，则x坐标+25
//			snakeX[0] += 25;
//			// 蛇头超出画布边界范围,将蛇头置零，则蛇死亡，游戏重新开始
//			if (snakeX[0] > 700) {
//				snakeX[0] = 0;
//			}
			
			// 改造蛇头移动，蛇头的坐标会随时间一直变化
			switch (direction) {
				case top :
					snakeY[0] -= 25;
					if (snakeY[0] <= 0) {
						snakeY[0] = 900;
					}
					break;
				case bottom:
					snakeY[0] += 25;
					if (snakeY[0] >= 900) {
						snakeY[0] = 0;
					}
					break;
				case right:
					snakeX[0] += 25;
					if (snakeX[0] >= 700) {
						snakeX[0] = 0;
					}
					break;
				case left:
					snakeX[0] -= 25;
					if (snakeX[0] <= 0) {
						snakeX[0] = 700;
					}
					break;
				default :
					System.out.println("输入错误……");	
					break;
			}
			
			// 判断蛇头是否吃到了食物
			if (snakeX[0] == foodX && snakeY[0] == foodY) {
				len++;
				// 生产新的食物
				foodX = 25 + 25 * random.nextInt(20);
				foodY = 25 + 25 * random.nextInt(20);
			}
			
			// 重启游戏，即重启画布组件
			repaint();
			// 重启定时器
			timer.start();
		}
		
	}
	
}




















