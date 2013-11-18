package sheepfarmer.net.gui;

import java.net.URL;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sheepfarmer.net.app.Sheep;
import sheepfarmer.net.app.Singleton;
import sheepfarmer.net.client.SheepResponse;
import sheepfarmer.net.client.sheepdb;

public class MapView extends Application {
	private static ArrayList<Sheep> sheeps = new ArrayList<Sheep>();

	public MapView() {
		upDateSheep();
	}

	public static void upDateSheep(){
		SheepResponse sr = null;
		try {
			sr = sheepdb.getSheep(Singleton.getInstance().getToken());
		} catch (Exception e) {
			System.out.println("ERR-constructing Map");
		}
		sheeps = sr.getSheepList();
	}
	
	
	public MapView(ArrayList<Sheep> sheepsForMap) {
		sheeps = sheepsForMap;

	}

	MyBrowser myBrowser;

	static ObservableList<String> names = FXCollections.observableArrayList(
			"ID: Unknown ", "Age: Unknown ", "Heartrate: unknown ",
			"Weight: unknown", "Respiration: unknown", "OwnerID: unknwon ",
			"Latitude: unknwon", "Longitude: unknown", "Name: unknown",
			"Gender: unknown", "Temperature: unknown", "Lifesatus: unknown",
			"Mood: unknown", "Color: unknown");
	ListView<String> myList = new ListView<String>(names);

	public SplitPane createMap() {

		myBrowser = new MyBrowser();

		SplitPane split = new SplitPane();
		split.setDividerPositions(0.215);

		VBox right = new VBox();
		final Label idLabel = new Label("Sheep");
		idLabel.setFont(new Font("Arial", 30));
		right.prefHeight(Screen.getPrimary().getVisualBounds().getHeight());
		right.minWidth((Screen.getPrimary().getVisualBounds().getWidth() * 0.80));
		right.getChildren().addAll(myBrowser);
		VBox left = new VBox();
		myList.setStyle("-fx-box-border: transparent;");
		left.prefHeight(Screen.getPrimary().getVisualBounds().getHeight() - 20);
		left.minWidth((Screen.getPrimary().getVisualBounds().getWidth() * 0.20));
		left.getChildren().addAll(idLabel, myList);

		split.getItems().addAll(left, right);
		return split;
	}

	static class MyBrowser extends Region {

		static WebView webView = new WebView();
		static WebEngine webEngine = webView.getEngine();

		public MyBrowser() {

			final URL urlGoogleMaps = getClass().getResource(
					"GoogleMapsV3.html");
			webEngine.load(urlGoogleMaps.toExternalForm());
			webEngine.setJavaScriptEnabled(true);
			getChildren().add(webView);

			webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {

				public void handle(WebEvent<String> arg0) {
					if (arg0.getData().equals("loaded")) {
						for (int i = 0; i < sheeps.size(); i++) {
							webEngine.executeScript(sheeps.get(i)
									.getMarkerString());

						}

					} else {
						for (int i = 0; i < sheeps.size(); i++) {
							if (Integer.parseInt(sheeps.get(i).getId()) == Integer
									.parseInt(arg0.getData())) {
								names.set(0, "Id: " + sheeps.get(i).getId());
								names.set(1, "Age: " + sheeps.get(i).getAge());
								names.set(2, "Heart-Rate: "
										+ sheeps.get(i).getHr());
								names.set(3, "Weight: "
										+ sheeps.get(i).getWeight());
								names.set(4, "Respiration: "
										+ sheeps.get(i).getRespiration());
								names.set(5, "OwnerId: "
										+ sheeps.get(i).getOwnerid());
								names.set(
										6,
										"Latitude: "
												+ String.valueOf(sheeps.get(i)
														.getLatitude()));
								names.set(
										7,
										"Longitude: "
												+ String.valueOf(sheeps.get(i)
														.getLongitude()));
								names.set(8, "Name: " + sheeps.get(i).getName());
								names.set(
										9,
										"Gender: "
												+ String.valueOf(sheeps.get(i)
														.getGen()));

								names.set(10, "Temperature "
										+ sheeps.get(i).getTemp());
								names.set(11, "Status: "
										+ parseString(sheeps.get(i).isDead()));
								names.set(
										12,
										"Mood: "
												+ String.valueOf(sheeps.get(i)
														.getMood()));
								names.set(
										13,
										"Color: "
												+ String.valueOf(sheeps.get(i)
														.getCol()));

							}

						}
					}

				}

				private String parseString(boolean dead) {
					if (dead == (true)) {
						return "Dead";
					}
					return "Alive";
				}

			});

		}
	}

	@Override
	public void start(Stage arg0) throws Exception {
	}
}
