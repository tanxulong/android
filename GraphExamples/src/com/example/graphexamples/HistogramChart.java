package com.example.graphexamples;

import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class HistogramChart {
	public Intent getIntent(Context context) {
		// Bar 1
		int[] y1 = { 124, 22, 342, 22, 12, 113, 443 };
		CategorySeries series1 = new CategorySeries("Histogram 1");
		for (int i = 0, len = y1.length; i < len; i++) {
			series1.add("Bar " + (i + 1), y1[i]);
		}
		// Bar 2
		int[] y2 = { 23, 333, 14, 33, 254, 223, 44 };
		CategorySeries series2 = new CategorySeries("Histogram 2");
		for (int i = 0, len = y2.length; i < len; i++) {
			series1.add("Bar " + (i + 1), y2[i]);
		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series1.toXYSeries());
		dataset.addSeries(series2.toXYSeries());

		//
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setChartTitle("Graph Title");
		mRenderer.setXTitle("X title");
		mRenderer.setYTitle("Y title");
		mRenderer.setAxesColor(Color.GREEN);
		mRenderer.setLabelsColor(Color.RED);
		
		
		Intent intent = null;
		return intent;
	}
}
