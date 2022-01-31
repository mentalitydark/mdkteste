package app.image;

import spark.*;
import spark.template.velocity.VelocityTemplateEngine;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.MultipartConfigElement;

public class ImageController {
    public static Route UploadPage = (req, res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Upload Image");
        model.put("username", req.session().attribute("username"));
        Boolean logged = req.session().attribute("logged");
        if(logged == null) {
            res.redirect("/login/");
            return null;
        }
        
        return new VelocityTemplateEngine("UTF-8").render(new ModelAndView(model, "velocity/image/imageForm.vm"));
    };
    public static Route UploadImage = (req, res) -> {
        Boolean logged = req.session().attribute("logged");
        if(logged == null) {
            res.redirect("/login/");
            return null;
        }

        req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        String pathName = "uploads/"+req.session().attribute("username");
        File uploadDir = new File(pathName);
        uploadDir.mkdir();

        try (InputStream inputStream = req.raw().getPart("uploadedFile").getInputStream()) {
            String filename = req.raw().getPart("uploadedFile").getSubmittedFileName();
            Path path = Files.createTempFile(uploadDir.toPath(), "", filename);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

            String imageName = req.queryParams("name");
            Integer userId = req.session().attribute("user_id");

            Image image = new Image(imageName, path.getFileName().toString(), userId);
            ImageDAO.insertImage(image);

            res.redirect("/");
            return null;
        } catch (Exception e) {
            e.printStackTrace();

            res.redirect("/");
            return null;
        }
        
    };
    public static Route UserImages = (req, res) -> {
        Map<String, Object> model = new HashMap<>();
        String username = req.params(":username");
        model.put("title", username+" Images");
        model.put("username", req.session().attribute("username"));
        List<Image> images = ImageDAO.selectUserImages(username);
        model.put("images", images);
        model.put("imgs", false);
        if(images.size() == 0) model.put("user", false);
        else {
            model.put("user", true);
            if(images.get(0).getName() == null) model.put("imgs", false);
            else model.put("imgs", true);
        }
        return new VelocityTemplateEngine("UTF-8").render(new ModelAndView(model, "velocity/image/userImages.vm"));
    };
}
