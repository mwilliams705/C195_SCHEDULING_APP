package main.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.Util.DBConnector;
import main.Util.DBQuery;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportThreeController implements Initializable {
    public VBox reportThreeVBOX;

    /**
     * Generates a pie chart from from database query and shows it in the VBOX.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PieChart p = new PieChart();
        p.setTitle("Total Appointments By Type");

        String getStatement = "select Type, count(*) as Count from appointments group by Type;";

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()){
                PieChart.Data  data = new PieChart.Data(rs.getString("Type"),rs.getInt("Count"));
                p.getData().add(data);
            }

        }catch (SQLException s){
            s.printStackTrace();
        }


        reportThreeVBOX.setAlignment(Pos.CENTER);
        reportThreeVBOX.getChildren().add(p);


        for (final PieChart.Data data : p.getData()) {

                Tooltip tooltip = new Tooltip();
                tooltip.setShowDelay(Duration.millis(350.0));
                tooltip.setText(data.getName() +" - "+
                        data.getPieValue());
                Tooltip.install(data.getNode(), tooltip);
        }


    }
}
