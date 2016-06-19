import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

/**
 * Solution to CS402 Assignment 3. A program that downloads a web page, parses and downloads embedded links.
 * @author Jamie McGowan
 */
public class DownloadParser {
    public static void main(String [] args) {
        String address;

        if(args.length > 0) {
            // Accept command line parameter for URL.
            address = args[0];
        }
        else {
            // Prompt user to enter URL (if no command line argument supplied).
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter URL of web page to be downloaded: ");
            address = scan.nextLine();
        }

        // Create folder to store downloaded files
        File dir = new File("Downloads");
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            System.out.println("Downloading: " + address);

            // Download the given document (timeout = 10s)
            Document doc = Jsoup.connect(address).timeout(10*1000).get();
            save(doc.body().toString(), address);

            System.out.println("Parsing");
            Elements links = doc.select("a[href]");

            String[] schemes = {"http","https"};
            UrlValidator urlValidator = new UrlValidator(schemes);

            // Parse all links (anchors-href)
            for (Element link : links) {
                // First check if the URL is valid
                if (urlValidator.isValid(String.valueOf(link.attr("href")))) {
                    System.out.println("Downloading: " + link.attr("href"));
                    Document page = Jsoup.connect(link.attr("href")).timeout(10*1000).get();

                    // Save the HTML body to file
                    save(page.body().toString(), link.attr("href"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sanitizes a filename, removing any invalid characters and replacing them with an underscore
     * @param url - The URL of the web page
     * @return String the sanitized filename
     */
    public static String editFilename(String url) {
        return url.replaceAll("[^a-zA-Z0-9.-]", "_") + ".html";
    }

    /**
     * Saves the HTML body to file
     * @param content - the HTML content
     * @param url - the URL of the data to be saved
     */
    public static void save(String content, String url) {
        // Sanitize filename
        String filename = "Downloads" + File.separator + editFilename(url);

        try {
            PrintWriter out = new PrintWriter(filename);
            out.print(content);
            out.flush();
            out.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}