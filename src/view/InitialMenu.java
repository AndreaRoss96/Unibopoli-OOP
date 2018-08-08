package view;

import application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class InitialMenu extends Application implements EventHandler<ActionEvent>{
	
	private static final Font TITLE_FONT = Font.font("Arial", FontWeight.NORMAL, 25);

	private static final int MAX_PLAYER = 6;

	final Button startButton = new Button();
	final Button reloadButton = new Button();
	final Button exitButton = new Button();
	final Button exitYes = new Button();
	final Button exitNo = new Button();
	final Button newPlayer = new Button();
	final Button okPlayer = new Button();
	final Button cancelPlayer = new Button();
	final Button icn1 = new Button();
	final Button icn2 = new Button();
	final Button icn3 = new Button();
	final Button icn4 = new Button();
	final Button icn5 = new Button();
	final Button icn6 = new Button();
	double x = 90;
	int playerNum;

	public void start(Stage primaryStage) throws Exception {

		
		primaryStage.setTitle("Welcome To Unibopoly");

		//Stile dei bottoni principali
		startButton.setText("START");
		startButton.setPrefWidth(100);
		startButton.setPrefHeight(70);
		reloadButton.setText("LOAD");
		reloadButton.setPrefWidth(100);
		reloadButton.setPrefHeight(70);
		exitButton.setText("EXIT");
		exitButton.setPrefWidth(100);
		exitButton.setPrefHeight(70);
		newPlayer.setText("Add Player");
		newPlayer.setPrefWidth(100);
		newPlayer.setPrefHeight(30);
		okPlayer.setText("OK");
		okPlayer.setPrefWidth(40);
		okPlayer.setPrefHeight(30);
		cancelPlayer.setText("DEL");
		cancelPlayer.setPrefWidth(50);
		cancelPlayer.setPrefHeight(30);
		
		//Bottoni finestra uscita
		exitYes.setText("YES");
		exitYes.setPrefWidth(50);
		exitYes.setPrefHeight(20);
		exitNo.setText("NO");
		exitNo.setPrefWidth(50);
		exitNo.setPrefHeight(20);
		
		//Bottoni icone
		
		
//		BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("/res/Icons/name.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
//        Background background = new Background(backgroundImage);
//        
//		icn1.setBackground(background);
//		icn2.setBackground(background);
//		icn3.setBackground(background);
//		icn4.setBackground(background);
//		icn5.setBackground(background);
//		icn6.setBackground(background);
		
		icn1.setText("1");
		icn2.setText("2");
		icn3.setText("3");
		icn4.setText("4");
		icn5.setText("5");
		icn6.setText("6");
		
		//Creazione AnchorPane principale
		AnchorPane layout1 = new AnchorPane();
		layout1.setPrefSize(900, 800);
		Label welcome = new Label("Benvenuto in Unibopoli, inserisci i dati dei giocatori");
		welcome.setFont(TITLE_FONT);
		layout1.getChildren().add(newPlayer);
		AnchorPane.setTopAnchor(newPlayer, 40d);
		AnchorPane.setLeftAnchor(newPlayer, 30d);
		//layout1.setStyle("-fx-background-color: red;");
		
		//Setup Menu Principale
		
		
		//Start Load Exit Button
		HBox botMenu = new HBox();
		//botMenu.setStyle("-fx-background-color: grey;");
		botMenu.setSpacing(4);
		botMenu.getChildren().addAll(startButton, reloadButton, exitButton);
		
		//Yes No Button
		HBox exitMenu = new HBox();
		exitMenu.setSpacing(4);
		exitMenu.getChildren().addAll(exitNo, exitYes);
		
		//Aggiunta della HBox
		AnchorPane.setBottomAnchor(botMenu, 0d);
		AnchorPane.setLeftAnchor(botMenu, 0d);
		layout1.getChildren().addAll(botMenu, welcome);
		
		
		//AnchorPane di Uscita
		final AnchorPane layout2 = new AnchorPane();
		layout2.setPrefSize(300, 250);
		
		Text text = new Text("Sei sicuro di voler uscire?");
		text.setFont(TITLE_FONT);
		layout2.getChildren().addAll(exitMenu, text);
		
		
		
		//Setting AnchorPane uscita
		
		
		//Creazione delle due scene
		Scene defaultScene = new Scene(layout1, 900, 800);
		Scene exitScene = new Scene(layout2, 300, 250);
		
		primaryStage.setScene(defaultScene);

		// startButton.setOnAction(e -> /*NEW GAME*/ );

		// reloadButton.setOnAction(e -> /*RELOAD GAME*/);

		exitButton.setOnAction(e -> primaryStage.setScene(exitScene));

		exitYes.setOnAction(e -> System.exit(0));

		exitNo.setOnAction(e -> primaryStage.setScene(defaultScene));
		
		newPlayer.setOnAction(new EventHandler<ActionEvent>() {
					
			@Override
					public void handle(ActionEvent event) {
				if (playerNum == MAX_PLAYER) {
					newPlayer.setDisable(true);
				}
							AnchorPane playerCreated = createPlayerPane();
							layout1.getChildren().add(playerCreated);
							playerNum ++;
					}
				});	
				

	primaryStage.show();

	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub

	}

	private AnchorPane createPlayerPane() {

		AnchorPane player = new AnchorPane();
		player.setPrefSize(450, 70);
		player.setStyle("-fx-background-color: blue;");
		TextField userTextField = new TextField("Add Player Name");
		AnchorPane.setTopAnchor(userTextField, 23d);
		AnchorPane.setTopAnchor(player, x);

		// AnchorPane per icone
		AnchorPane icnsPane = new AnchorPane();
		icnsPane.setPrefSize(60, 70);
		AnchorPane.setLeftAnchor(icnsPane, 250d);

		// TilePane per posizionare icone a griglia
		TilePane buttnPane = new TilePane();
		buttnPane.setPrefRows(2);
		buttnPane.setPrefColumns(3);
		buttnPane.setTileAlignment(Pos.CENTER_LEFT);
		buttnPane.setHgap(10);
		buttnPane.setVgap(10);
		buttnPane.getChildren().addAll(icn1, icn2, icn3, icn4, icn5, icn6);
		
		AnchorPane.setTopAnchor(buttnPane, 10d);
		AnchorPane.setRightAnchor(buttnPane, 50d);
		icnsPane.getChildren().addAll(buttnPane);
		icnsPane.setStyle("-fx-background-color: black;");
		
		HBox okCancelMenu = new HBox();
		okCancelMenu.setSpacing(4);
		okCancelMenu.getChildren().addAll(okPlayer, cancelPlayer);
		okCancelMenu.setStyle("-fx-background-color: green;");

		AnchorPane.setTopAnchor(okCancelMenu, 35d);
		AnchorPane.setLeftAnchor(okCancelMenu, 780d);
		
		player.getChildren().addAll(userTextField, okCancelMenu, icnsPane);
	
		
		x += 90;
		return player;

	}

}