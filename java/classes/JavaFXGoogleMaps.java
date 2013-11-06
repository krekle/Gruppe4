package GUI;

import java.net.URL;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class JavaFXGoogleMaps extends Application {

	private Scene scene;
	MyBrowser myBrowser;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("SHEEPTRACKER3000");
		primaryStage.setWidth(400);
		primaryStage.setHeight(300);
		myBrowser = new MyBrowser();
		scene = new Scene(myBrowser, 400, 300);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

	static class MyBrowser extends Region {
		HBox toolbar;

		static WebView webView = new WebView();
		static WebEngine webEngine = webView.getEngine();

		public MyBrowser() {

			final URL urlGoogleMaps = getClass().getResource(
					"GoogleMapsV3.html");
			webEngine.load(urlGoogleMaps.toExternalForm());
			webEngine.setJavaScriptEnabled(true);
			getChildren().add(webView);

			webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {

				@Override
				public void handle(WebEvent<String> arg0) {

					System.out.println("We are here");
					webEngine
							.executeScript("document.getElementById('test').innerHTML = 'the new text';");
					webEngine.executeScript("document.setNewMarker()");
					webEngine
							.executeScript("document.setNewMarkerWithParameters(navn-63.44-10.39)");
					// webEngine.load(urlGoogleMaps.toExternalForm());
					// getChildren().add(webView);

				}

			});

		}
	}
}