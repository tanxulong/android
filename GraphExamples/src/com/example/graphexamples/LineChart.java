package com.example.graphexamples;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class LineChart {
	public Intent getIntent(Context context) {
		int[] x1 = { 1, 2, 3, 4, 5, 6, 7 };
		int[] y1 = { 5, 10, 15, 20, 25, 30, 35 };
		TimeSeries series1 = new TimeSeries("Line1");
		for(int i = 0, len = x1.length; i < len; i++){
			series1.add(x1[i], y1[i]);
		}
		
		int[] x2 = { 1, 2, 3, 4, 5, 6, 7 };
		int[] y2 = { 22, 12, 4, 51, 22, 43, 35 };
		TimeSeries series2 = new TimeSeries("Line2");
		for(int i = 0, len = x2.length; i < len; i++){
			series2.add(x2[i], y2[i]);
		}
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer render1 = new XYSeriesRenderer();
		XYSeriesRenderer render2 = new XYSeriesRenderer();
		mRenderer.addSeriesRenderer(render1);
		mRenderer.addSeriesRenderer(render2);
		
		// Customization line 1
		render1.setColor(Color.RED);
		render1.setPointStyle(PointStyle.SQUARE);
		render1.setFillPoints(true);
		
		// Customization line 2
		render2.setColor(Color.BLUE);
		render2.setPointStyle(PointStyle.DIAMOND);
		render2.setFillPoints(true);
		
		Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, "Line Graph");
		return intent;
	}
}
