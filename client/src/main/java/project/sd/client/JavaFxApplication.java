package project.sd.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import project.sd.client.controller.*;

public class JavaFxApplication extends Application {

    private Stage window;
    private static ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);


        applicationContext = new SpringApplicationBuilder()
                .sources(ClientApplication.class)
                .run(args);
    }

    public static Scene changeScene(String controllerName) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent pane;

        switch (controllerName){
            case "admin":
                pane = fxWeaver.loadView(AdminController.class);
                break;
            case "users":
                pane = fxWeaver.loadView(UserController.class);
                break;
            case "books":
                pane = fxWeaver.loadView(BookController.class);
                break;
            case "regularUser":
                pane = fxWeaver.loadView(RegularUserController.class);
                break;
            case "ratings":
                pane = fxWeaver.loadView(RatingsController.class);
                break;
            case "reviews":
                pane = fxWeaver.loadView(ReviewsController.class);
                break;
            case "borrows":
                pane = fxWeaver.loadView(BorrowsController.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + controllerName);
        }

        Stage stage = new Stage();
        Scene newScene = new Scene(pane);
        stage.setScene(newScene);
        stage.show();

        return newScene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(LoginController.class);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
}
