/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.entite.Recette;
import com.mycompany.service.ServiceRecette;
import java.util.Hashtable;

/**
 *
 * @author SouhaiKr
 */
public class ChartForm {

    Form f;
        Resources theme;

    
    /**
     * Creates a renderer for the specified colors.
     */
    private DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(18);
        renderer.setLegendTextSize(18);
        renderer.setMargins(new int[]{20, 30});
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    /**
     * Builds a category series using the provided values.
     *
     * @param titles the series titles
     * @param values the values
     * @return the category series
     */
    protected CategorySeries buildCategoryDataset(String title, double[] values) {
        CategorySeries series = new CategorySeries(title);
        int k = 0;
        for (double value : values) {
            k++ ;
            if (k==1)
            {
            series.add("sucré", value);
            }
            else
            {
            series.add("salé", value);

            }
        }
       
        return series;
    }

    public ChartForm() {
         theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);

        // Generate the values
        ServiceRecette serviceRecette = new ServiceRecette();
        int i = 0;
        int j = 0;

        for (Recette r : serviceRecette.getList2()) {

            if (r.getCategorie().equals("sucré")) {
                i++;

            } else {
                j++;
            }

        }
        System.out.println(i);

        double[] values = new double[]{i, j};

        // Set up the renderer
        int[] colors = new int[]{ColorUtil.GREEN, ColorUtil.MAGENTA};
        DefaultRenderer renderer = buildCategoryRenderer(colors);
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextSize(20);
        renderer.setDisplayValues(true);
        renderer.setShowLabels(true);
        SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
        r.setGradientEnabled(true);
        r.setGradientStart(0, ColorUtil.GREEN);
        r.setGradientStop(0, ColorUtil.WHITE);
        r.setHighlighted(true);

        // Create the chart ... pass the values and renderer to the chart object.
        PieChart chart = new PieChart(buildCategoryDataset("Project budget", values), renderer);

        // Wrap the chart in a Component so we can add it to a form
        ChartComponent c = new ChartComponent(chart);

        // Create a form and show it.
        f = new Form("Statistiques");
        f.setLayout(new BorderLayout());
        f.addComponent(BorderLayout.CENTER, c);
        f.show();
        
        
        
        f.getToolbar().addCommandToLeftBar("", theme.getImage("back-command.png"),  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
            RecetteForm r = new RecetteForm();
            r.getF().show();
        }});

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
