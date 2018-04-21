package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ParseManager {

    private String url;

    private Document doc;


    /**
     * @param url
     */
    public ParseManager(String url) {
        this.url = url;
    }

    /**
     * Loads site as document by URL
     */
    private void loadDoc() {
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get site title
     *
     * @return String title
     */
    public String getTitle() {
        loadDoc();
        Element titleElement = doc.getElementById("firstHeading");
        String title = titleElement.text();
        return title;
    }

    /**
     * Parsing data to obtain image source
     *
     * @return String imageSrc
     */
    private String getImageSrc() {
        loadDoc();
        Element parentElement = doc.getElementsByClass("mw-parser-output").first();
        Element tableElement = parentElement.getElementsByTag("table").first();
        Element imageAnchorClass = tableElement.select("a[class=image]").first();
        Element imageTag = imageAnchorClass.getElementsByTag("img").first();
        String imageSrc = imageTag.attr("src");
        return imageSrc;
    }

    /**
     * Getting image from the site and save it to the file system
     *
     * @throws IOException
     */
    public void getAndSaveImage() {
        String imageSrc = getImageSrc();
        String imageUrl = imageSrc.replace(getImageSrc().substring(0, 2), "");
        String imageFormat = imageUrl.substring(imageUrl.lastIndexOf(".") + 1, imageUrl.length());
        String imageName = getTitle();
        File imageFile = new File("img/" + imageName + "." + imageFormat);
        try {
            //read image from URL
            BufferedImage reader = ImageIO.read(new URL("https://" + imageUrl));
            if (imageFile.exists() && !imageFile.isDirectory()) {
                System.out.println("File is already exists!");
            } else {
                //write image to file
                ImageIO.write(reader, imageFormat, imageFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
