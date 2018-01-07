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


public class RecordViewController {

    private DomainCtrl domainCtrl;

    public VBox mainVBox;
    public GridPane recordGridPane;

    public void setDomainCtrl(DomainCtrl domainCtrl) {
        this.domainCtrl = domainCtrl;
        buildRecordsGridPane();
    }

    private void buildRecordsGridPane() {

        RREntry recordList[] = domainCtrl.getRecordsRREntryes();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (int i = 0; i < 4; i++) {
            Label label = new Label(df.format(recordList[i].getDate()));
            label.setId(("dateLabel" + i+1));
            recordGridPane.add(label, 1, i+1);
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setValignment(label, VPos.CENTER);
            label = new Label(recordList[i].getUsername());
            label.setId(("usernameLabel" + i+1));
            recordGridPane.add(label, 2, i+1);
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setValignment(label, VPos.CENTER);
            label = new Label(String.valueOf(recordList[i].getScore()));
            label.setId(("usernameLabel" + i+1));
            recordGridPane.add(label, 3, i+1);
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setValignment(label, VPos.CENTER);
        }
    }

    public void exitButtonAction(ActionEvent actionEvent) {
        try {
            ViewController.mainMenuView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
