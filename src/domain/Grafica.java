/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.Table;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author Developer GAGS
 */
public class Grafica {

    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @param tabla
     * @return The dataset.
     */
    private Double maxPeso;
    private Double minPeso;
    private Image i;

    public void cargarImagen() {
        i = null;
        i = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/logo tru-test.png"));
    }

    public XYDataset createDatasetPesos(Table tabla) {

        TimeSeries s1 = new TimeSeries("", Second.class);
        Integer Dia, Mes, Año, Hora, Minuto, Segundo;
        java.util.Calendar fecha = java.util.Calendar.getInstance();
        Double peso = null;
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        maxPeso = new Double(0.0);
        minPeso = new Double(-100.0);
        for (int i = 0; i < tabla.getRowCount(); i++) {

            try {

                fecha.setTime(formatoDelTexto.parse(tabla.getValueAt(i, 0).toString()));
            } catch (ParseException ex) {

                Logger.getLogger(Grafica.class.getName()).log(Level.SEVERE, null, ex);
            }

            peso = Double.parseDouble(tabla.getValueAt(i, 1).toString());

            if (peso > maxPeso) {

                maxPeso = peso;
            }

            if (minPeso.equals(-100.0)) {

                minPeso = peso;
            }

            if (peso < minPeso) {

                minPeso = peso;
            }
            // System.out.println(fecha);
            Mes = fecha.get(java.util.Calendar.MONTH);
            Año = fecha.get(java.util.Calendar.YEAR) - 1900;
            Dia = fecha.get(java.util.Calendar.DAY_OF_MONTH);
            Hora = fecha.get(java.util.Calendar.HOUR_OF_DAY);
            Minuto = fecha.get(java.util.Calendar.MINUTE);
            Segundo = fecha.get(java.util.Calendar.SECOND);

           
            
            s1.addOrUpdate(new Second(new Date(Año, Mes, Dia, Hora, Minuto, Segundo)), peso);
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);
        return dataset;
    }

    public JFreeChart createChart(XYDataset dataset) {
        /*
         ///*
         NumberAxis numberaxis = new NumberAxis("");
         numberaxis.setAutoRangeIncludesZero(false);

         DateAxis dateaxis = new DateAxis("");

         NumberAxis numberaxis1 = new NumberAxis("");
         numberaxis1.setAutoRangeIncludesZero(false);

         XYSplineRenderer xysplinerenderer = new XYSplineRenderer();

         XYPlot xyplot = new XYPlot(dataset, dateaxis, numberaxis1, xysplinerenderer);
         // xyplot.setBackgroundPaint(new Color(238, 242, 250));//
         // xyplot.setDomainGridlinePaint(new Color(255, 255, 255));
         // xyplot.setRangeGridlinePaint(new Color(238, 242, 250));
         xyplot.getRenderer().setSeriesPaint(0, Color.RED);
         xyplot.setAxisOffset(new RectangleInsets(4D, 4D, 4D, 4D));
         XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
         renderer.setSeriesShapesVisible(0, true);//FIXME Dots

         //  xyplot.getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
         JFreeChart jfreechart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, xyplot, false);
         jfreechart.setBackgroundPaint(Color.white);

         DateAxis axis = (DateAxis) xyplot.getDomainAxis();
         axis.setDateFormatOverride(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa"));

         xyplot.getRangeAxis().setUpperBound(maxPeso * 1.25);
         xyplot.getRangeAxis().setLowerBound(minPeso * 0.75);
         cargarImagen();
         // xyplot.setBackgroundImage(i);
         //jfreechart.setBackgroundImage(i);
         return jfreechart;
         */
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "", // title
                "Fecha/Hora", // x-axis label
                "Pesos(Kg.)", // y-axis label
                dataset, // data
                false, // create legend?
                true, // generate tooltips?
                false // generate URLs?
        );

        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);
        /*         plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
         plot.setDomainCrosshairVisible(false);
         plot.setRangeCrosshairVisible(false);
         */
        plot.setAxisOffset(new RectangleInsets(4D, 4D, 4D, 4D));

        XYItemRenderer r = plot.getRenderer();

        if (r instanceof XYLineAndShapeRenderer) {

            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
        }

        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        // range.setAutoRangeIncludesZero(true);
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss"));

        plot.getRangeAxis().setUpperBound(maxPeso * 1.25);
        plot.getRangeAxis().setLowerBound(minPeso * 0.75);
        
        return chart;

    }
}
