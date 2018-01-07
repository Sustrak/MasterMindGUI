package GUI;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import layers.DomainCtrl;
import rr.RREntry;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class RecordViewController {

    private DomainCtrl domainCtrl;

    public VBox mainVBox;
    public GridPane recordGridPane;

    public void setDomainCtrl(DomainCtrl domainCtrl) {
        this.domainCtrl = domainCtrl;
        buildRecordsGridPane();
    }

    public void exitButtonAction(ActionEvent actionEvent) {
        try {
            ViewController.mainMenuView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void buildRecordsGridPane() {

        ArrayList<ArrayList<String>> recordList = domainCtrl.getRecordsTable();
        for (int i = 0; i < 4; i++) {
            Label label = new Label(recordList.get(i).get(0));
            label.setId(("nameLabel" + i));
            recordGridPane.add(label, 1, i+1);
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setValignment(label, VPos.CENTER);
            label = new Label(recordList.get(i).get(1));
            label.setId(("scoreLabel" + i));
            recordGridPane.add(label, 2, i+1);
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setValignment(label, VPos.CENTER);
            label = new Label(recordList.get(i).get(2));
            label.setId(("dateLabel" + i+1));
            recordGridPane.add(label, 3, i+1);
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setValignment(label, VPos.CENTER);
        }
    }

}
