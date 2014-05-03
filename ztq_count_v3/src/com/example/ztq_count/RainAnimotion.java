package com.example.ztq_count;

/*
 * 用于实现柱状图的动态效果
 * */
import java.math.BigDecimal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RainAnimotion extends SurfaceView implements
		SurfaceHolder.Callback {
	private int currentX;
	private int score;

	private int oldX;

	private SurfaceHolder sfh;

	private boolean isRunning = true;

	private static float[] rainNum = { 0, 5, 1, 0.0f, 1, 3, 8, 10, 11, 12, 15,
			14, 18, 12, 15, 17, 13, 15, 12, 14, 11, 12, 14, 17 };// 24个温度值
	// private static float[] temp = { 29, 26, 25, 20, 20, 20, 26, 32, 36, 22,
	// 25,
	// 24, 28, 22, 20, 27, 23, 28, 29, 32, 21, 22, 24, 37 };
	private String[] houres = { "00", "01", "02", "03", "04", "05", "06", "07",
			"08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18",
			"19", "20", "21", "22", "23" };// 一天的时间24H

	private int tick = 10; // 时间间隔(ms)
	private int bottom = 150; // 坐标系地段距离框架顶端的距离
	private int top = 10; // 坐标系顶端距离框架顶端框的距离
	private int lift = 38; // 坐标系左边距离框架左边框的距离
	static int right; // 坐标系右边距离框架左边的距离(!)
	static int gapX; // 两根竖线间的间隙(!)
	private int gapY = 20; // 两根横线间的间隙

	public RainAnimotion(Context context) {
		super(context);
	}

	// 在这里初始化才是最初始化的。
	public RainAnimotion(Context context, AttributeSet atr) {
		super(context, atr);

		setZOrderOnTop(true);// 设置置顶（不然实现不了透明）
		sfh = this.getHolder();
		sfh.addCallback(this);
		sfh.setFormat(PixelFormat.TRANSLUCENT);// 设置背景透明
	}

	/**
	 * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i("系统消息", "surfaceCreated");

		// 加入下面这三句是当抽屉隐藏后，打开时防止已经绘过图的区域闪烁，所以干脆就从新开始绘制。
		isRunning = true;
		currentX = 0;
		clearCanvas();

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				gridDraw();
				drawChartLine();
			}
		});

		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		Log.i("系统信息", "surfaceChanged");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		Log.i("系统信息", "surfaceDestroyed");

		// 加入这个变量是为了控制抽屉隐藏时不会出现异常。
		isRunning = false;
	}

	protected void gridDraw() {
		float max = rainNum[0];
		float temMax = 0;
		float min = rainNum[0];
		float temMin = 0;
		float space = 0f;// 平均值
		for (int i = 1; i < rainNum.length; i++) {
			if (max < rainNum[i]) {
				max = rainNum[i];
			}
			if (min > rainNum[i]) {
				min = rainNum[i];
			}
			temMax = max;
			temMin = min;
		}
		space = (temMax - temMin) / 7;

		Canvas canvas = sfh.lockCanvas();

		Paint mbackLinePaint = new Paint();// 用来画坐标系了
		mbackLinePaint.setColor(Color.WHITE);
		mbackLinePaint.setAntiAlias(true);
		mbackLinePaint.setStrokeWidth(1);
		mbackLinePaint.setStyle(Style.FILL);

		Paint mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(12F);// 设置温度值的字体大小
		// 绘制坐标系
		for (int i = 0; i < 8; i++) {
			canvas.drawLine(lift, top + gapY * i, lift + gapX * 24, top + gapY
					* i, mbackLinePaint);
			mTextPaint.setTextAlign(Align.RIGHT);
			if (temMax < 0.8) {
				float result = 0.1f * i;
				canvas.drawText("" + result, lift - 2, bottom + 3 - 20 * i,
						mTextPaint);
			} else {
				float result = temMin + space * i;// 精确的各个节点的值
				BigDecimal b = new BigDecimal(result);// 新建一个BigDecimal
				float displayVar = b.setScale(1, BigDecimal.ROUND_HALF_UP)
						.floatValue();// 进行小数点一位保留处理现实在坐标系上的数值
				canvas.drawText("" + displayVar, lift - 2, bottom + 3 - 20 * i,
						mTextPaint);
			}
		}
		for (int i = 0; i < 25; i++) {
			canvas.drawLine(lift + gapX * i, top, lift + gapX * i, bottom,
					mbackLinePaint);

		}
		for (int i = 0; i < rainNum.length; i++) {
			mTextPaint.setTextAlign(Align.CENTER);
			canvas.drawText(houres[i], lift + gapX * i + gapX / 2, bottom + 14,
					mTextPaint);
		}
		sfh.unlockCanvasAndPost(canvas);
	}

	protected void GridDraw(Canvas canvas) {
		if (canvas == null) {
			return;
		}
		float max = rainNum[0];
		float temMax = 0;
		float min = rainNum[0];
		float temMin = 0;
		float space = 0f;// 平均值
		for (int i = 1; i < rainNum.length; i++) {
			if (max < rainNum[i]) {
				max = rainNum[i];
			}
			if (min > rainNum[i]) {
				min = rainNum[i];
			}
			temMax = max;
			temMin = min;
		}
		space = (temMax - temMin) / 7;// 7段有效显示范围
		// textY=Math.round(temMin + space * i);

		Paint mbackLinePaint = new Paint();// 用来画坐标系了
		mbackLinePaint.setColor(Color.WHITE);
		mbackLinePaint.setAntiAlias(true);
		mbackLinePaint.setStrokeWidth(1);
		mbackLinePaint.setStyle(Style.FILL);

		Paint mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		// mTextPaint.setTextAlign(Align.RIGHT);
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(12F);// 设置温度值的字体大小
		// 绘制坐标系
		for (int i = 0; i < 8; i++) {
			canvas.drawLine(lift, top + gapY * i, lift + gapX * 24, top + gapY
					* i, mbackLinePaint);
			mTextPaint.setTextAlign(Align.RIGHT);
			if (temMax < 0.8) {
				float result = 0.1f * i;
				canvas.drawText("" + result, lift - 2, bottom + 3 - 20 * i,
						mTextPaint);
			} else {
				float result = temMin + space * i;// 精确的各个节点的值
				BigDecimal b = new BigDecimal(result);// 新建一个BigDecimal
				float displayVar = b.setScale(1, BigDecimal.ROUND_HALF_UP)
						.floatValue();// 进行小数点一位保留处理现实在坐标系上的数值
				canvas.drawText("" + displayVar, lift - 2, bottom + 3 - 20 * i,
						mTextPaint);
			}
		}
		for (int i = 0; i < 25; i++) {
			canvas.drawLine(lift + gapX * i, top, lift + gapX * i, bottom,
					mbackLinePaint);

		}
		for (int i = 0; i < rainNum.length; i++) {
			mTextPaint.setTextAlign(Align.CENTER);
			canvas.drawText(houres[i], lift + gapX * i + gapX / 2, bottom + 14,
					mTextPaint);
		}
	}

	private void drawChartLine() {
		while (isRunning) {
			try {
				// score = bottom - currentX;
				// drawChart(score);// 绘制
				drawChart(currentX);// 绘制
				currentX++;// 往前进

				if (currentX == 150) {
					// 如果到了终点，则清屏重来
					clearCanvas();
					currentX = 0;
				}

				try {
					Thread.sleep(tick);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {

			}
		}
	}

	void drawChart(int length) {
		if (length == 0)
			oldX = 150;
		// Canvas canvas = sfh.lockCanvas(new Rect(oldX, 0, oldX + length,
		// 180));// 范围选取正确(从左到右动画)
		Canvas canvas = sfh.lockCanvas(new Rect(lift, oldX - length, lift
				+ gapX * 24, oldX));// 范围选取正确
		Log.i("系统消息", "oldX = " + oldX + "  length = " + length);
		Paint mPointPaint = new Paint();
		mPointPaint.setAntiAlias(true);
		mPointPaint.setColor(Color.YELLOW);

		Paint mLinePaint = new Paint();// 用来画折线
		mLinePaint.setColor(Color.YELLOW);
		mLinePaint.setAntiAlias(true);
		mLinePaint.setStrokeWidth(2);
		mLinePaint.setStyle(Style.FILL);

		float max = rainNum[0];
		float temMax = 0;
		float min = rainNum[0];
		float temMin = 0;
		float spacePX = 0f;// 平均像素值
		for (int i = 1; i < rainNum.length; i++) {
			if (max < rainNum[i]) {
				max = rainNum[i];
			}
			if (min > rainNum[i]) {
				min = rainNum[i];
			}
			temMax = max;
			temMin = min;
		}
		spacePX = 140 / (temMax - temMin);// 平均每个温度值说占用的像素值

		float cx = 0f;
		float cy = 0f;
		float dx = 0f;
		float dy = 0f;
		for (int j = 0; j < rainNum.length; j++) {
			cx = lift + gapX * j;
			cy = bottom - (rainNum[j] - temMin) * spacePX;
			dx = lift + gapX * (j + 1);
			dy = bottom - (rainNum[j] - temMin) * gapY * 10;
			if (temMax < 0.8) {
				if (rainNum[j] == 0) {
					canvas.drawRect(new RectF(cx, bottom - 2, dx - 2, bottom),
							mLinePaint);// 当雨量值是0时，绘制2px的矩形，表示这里有值
				} else {
					canvas.drawRect(new RectF(cx, dy, dx - 2, bottom),
							mLinePaint);
				}

			} else {
				if (rainNum[j] == 0) {
					canvas.drawRect(new RectF(cx, bottom - 2, dx - 2, bottom),
							mLinePaint);// 当雨量值是0时，绘制2px的矩形，表示这里有值
				} else {
					canvas.drawRect(new RectF(cx, cy, dx - 2, bottom),
							mLinePaint);
				}
			}

		}

		sfh.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
	}

	/**
	 * 把画布擦干净，准备绘图使用。
	 */
	private void clearCanvas() {
		Canvas canvas = sfh.lockCanvas();

		canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);// 清除画布

		GridDraw(canvas);

		sfh.unlockCanvasAndPost(canvas);
	}
}
