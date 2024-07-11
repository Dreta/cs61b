package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.List;


public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap map;

    public HistoryHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        System.out.println("Got query that looks like:");
        System.out.println("Words: " + q.words());
        System.out.println("Start Year: " + q.startYear());
        System.out.println("End Year: " + q.endYear());

        List<TimeSeries> results = q.words().stream().map(word -> map.weightHistory(word, q.startYear(), q.endYear())).toList();

        XYChart chart = Plotter.generateTimeSeriesChart(q.words(), results);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
