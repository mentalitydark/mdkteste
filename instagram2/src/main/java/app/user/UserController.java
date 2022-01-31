package app.user;

import spark.*;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.HashMap;
import java.util.Map;


public class UserController {
    public static Route Signup = (req, res) -> {
        Boolean logged = req.session().attribute("logged");
        if(logged == null) {
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Sign up");
            return new VelocityTemplateEngine("UTF-8").render(new ModelAndView(model, "velocity/user/Signup.vm"));
        }
        res.redirect("/login/");
        return null;
    };
    public static Route RegisterUser = (req, res) -> {
        String userName = req.queryParams("username");
        String userEmail = req.queryParams("email");
        String password = req.queryParams("password");
        User user = new User(userName, userEmail);
        user.setPassword(password);
        UserDAO.insertUser(user);
        res.redirect("/");
        return null;
    };
    public static Route Auth = (req, res) -> {
        String username = req.queryParams("username");
        String password = req.queryParams("password");
        try {
            User user = UserDAO.authentication(username, password);
            if(user == null) {
                throw new Exception();
            }
            req.session(true);
            req.session().attribute("user_id", user.getId());
            req.session().attribute("username", user.getUsername());
            req.session().attribute("email", user.getEmail());
            req.session().attribute("logged", true);
            res.redirect("/upload/");
            return null;
        } catch (Exception e) {
            req.session(true);
            req.session().attribute("error_login", true);
            res.redirect("/login/");
            return null;
        }
    };
    public static Route Login = (req, res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Login");
        return new VelocityTemplateEngine("UTF-8").render(new ModelAndView(model, "velocity/user/login.vm"));
    };

    public static Route Logout = (req, res) -> {
        req.session().invalidate();
        res.redirect("/");
        return null;
    };
    public static Route Profile = (req, res) -> {
        Boolean logged = req.session().attribute("logged");
        if(logged == null) {
            res.redirect("/login/");
            return null;
        }
        res.redirect("/imgs/"+req.session().attribute("username"));
        return null;
    };
}
