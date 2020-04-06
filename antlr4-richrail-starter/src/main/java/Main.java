//import org.antlr.v4.runtime.CharStream;
//import org.antlr.v4.runtime.CharStreams;
//import org.antlr.v4.runtime.CommonTokenStream;
//import org.antlr.v4.runtime.Lexer;
//import org.antlr.v4.runtime.tree.ParseTree;
//import org.antlr.v4.runtime.tree.ParseTreeWalker;
//
//import com.richrail.repository.TrainDAO;
//import com.richrail.repository.TrainDAOImpl;
//
//import parser.*;
//
//public class Main {
//    public static void main(String[] args) {
//        CharStream lineStream = CharStreams.fromString("new train tr1");
//
//        // Tokenize / Lexical analysis
////        Lexer lexer = new RichRailLexer(lineStream);
////        CommonTokenStream tokens = new CommonTokenStream(lexer);
////
////        // Create Parse Tree
////        RichRailParser parser = new RichRailParser(tokens);
////        ParseTree tree = parser.command();
////
////        // Create ParseTreeWalker and Custom Listener
////        ParseTreeWalker walker = new ParseTreeWalker();
////        RichRailListener listener = new RichRailCli();
////
////        // Walk over ParseTree using Custom Listener that listens to enter/exit events
////        walker.walk(listener, tree);
//        
//        
//    }
//}

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private static final String APP_TITLE = "From Poorrail to Richrail";
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(APP_TITLE);
		URL path = getClass().getResource("/gui.fxml");
		Parent panel = FXMLLoader.load(path);
		primaryStage.setScene(new Scene(panel));
		primaryStage.show();
	}
}
