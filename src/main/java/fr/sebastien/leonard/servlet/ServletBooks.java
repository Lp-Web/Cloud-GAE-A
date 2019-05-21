package fr.sebastien.leonard.servlet;

import com.google.cloud.sql.jdbc.internal.Url;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(name = "Books")
public class ServletBooks extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(request.getParameter("id") == null || request.getParameter("number") == null) {
                response.getWriter().println("You must give the id and number in parameters of the request");
                response.setStatus(400);
                return;
            }

            Long id = Long.parseLong(request.getParameter("id"));
            Long number = Long.parseLong(request.getParameter("number"));

            URL url = new URL(getUrl("GAE") + "?idToRemove=" + id + "&numberToRemove=" + number);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                response.setStatus(200);
                response.getWriter().println("The book with id: " + id + " is now taken.");
            } else {
                URL b = new URL(getUrl("Heroku") + "?idToAdd=" + id + "& numberToAdd=" + number);
                HttpURLConnection connectionB = (HttpURLConnection) b.openConnection();

                connectionB.setDoOutput(true);
                connectionB.setRequestMethod("PUT");

                response.setStatus(connectionB.getResponseCode());
                response.getWriter().println(connectionB.getResponseMessage());
            }

        } catch (NullPointerException ex) {
            response.getWriter().println("You must give the id, and number in parameters of the request");
            response.setStatus(400);
        }

    }

    private String getUrl(String engine) {
        return (engine.equalsIgnoreCase("Heroku")
                ? "http://gcloud-heroku.herokuapp.com/" : "http://iut-projet-238109.appspot.com/");
    }

}