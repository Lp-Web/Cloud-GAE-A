package fr.sebastien.leonard.servlet;

import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.cloud.sql.jdbc.internal.Url;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import fr.sebastien.leonard.servlet.model.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

@WebServlet(name = "Books")
public class ServletBooks extends HttpServlet {

    private Logger logger = Logger.getLogger("ServletBooks");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(request.getParameter("id") == null || request.getParameter("number") == null) {
                response.setHeader("Content-Type", "application/json");
                response.getWriter().println(new Gson().toJson(new Response("You must give the id and number in parameters of the request")));
                response.setStatus(400);
                return;
            }

            Long id = Long.parseLong(request.getParameter("id"));
            Long number = Long.parseLong(request.getParameter("number"));

            String a = "https://iut-projet-238109.appspot.com/";
            a = a.trim();

            HttpResponse queryA = Unirest.post(a)
                    .queryString("idToRemove", id)
                    .queryString("numberToRemove", number)
                    .body(new String("")).asString();

            if(queryA.getStatus() != 200) {

                String b = "https://gcloud-heroku.herokuapp.com/";
                b = b.trim();

                HttpResponse queryB = Unirest.put(b)
                        .queryString("idToAdd", id)
                        .queryString("numberToAdd", number)
                        .body(new String(""))
                        .asString();

                response.setStatus(queryB.getStatus());
                response.setHeader("Content-Type", "application/json");
                response.getWriter().println(new Gson().toJson(new Response(queryB.getBody().toString().replace("\n", ""))));
            } else {
                response.setHeader("Content-Type", "application/json");
                response.getWriter().println(new Gson().toJson(new Response(queryA.getBody().toString().replace("\n", ""))));
                response.setStatus(200);
            }

        } catch (NullPointerException ex) {
            response.setHeader("Content-Type", "application/json");
            response.getWriter().println(new Gson().toJson(new Response("You must give the id, and number in parameters of the request")));
            response.setStatus(400);
        } catch (UnirestException ex) {
            response.setHeader("Content-Type", "application/json");
            ex.printStackTrace();
            response.getWriter().println(new Gson().toJson(new Response(ex.getMessage())));
            response.setStatus(400);
        }

    }

    /*
    URL url = new URL("https://iut-projet-238109.appspot.com?idToRemove=" + id + "&numberToRemove=" + number);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setFixedLengthStreamingMode(0);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            if (connection.getResponseCode() == 200) {
                response.setStatus(200);
                //response.setHeader("Content-Type", "application/json");
                //response.getWriter().println(new Gson().toJson(new Response("The book with id: " + id + " is now taken.")));

                response.getWriter().println("ok");

                connection.disconnect();
            } else {
                URL b = new URL("https://gcloud-heroku.herokuapp.com/?idToAdd=" + id + "&numberToAdd=" + number);
                HttpURLConnection connectionB = (HttpURLConnection) b.openConnection();

                connectionB.setDoOutput(true);
                connectionB.setRequestMethod("PUT");

                response.setStatus(connectionB.getResponseCode());
                //response.setHeader("Content-Type", "application/json");
                //response.getWriter().println(new Gson().toJson(new Response(connectionB.getResponseMessage())));
                response.getWriter().println(connectionB.getResponseMessage());
                connectionB.disconnect();
            }
     */

}