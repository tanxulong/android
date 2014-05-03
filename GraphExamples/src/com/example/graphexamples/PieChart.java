package com.example.graphexamples;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class PieChart {
	public Intent getIntent(Context context) {
		int[] values = { 23, 45, 66, 23, 44, 2, 34 };
		CategorySeries series = new CategorySeries("Pie Chart");
		for (int i = 0, len = values.length; i < len; i++) {
			series.add("Section " + (i + 1), values[i]);
		}

		int[] colors = new int[] { Color.BLUE, Color.DKGRAY, Color.CYAN,
				Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.YELLOW };
		DefaultRenderer render = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			render.addSeriesRenderer(r);
		}
		render.setChartTitle("Pie Chart");
		render.setChartTitleTextSize(7);
		render.setZoomButtonsVisible(true);
		Intent intent = ChartFactory.getPieChartIntent(context, series, render, "Pie");
		return intent;
	}
}
