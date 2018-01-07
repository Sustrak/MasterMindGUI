package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import layers.DomainCtrl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsViewController implements Initializable{

    private DomainCtrl domainCtrl;

    public Slider volumeSlider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        volumeSlider.valueProperty().addListener((obs, wasChanging, isChanging) -> {
            domainCtrl.setVolumeBackgroundMusic(volumeSlider.getValue());
        });
    }

    public void setDomainCtrl(DomainCtrl domainCtrl) {
        this.domainCtrl = domainCtrl;
        volumeSlider.setValue(domainCtrl.getVolumeBackgrounMusic());
    }



    public void exitButtonAction(ActionEvent actionEvent) {
        try {
            domainCtrl.saveMeta();
            ViewController.mainMenuView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
