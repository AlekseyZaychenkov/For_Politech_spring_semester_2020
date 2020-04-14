package DownloadAll;

import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    public static void main(String[] args) {

        Document doc;
        try {

            //get all images
            doc = Jsoup.connect("https://vk.com/id58172989").get();
            Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
            for (Element image : images) {

                System.out.println("\nsrc : " + image.attr("src"));
                System.out.println("height : " + image.attr("height"));
                System.out.println("width : " + image.attr("width"));
                System.out.println("alt : " + image.attr("alt"));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //downloadImage("https://sun9-70.userapi.com/c846124/v846124271/6303d/eApw-Kebor8.jpg");

    }

    public static void downloadImage(String urlString){
        String[] strings = urlString.split("/");
        String imageName = strings[strings.length-1];

        Image image = null;
        try {
            URL url = new URL(urlString);
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1!=(n=in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();


            FileOutputStream fos = new FileOutputStream("src\\DownloadAll\\"+imageName);
            fos.write(response);
            fos.close();
        } catch (IOException e) {
        }
    }
}
