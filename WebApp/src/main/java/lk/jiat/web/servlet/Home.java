package lk.jiat.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.web.core.remote.CurrencyConverter;


import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;

@WebServlet("/home")
public class Home extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("<h1>This is Home Page</h1>");

        try {
            InitialContext context = new InitialContext();
            CurrencyConverter converter = (CurrencyConverter) context.lookup("java:global/app-module/CurrencyConverterBean!lk.jiat.web.core.remote.CurrencyConverter");
            double lkr = converter.convertToLKR(1200);
            response.getWriter().println("LKR:" + lkr);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }


        //Portable JNDI names for EJB CurrencyConverterBean: [java:global/app-module/CurrencyConverterBean!lk.jiat.web.core.remote.CurrencyConverter, java:global/app-module/CurrencyConverterBean]]]
        //Glassfish-specific (Non-portable) JNDI names for EJB CurrencyConverterBean: [lk.jiat.web.core.remote.CurrencyConverter#lk.jiat.web.core.remote.CurrencyConverter, lk.jiat.web.core.remote.CurrencyConverter]]]
    }
}
