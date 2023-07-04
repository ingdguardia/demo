package com.example;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import org.openqa.selenium.JavascriptExecutor;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FullTest extends Elements {
    String grupos;
    String url;
    WebDriver driver;
    String texto;

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
            sendKeys(driver, txtVolantaNota, "Test Auto");
            sendKeys(driver, txtTituloNota, "Automation");
            sendKeys(driver, txtCopeteNota, "Auto-Copete");
            // sendKeys(driver, txtCuerpoNota, " texto de prueba");
            Thread.sleep(2000);
            // driver.switchTo().frame("cuerpoiframe");
            // System.out.println("switch iframe");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.getElementsByTagName('p')[0].innerHTML='Hola Mundo';");
            // click(driver, txtCuerpoNota);
            // sendKeys(driver, txtCuerpoNota, "TEXTO DE PRUEBA");
            // driver.findElement(txtCuerpoNota).sendKeys("TEXTO DE PRUEBA");
            Thread.sleep(2000);

            // driver.switchTo().parentFrame();
            // System.out.println("switch iframe");

            click(driver, btnGrabarNota);
            driver.close();
            driver.switchTo().window(tabs.get(0));
            setHeaderNote("Automation");
            System.out.println("HEADER NOTA: " + headerNota.toString());
            visibiltyOf(driver, headerNota);

            System.out.println("CREAR NOTA OK");
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
        driver.quit();
    }

    // @Test
    public void testApp3(String url) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=800x600");
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);
        System.out.println("TEST 3");
        Thread.sleep(10000);
        driver.quit();
    }

    @AfterTest
    public void quit() {
        driver.quit();
    }

    public void getUrl(String check) {
        if (check.equals("america")) {
            grupos = check;
            url = "https://diariolasamericas-diariolasamericas.backend.staging.thinkindot.com/backend/";
        }
    }

    public WebDriver initializeChrome(WebDriver explorer) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-position=1,1");
        options.addArguments("--window-size=800x600");
        explorer = new ChromeDriver(options);
        return explorer;
    }
}
