package com.example;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.ArrayList;
//import org.openqa.selenium.JavascriptExecutor;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FullTest extends Elements {
    String grupos;
    String url;
    String urlTestApi;
    WebDriver driver;
    String texto;
    String urlApi;

    @Parameters({ "grupo", "testType" })
    @BeforeTest
    public void initializeVariables(String grupo, String testType) {
        System.out.println("BEFORE TEST");
        getUrl(grupo);
        System.out.println("URL BASE: " + url);
        System.out.println("GRUPO: " + grupo);
        System.out.println("Tipo de test: " + testType);

        driver = initializeChrome(driver);

    }

    @Test
    public void login() throws InterruptedException {
        System.out.println("LOGIN");
        try {
            driver.get(url);
            driver.manage().window().maximize();
            Assert.assertEquals(url, driver.getCurrentUrl());
            Thread.sleep(2000);
            sendKeys(driver, txtLoginUser, "staging");
            sendKeys(driver, txtLoginPass, "P4ssSt4g1ng");
            click(driver, btnLogin);
            Assert.assertEquals(url + "administrator/notas", driver.getCurrentUrl());
            System.out.println("LOGIN OK");
            Thread.sleep(2000);
            // driver2.quit();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }

    }

    @Test
    public void createNote() throws InterruptedException {
        System.out.println("Crear Nota");
        try {
            click(driver, btnNuevo);
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            checkTitlePage(driver, "Sin Titulo");
            Thread.sleep(2000);
            sendKeys(driver, txtVolantaNota, "Test Auto");
            sendKeys(driver, txtTituloNota, "Automation");
            sendKeys(driver, txtCopeteNota, "Auto-Copete");

            clickJS(driver, btnGrabarNota);

            // try {
            // WebElement elementGrabar = driver.findElement(btnGrabarNota);
            // JavascriptExecutor js = (JavascriptExecutor) driver;
            // js.executeScript("arguments[0].click()", elementGrabar);
            // System.out.println("CLICK GUARDAR");

            // Thread.sleep(3000);
            // } catch (Exception e) {
            // System.out.println("no se pudo clickear el objeto: " + e);
            // }

            // urlApi = driver.getCurrentUrl();
            Thread.sleep(2000);
            String idApi = getNoteIdApi(driver.getCurrentUrl());

            driver.close();
            driver.switchTo().window(tabs.get(0));
            headerNota = setHeaderNote("Automation");
            System.out.println("HEADER NOTA: " + headerNota.toString());
            visibiltyOf(driver, headerNota);

            String testApi = urlTestApi + idApi;
            driver.get(testApi);
            System.out.println(testApi);
            Thread.sleep(3000);
            visibiltyOf(driver, jsonTituloNota);
            visibiltyOf(driver, jsonCopeteNota);
            visibiltyOf(driver, jsonVolanteNota);
            jsonIdNota = setIdNote(idApi);
            visibiltyOf(driver, jsonIdNota);

            System.out.println("CREAR NOTA OK");

        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    // @Test
    public void createTag() throws InterruptedException {
        driver.get(url);

    }

    @AfterTest
    public void quit() {
        driver.quit();
    }

    public void getUrl(String check) {
        if (check.equals("america")) {
            grupos = check;
            url = "https://diariolasamericas-diariolasamericas.backend.staging.thinkindot.com/backend/";
            urlTestApi = "https://lasamericas-diariolasamericas-upgrade-81.backend.staging.thinkindot.com/2.0/public/articles/";
        }
    }

    public WebDriver initializeChrome(WebDriver explorer) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addExtensions(new File("JsonViewer.crx"));
        explorer = new ChromeDriver(options);
        return explorer;
    }
}
