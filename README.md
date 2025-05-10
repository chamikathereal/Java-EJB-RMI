## 🚀 Java EJB RMI - Core, AppModule & WebApp (Currency Converter App)

### Overview

This repository contains three modular Java projects demonstrating the use of **Enterprise JavaBeans (EJB)**, **Remote Method Invocation (RMI)**, and **Web Integration** with **JSP**. The projects work together to create a simple application for currency conversion, featuring a modular design with a **Core** module, an **AppModule** (EJB implementation), and a **WebApp** (Servlet-based web interface).

### Table of Contents

1. [Project Structure](#project-structure)
2. [Core Module](#core-module)
3. [AppModule (EJB)](#appmodule-ejb)
4. [WebApp](#webapp)
5. [Running the Application](#running-the-application)
6. [Technologies Used](#technologies-used)
7. [License](#license)

---

### 📁 Project Structure

```
Java-EJB-RMI/
├── Core/
│   ├── src/main/java/lk/jiat/web/core/remote/CurrencyConverter.java
│   └── pom.xml
│
├── AppModule/
│   ├── src/main/java/lk/jiat/web/ejb/CurrencyConverterBean.java
│   └── pom.xml
│
└── WebApp/
    ├── src/main/java/lk/jiat/web/servlet/Home.java
    ├── src/main/webapp/index.jsp
    └── pom.xml
```

---

### 🔑 Core Module

The **Core** module defines the **`CurrencyConverter`** interface used for the conversion between USD and LKR. This is a remote interface, implemented by the **AppModule**.

#### Key Files:

* `CurrencyConverter.java`: Defines the remote interface for currency conversion.

#### Code:

```java
package lk.jiat.web.core.remote;

import jakarta.ejb.Remote;

@Remote
public interface CurrencyConverter {
    double convertToLKR(double amount);
    double convertToUSD(double amount);
}
```

---

### ⚙️ AppModule (EJB)

The **AppModule** implements the **`CurrencyConverter`** interface and contains the actual business logic for currency conversion. The `CurrencyConverterBean` is a stateless EJB bean that performs the conversion and manages the lifecycle of the bean.

#### Key Files:

* `CurrencyConverterBean.java`: Implements the currency conversion methods and lifecycle methods.

#### Code:

```java
package lk.jiat.web.ejb;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Stateless;
import lk.jiat.web.core.remote.CurrencyConverter;

@Stateless
public class CurrencyConverterBean implements CurrencyConverter {

    private static final double USD_TO_LKR_RATE = 300.00;

    public CurrencyConverterBean() {
        System.out.println("CurrencyConverterBean Constructor called: " + this.hashCode());
    }

    @PostConstruct
    public void init() {
        System.out.println("CurrencyConverterBean Created");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("CurrencyConverterBean Destroyed");
    }

    @Override
    public double convertToLKR(double amount) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return amount * USD_TO_LKR_RATE;
    }

    @Override
    public double convertToUSD(double amount) {
        return amount / USD_TO_LKR_RATE;
    }
}
```

---

### 🌐 WebApp

The **WebApp** is a simple Servlet-based web application that integrates with the **Core** and **AppModule** to display the currency conversion result. It uses the **CurrencyConverter** EJB to convert a fixed amount of USD to LKR and displays the result on a webpage.

#### Key Files:

* `Home.java`: The main servlet that interacts with the **CurrencyConverter** EJB and displays results.
* `index.jsp`: A simple JSP page displaying the results of the conversion.

#### Code (Home.java):

```java
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
            response.getWriter().println("LKR: " + lkr);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
```

#### Output:

```html
This is Home Page
LKR: 360000.0
```

---

### 🚀 Running the Application

#### Prerequisites:

1. **Java 11** (or newer)
2. **Maven**
3. **GlassFish** (or any JEE compatible server)

#### Steps to Run:

1. **Build the Projects**:

   * In each project directory (`Core`, `AppModule`, `WebApp`), run:

     ```bash
     mvn clean install
     ```
2. **Deploy `AppModule` on GlassFish**:

   * Deploy the `AppModule/target/app-module.war` to your GlassFish server.
3. **Start the WebApp**:

   * Deploy the `WebApp/target/webapp.war` to your server.
4. **Access the WebApp**:

   * Open your browser and visit `http://localhost:8080/webapp/home` to view the results.

---

### 🛠️ Technologies Used

* **Java 11**
* **Jakarta EE (formerly Java EE)** - for EJB, Servlets, and JNDI
* **Maven** - for build automation
* **GlassFish** - as the application server for running EJBs
* **JSP** - for the web interface

---

### 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
