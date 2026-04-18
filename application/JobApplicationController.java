package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class JobApplicationController implements Initializable {

    @FXML private TextField fullNameField;
    @FXML private DatePicker datePicker;
    @FXML private TextArea commentsArea;
    @FXML private Slider fontSlider;
    @FXML private ImageView empImageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        commentsArea.setStyle("-fx-font-size: 14px;");
    }

    @FXML
    private void handleUploadPhoto() {
        try {
          
            Image image = new Image(getClass().getResourceAsStream("/application/image/user.png"));
            empImageView.setImage(image);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Image Not Found", "Check the image path in your project.");
        }
    }

 
    @FXML
    private void handleExit() {
        Stage stage = (Stage) fullNameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSlider() {
        double size = fontSlider.getValue();
        commentsArea.setStyle("-fx-font-size: " + size + "px;");
    }

    @FXML
    private void handleSave() {
        if (fullNameField.getText().isEmpty() || datePicker.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Missing Data", "Please enter name and date.");
        } else {
            saveToFile();
            showAlert(Alert.AlertType.INFORMATION, "Success", null, "Review Saved Successfully for: " + fullNameField.getText());
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("employees_reviews.txt", true))) {
            writer.println("Employee: " + fullNameField.getText());
            writer.println("Date: " + datePicker.getValue());
            writer.println("Comments: " + commentsArea.getText());
            writer.println("---------------------------");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "File Error", null, "Could not save to file.");
        }
    }

    @FXML
    private void handleClear() {
        fullNameField.clear();
        commentsArea.clear();
        datePicker.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}