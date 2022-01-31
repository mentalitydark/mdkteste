package app;

import static spark.Spark.*;

import app.image.ImageController;
import app.index.IndexController;
import app.user.UserController;
import app.utils.Filters;
import app.utils.Port;

public class App {
    public static void main(String[] args) {
        port(Port.getHerokuAssignedPort());
        staticFiles.location("/public");
        staticFiles.externalLocation("uploads");
        before("*", Filters.addTrailingSlashes);
        notFound(IndexController.NotFound);

        // INDEX
        get("/", IndexController.IndexPage);

        // Image
        get("/upload/", ImageController.UploadPage);
        post("/upload/", ImageController.UploadImage);
        get("/imgs/:username/", ImageController.UserImages);

        // login
        get("/signup/", UserController.Signup);
        post("/registeruser/", UserController.RegisterUser);

        get("/login/", UserController.Login);
        post("/auth/", UserController.Auth);

        get("/logout/", UserController.Logout);

        // Profile
        get("/profile/", UserController.Profile);
    }
}
