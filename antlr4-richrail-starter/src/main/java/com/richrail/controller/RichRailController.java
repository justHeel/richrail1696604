package com.richrail.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import parser.RichRailCli;
import parser.RichRailLexer;
import parser.RichRailParser;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.richrail.domain.Train;
import com.richrail.domain.Wagon;
import com.richrail.log.FileLog;
import com.richrail.log.Logger;
import com.richrail.persistency.IPersist;
import com.richrail.persistency.JSON;
import com.richrail.persistency.Persist;
import com.richrail.persistency.XML;
import com.richrail.service.implementations.TrainServiceImpl;
import com.richrail.service.implementations.WagonServiceImpl;
import com.richrail.services.TrainService;
import com.richrail.services.WagonService;
import com.sun.jmx.snmp.Timestamp;

@SuppressWarnings("deprecation")
public class RichRailController implements Initializable, Observer{
	private Train selectedTrain = null;
	private Wagon selectedWagon = null;
	
	@FXML TextFlow clbox;
	@FXML TextFlow textbox;
	@FXML private ScrollPane sp;
	
	@FXML private Button btnCommand;
	@FXML private Button btnCreateTrain;
	@FXML private Button btnSelectTrain;
	@FXML private Button btnDeleteTrain;
	@FXML private Button bntCreateWagon;
	@FXML private Button btnSelectWagon;
	@FXML private Button btnDeleteWagon;
	@FXML private Button btnConnectWagon;
	@FXML private Button btnDisconnectWagon;
	@FXML private Button btnOpenScreen;
	@FXML private Button btnSave;
	
	@FXML private Text txSelectedTrainName = new Text();
	@FXML private Text txSelectedWagonName = new Text();
	
	@FXML private TextField tfTrainName = new TextField();
	@FXML private TextField tfWagonName = new TextField();
	@FXML private TextField tfCommand = new TextField();
	
	@FXML private ComboBox<Train> cbTrains = new ComboBox<Train>();
	@FXML private ComboBox<Wagon> cbWagons = new ComboBox<Wagon>();
	@FXML private ComboBox<String> cbWagonTypes = new ComboBox<String>();
	
	@FXML private Canvas canvasRichRail;
	
	public TrainService trainService = new TrainServiceImpl();
	public WagonService wagonService = new WagonServiceImpl();
	private ArrayList<IPersist> persistTypes;
	private Persist persist;
	private Logger logger;
	
	public RichRailController() {
		this.persistTypes = new ArrayList<IPersist>();
		this.persistTypes.add(new XML());
		this.persistTypes.add(new JSON());
		this.persist = new Persist(this.persistTypes);
		this.logger = new Logger(new FileLog());
	}
	
	
	/**
	 * Open screen button
	 * 
	 * @throws IOException
	 */
	@FXML
	public void openNewScreen() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/gui.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Another screen");
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	/*
	 * ANTLR command
	 */
	public void antlrCommandReader() {
		ANTLRInputStream parseInput = new ANTLRInputStream(this.tfCommand.getText());
		RichRailLexer lexer = new RichRailLexer(parseInput);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		RichRailParser parser = new RichRailParser(tokens, this.trainService, this.wagonService);
		ParserRuleContext commandContext = parser.command();
		ParseTreeWalker walker = new ParseTreeWalker();
        RichRailCli listener = new RichRailCli();
        walker.walk(listener, commandContext);
	}
	
	/*
	 * Log button
	*/
	@FXML
	public void saveToJSONAndXML() {
		this.persist.save(trainService.getAllTrains());
	}
	
	/*
	 * Train
	 */
	@FXML public void refreshTrainList() {
		refreshTrainComboBox();
	}
	
	@FXML public void tfCreateTrain(){
		createTrain(tfTrainName.getText());
	}
	
	@FXML public void selectTrain() {
		selectedTrain = cbTrains.getSelectionModel().getSelectedItem();
		if (selectedTrain != null) {
			txSelectedTrainName.setText(selectedTrain.getLocomotive().getName());
			refreshCanvasAferConnection();
		} else {
			System.out.print("No train selected! \n");
		}
	}
	
		@FXML public void deleteTrain() {
		if (selectedTrain != null) {
			trainService.deleteTrain(selectedTrain.getLocomotive().getName());
			refreshTrainComboBox();
			refreshWagonComboBox();
			txSelectedTrainName.setText("");
			
			Date date = Calendar.getInstance().getTime();  
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
            String strDate = dateFormat.format(date);    
			this.logger.addlog("Deleted train with name: " + selectedTrain.getLocomotive().getName() + " @ " + strDate);
			this.logger.log();
			
			selectedTrain = null;
			selectedWagon = null;
			setupCanvas();
		} else {
			System.out.print("No train selected yet!\n");
		}
	}
		
	public void createTrain(String trainName) {
		boolean canCreate = trainService.createTrain(trainName);
		if (canCreate) {
			Train train = trainService.getTrain(trainName);
			
			Date date = Calendar.getInstance().getTime();  
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
            String strDate = dateFormat.format(date);    
			this.logger.addlog("Created train with name: " + train.getLocomotive().getName() + " @ " + strDate);
			this.logger.log();
			
			selectedTrain = train;
			selectTrain();
			cbTrains.setValue(train);
			refreshTrainComboBox();
			tfTrainName.clear();
			tfCommand.clear();
		}
	}
	
	public void refreshTrainComboBox() {
		ObservableList<Train> trains = FXCollections.observableArrayList(trainService.getAllTrains());
		cbTrains.setItems(trains);
		update(new Observable(), new Object());
	}
	
