package app.utils;

import spark.*;

public class Filters {
    public static Filter addTrailingSlashes = (Request req, Response res) -> {
        if(!req.pathInfo().endsWith("/")) {
            res.redirect(req.pathInfo()+"/");
        }
    };
}