import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.dataset.FileDataSet;
import java.io.*;

public class plotprueba {
    public static void main(String[] args) {
        JavaPlot jp = new JavaPlot();

        jp.addPlot("sin(x)");
        jp.plot();
    }
}
