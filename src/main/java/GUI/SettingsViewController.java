package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import layers.DomainCtrl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsViewController implements Initializable{

    public Slider volumeSlider;

    private DomainCtrl domainCtrl;


    public void setDomainCtrl(DomainCtrl domainCtrl) {
        this.domainCtrl = domainCtrl;
        volumeSlider.setValue(domainCtrl.getVolumeBackgrounMusic());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        volumeSlider.valueProperty().addListener((obs, wasChanging, isChanging) -> {
            domainCtrl.setVolumeBackgroundMusic(volumeSlider.getValue());
        });
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
