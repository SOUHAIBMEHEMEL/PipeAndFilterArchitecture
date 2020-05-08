package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {
    TextField number1_field;
    TextField number2_field;
    Button somme;
    Button produit;
    Button fact;
    Label results_number_label;
    Button quit;
    Button trace;




    public String getNumber1_field(){
        return number1_field.getText();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("CMPG");
        VBox main = new VBox();

        // Title bar
        buildTitleSection(main);
        addSeparator(main,new Separator());

        // Number bar
        buildNumbersSection(main);
        addSeparator(main,new Separator());


        /// operations bar
        buildOperationsSection(main);
        addSeparator(main,new Separator());


        // results bar
        buildResultsSection(main);
        addSeparator(main,new Separator());


        // last bar
        buildSettingsSection(main);

        // handle events
        somme.setOnMouseClicked((MouseEvent event) -> {
            handle_events(0);
        });
        produit.setOnMouseClicked((MouseEvent event) -> {
            handle_events(1);
        });
        fact.setOnMouseClicked((MouseEvent event) -> {
            handle_events(2);
        });


        setScene(main,primaryStage);
    }

    public void handle_events(int op){
        char operation;

        // get text fields
        int number1 = Integer.parseInt(number1_field.getText());
        int number2;

        if (op==2){number2 = 0;}
        else {number2 = Integer.parseInt(number2_field.getText());}

        if (op==0){operation='s';}
        else {
            if (op==1){operation='p';}
            else {operation='f';}
        }


        Pipe p0 = new BlockingQueue();
        Pipe p1 = new BlockingQueue();
        Pipe p2 = new BlockingQueue();
        Pipe p3 = new BlockingQueue();

        // get data from GUI to FilterA
        p0.dataIN(""+operation+"-"+number1+"-"+number2);


        Filter a1 = new FilterA(p0,p1);
        Filter b1 = new FilterB(p1,p2);
        Filter c1 = new FilterC(p2,p3);
        Filter d1 = new FilterD(p3,null);

        Thread th1 = new Thread( a1 );
        Thread th2 = new Thread( b1 );
        Thread th3 = new Thread( c1 );
        Thread th4 = new Thread( d1 );

        th1.start();
        th2.start();
        th3.start();
        th4.start();

        //get data from Trace and show them in dialog
        String out = p3.dataOUT();
        String[] arrOfStr = out.split("-");
        int result = Integer.parseInt(arrOfStr[3]);
        results_number_label.setText(""+result);

        trace.setOnMouseClicked((MouseEvent event) -> {
            String traceOutput=" Operation: { operation: "+arrOfStr[0]+", opg: "+arrOfStr[1]+", opd: "+arrOfStr[2]+", res: "+arrOfStr[3]+"}";
            Dialog d=new Alert(Alert.AlertType.INFORMATION,traceOutput);
            d.show();
        });


    }

    public HBox horiz_pane() {
        HBox pane = new HBox();
        pane.setAlignment(Pos.CENTER);
        pane.setPrefHeight(100);
        pane.setPadding(new Insets(10, 10, 10, 10));
        return pane;
    }
    public VBox vert_pane(){
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setPrefHeight(100);
        pane.setPadding(new Insets(10, 10, 10, 10));
        return pane;
    }

    public void buildTitleSection(VBox main){
        HBox title = horiz_pane();
        Text title_label = new Text("Les formes et les vues");
        title_label.setFont(new Font(20));
        title.getChildren().add(title_label);
        main.getChildren().add(title);
    }

    public void addSeparator(VBox main,Separator sep){
        sep.setOrientation(Orientation.HORIZONTAL);
        main.getChildren().add(sep);
    }

    public void buildNumbersSection(VBox main){
        HBox numbers = horiz_pane();
        Label number1_label = new Label("Nombre 1");
        number1_label.setPadding(new Insets(10, 10, 10, 10));
        number1_field = new TextField();
        HBox.setMargin(number1_field,new Insets(0, 50, 0, 0));
        Label number2_label = new Label("Nombre 2");
        number2_label.setPadding(new Insets(10, 10, 10, 10));
        number2_field = new TextField();
        HBox.setMargin(number2_field,new Insets(0, 50, 0, 0));
        numbers.getChildren().addAll(number1_label,number1_field,number2_label,number2_field);
        main.getChildren().add(numbers);
    }

    public void buildOperationsSection(VBox main){
        HBox operations = horiz_pane();

        somme = new Button("Somme");
        somme.setPrefWidth(100);
        HBox.setMargin(somme,new Insets(0, 50, 0, 0));
        produit = new Button("Produit");
        produit.setPrefWidth(100);
        HBox.setMargin(produit,new Insets(0, 50, 0, 0));
        fact = new Button("Factoriel");
        fact.setPrefWidth(100);
        operations.getChildren().addAll(somme,produit,fact);
        main.getChildren().add(operations);
    }

    public void buildResultsSection(VBox main){
        VBox results = vert_pane();
        Label results_label = new Label("Résultats");
        results_label.setFont(new Font(16));
        VBox.setMargin(results_label,new Insets(10,0,20,0));
        results_number_label = new Label("_ _");
        results.getChildren().addAll(results_label,results_number_label);
        main.getChildren().add(results);
    }

    public void buildSettingsSection(VBox main){
        HBox settings = horiz_pane();
        quit = new Button("Quitter");
        quit.setPrefWidth(100);
        HBox.setMargin(quit,new Insets(0, 50, 0, 0));
        quit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
        trace = new Button("Trace");
        trace.setPrefWidth(100);
        settings.getChildren().addAll(quit,trace);

        main.getChildren().add(settings);
    }
    public void setScene(VBox main, Stage primaryStage){
        Scene root = new Scene(main,600,500);
        primaryStage.setScene(root);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
