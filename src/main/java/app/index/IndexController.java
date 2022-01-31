package app.index;

import java.util.Map;
import app.image.Image;
import app.image.ImageDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import spark.*;
import spark.template.velocity.VelocityTemplateEngine;

public class IndexController {
    public static Route IndexPage = (req, res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Index Page");
        model.put("username", req.session().attribute("username"));
        List<Image> images = ImageDAO.selectAll();
        Collections.reverse(images);
        List<Image> subimages = new ArrayList<>();
        if(images.size() >= 6) {
            subimages.addAll(images.subList(0, 6));
        } else {
            subimages.addAll(images);
        }
        model.put("images", subimages);
        return new VelocityTemplateEngine("UTF-8").render(new ModelAndView(model, "velocity/index/index.vm"));
    };
    public static Route NotFound = (req, res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Not Found");
        model.put("username", req.session().attribute("username"));
        return new VelocityTemplateEngine("UTF-8").render(new ModelAndView(model, "velocity/index/notFound.vm"));
    };
}