	public void clDeleteTrain(String trainName) {
		Train object = trainService.getTrain(trainName);
		if (object != null) {
			selectedTrain = object;
			deleteTrain();
		} else {
			System.out.print("Train not found!\n");
		}
	}
	
	/*
	 * Wagon
	 */
	@FXML public void selectWagon() {
		selectedWagon = cbWagons.getSelectionModel().getSelectedItem();
		if (selectedWagon != null) {
			txSelectedWagonName.setText(selectedWagon.getName());
			System.out.print("Wagon "+selectedWagon.getName()+" selected! \n");
		} else {
			System.out.print("No wagon selected!\n");
		}
	}
	
	@FXML public void tfCreateWagon() {
		createWagon(tfWagonName.getText(), cbWagonTypes.getSelectionModel().getSelectedItem());
	}
	
	@FXML public void deleteWagon() {
		if (selectedWagon != null) {
			if (wagonService.deleteWagon(selectedWagon,selectedWagon.getName())) {
				txSelectedWagonName.setText("-");
				refreshWagonComboBox();
				
				Date date = Calendar.getInstance().getTime();  
	            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
	            String strDate = dateFormat.format(date);    
				this.logger.addlog("Deleted wagon with name: " + selectedWagon.getName() + " amount of seats: " + selectedWagon.getSeats()  + " @ " + strDate);
				this.logger.log();
				
				selectedWagon = null;
				refreshCanvasAferConnection();
			} else {
				System.out.print("Could delete the wagon \n");
			}
		} else {
			System.out.print("You must select a wagon first!\n");
		}
		
	}
	
	@FXML public void refreshWagonList() {
		refreshWagonComboBox();
	}
	
	public void createWagon(String wagonName, String wagonType) {
		boolean result = wagonService.createWagon(selectedTrain, wagonName, wagonType);
		if (result) {
			Wagon wagon = wagonService.getWagon(wagonName);
			cbWagons.setValue(wagon);
			refreshWagonComboBox();
			refreshTrainComboBox();
			selectedTrain = trainService.getTrain(selectedTrain.getLocomotive().getName());
			refreshCanvasAferConnection();
			tfWagonName.clear();
			
			Date date = Calendar.getInstance().getTime();  
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
            String strDate = dateFormat.format(date);    
			this.logger.addlog("Created wagon with name: " + wagon.getName() + " amount of seats: " +wagon.getSeats()  + " @ " + strDate);
			this.logger.log();
			
		} else {
			System.out.print("Couldn't create wagon! \n");
		}
	}

	public void clDeleteWagon(String wagonName) {
		Wagon object = wagonService.getWagon(wagonName);
		if (object != null) {
			selectedWagon = object;
			deleteWagon();
		} else {
			System.out.print("Wagon not found!\n");
		}
	}
	
	private void refreshWagonComboBox() {
		ObservableList<Wagon> wagons = FXCollections.observableArrayList(wagonService.getAllWagons());
		cbWagons.setItems(wagons);
		update(new Observable(), new Object());	
	}
	
	public void fillCbWagonTypes() {
		ObservableList<String> listOfSeats = FXCollections.observableArrayList("wagonOne","wagonTwo","wagonThree");
		cbWagonTypes.setItems(listOfSeats);
	}
	
	
	/*
	 * Canvas
	 */
	private void setupCanvas() {
		GraphicsContext graphicsContext = canvasRichRail.getGraphicsContext2D();
		graphicsContext.clearRect(0, 0, 3000, 1000);
	}
	
	public void refreshCanvasAferConnection() {
		setupCanvas();
		int totalWagons = 0;
		if (selectedTrain != null) {
			totalWagons = selectedTrain.getWagons().size();
			GraphicsContext gc = canvasRichRail.getGraphicsContext2D();
			drawTrain(gc);
			drawWagon(gc, totalWagons);
		} else {
			System.out.print("Selected train is null \n");
		}
		
	}
	
	private void drawTrain(GraphicsContext gc) {
		gc.setFill(Color.PINK);
        gc.strokeOval(100,5,10,10);
        gc.strokeOval(90,20,15,15);
        gc.strokeRoundRect(90, 40, 20, 20, 5, 5);
        gc.strokeRoundRect(50, 60, 60, 30, 10, 10);
        gc.fillOval(50, 90, 15, 15);
        gc.fillOval(95, 90, 15, 15);
    }
	
	private void drawWagon(GraphicsContext gc, int totalWagons) {
		for (int i = 0 ; i < totalWagons ; i++) {
			gc.setFill(Color.PINK);
	        gc.strokeRoundRect(130 + (i * 80), 60, 60, 30, 10, 10);
	        gc.fillOval(130 + (i * 80), 90, 15, 15);
	        gc.fillOval(175 + (i * 80), 90, 15, 15);
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		
		textbox.getChildren().clear();
		clbox.getChildren().clear();
		textbox.getChildren().add(new Text("Wagons: \n"));
		
		for (Wagon wagons : wagonService.getAllWagons()) {
			textbox.getChildren().add(new Text(wagons.toString() + "\n"));
		}
		
		textbox.getChildren().add(new Text("\n\nTrains:\n"));
		for (Train trains : trainService.getAllTrains()) {
			textbox.getChildren().add(new Text(trains.toString()));
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		trainService.getAllTrains();
		refreshTrainComboBox();
		refreshWagonComboBox();
		fillCbWagonTypes();
		update(new Observable(), new Object());
	}
}

