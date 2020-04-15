package DownloadAll;

/*"Скачивание картинок с сайта. На входе - URL страницы.
Необходимо скачать все изображения на странице а также на всех дочерних страницах, на которые можно перейти по гиперссылкам. Скачивать изображения только > X КБ. Требуется отчёт по выполненной работе с защитой архитектуры.
+параметры приложения (URL, каталог для сохранения, размер X и др.) задавать в property-файле [+5 баллов]
+использовать многопоточность для ускорения работы приложения, сравнить производительность [+25 баллов]
+выводить статистику во время работы приложения (какая картинка качается, сколько всего скачано) [+10 баллов]"*/


import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
    private static AtomicInteger downloadCounter = new AtomicInteger();
    private static String[] imageFormats =  {".png",".psd",".jpe",".jpg", ".gif"};
    private static double minSizeOfImage = 0.01;
    private static double maxSizeOfImage = 0.01;

    public static void main(String[] args) {
        //ArrayList<String> imageSrcArrayList = getImageSources("https://vk.com/id58172989");
        ArrayList<String> hyperlinksArrayList = getImageSources("https://vk.com/id58172989");

        boolean isImage = false;
        for (String link : hyperlinksArrayList) {
            System.out.println("1) link "+link);
            for (String imageFormat : imageFormats)
                if (link.contains(imageFormat))
                    isImage = true;


            if (!isImage) {
                ArrayList<String> imageSrcArrayList = getHyperlinks(link);
                for (String srcImage : imageSrcArrayList){
                    System.out.println("2) srcImage "+srcImage);
                    downloadImage(srcImage, minSizeOfImage, maxSizeOfImage);
                }
            } else {
                downloadImage(link, minSizeOfImage, maxSizeOfImage);
                System.out.println("3) link "+link);
            }

        }

    }



    public static ArrayList<String> getImageSources(String downloadFrom){
        ArrayList<String> imageSrcArrayList = new ArrayList<String>();
        Document doc;
        try {
            //get all images
            doc = Jsoup.connect(downloadFrom).get();
            Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");

            for (Element image : images) {
                String src = image.attr("src");


                for (String d : imageFormats)
                    if ( src.indexOf(d) != -1)
                        src = src.substring(0, src.indexOf(d)+4);

                imageSrcArrayList.add(src);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageSrcArrayList;
    }

    public static ArrayList<String> getHyperlinks(String downloadFrom){
        ArrayList<String> linksArrayList = new ArrayList<String>();
        Document doc;
        try {
            //need http protocol
            doc = Jsoup.connect(downloadFrom).get();

            //get page title
            String title = doc.title();
            System.out.println("title : " + title);

            //get all links
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                //get the value from href attribute
                linksArrayList.add("/"+downloadFrom+link.attr("href"));
                System.out.println("/"+downloadFrom+link.attr("href"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return linksArrayList;
    }

    public static void downloadImage(String urlString , double minSize, double maxSize){
      //  Thread thread = new Thread("new Thread ") {
        //    public void run() {
        //        System.out.println("run by: " + getName() + getId());


                String[] strings = urlString.split("/");
                String imageName = strings[strings.length - 1];

                Image image = null;
                try {
                    URL url = new URL(urlString);
                    try (InputStream in = new BufferedInputStream(url.openStream());
                         ByteArrayOutputStream out = new ByteArrayOutputStream();
                         FileOutputStream fos = new FileOutputStream("src\\DownloadAll\\" + imageName)) {

                        byte[] buf = new byte[1024 * (int) maxSize];
                        int n = 0;

                       /* while (inputStream.available() > 0) //пока есть еще непрочитанные байты
                        {
                            // прочитать очередной блок байт в переменную buffer и реальное количество в count
                            int count = inputStream.read(buffer);
                            outputStream.write(buffer, 0, count); //записать блок(часть блока) во второй поток
                        }*/

                        while (in.available()>0)
                            out.write(buf, 0, n);

                        if (((out.size() / 1024) >= minSize) && ((out.size() / 1024) <= maxSize)) {
                            System.out.println("Donloading in process. UmageName : " +
                                    imageName + "  Size : " + bytesToMegabytes(out.size()));
                            byte[] response = out.toByteArray();
                            fos.write(response);
                            System.out.println("   Total downloaded : "+
                                    downloadCounter.incrementAndGet());
                        }
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                } catch (MalformedURLException mue) {
                    System.out.println(mue);
                }

       // };
      //  thread.start();

    }


    public static String bytesToMegabytes(long bytes) {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %ciB", value / 1024.0, ci.current());
    }


}
