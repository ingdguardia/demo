package com.example;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.Keys;
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
        System.out.println("##############TEST LOGIN##############");
        try {
            driver.get(url);
            driver.manage().window().maximize();
            Assert.assertEquals(url, driver.getCurrentUrl());
            Thread.sleep(2000);
            sendKeys(driver, inputLoginUser, "staging");
            sendKeys(driver, inputLoginPass, "P4ssSt4g1ng");
            click(driver, btnLogin);
            Assert.assertEquals(url + "administrator/notas", driver.getCurrentUrl());
            System.out.println("##############TEST LOGIN OK##############");
            Thread.sleep(2000);
            // driver2.quit();
        } catch (Exception e) {
            System.out.println("##############TEST LOGIN ERROR: " + e);
        }

    }

    @Test
    public void createNote() throws InterruptedException {
        System.out.println("##############TEST CREAR NOTA##############");
        try {
            click(driver, btnNuevo);
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            checkTitlePage(driver, "Sin Titulo");
            Thread.sleep(2000);
            sendKeys(driver, inputVolantaNota, "Test Auto");
            sendKeys(driver, inputTituloNota, "Automation");
            sendKeys(driver, inputCopeteNota, "Auto-Copete");

            clickJS(driver, btnGrabarNota);

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

            System.out.println("##############TEST CREAR NOTA OK##############");

        } catch (Exception e) {
            System.out.println("##############TEST CREAR NOTA ERROR: " + e);
        }
    }

    @Test
    public void createTag() throws InterruptedException {
        try {
            System.out.println("##############TEST CREAR AGRUPADOR##############");
            driver.navigate().back();
            click(driver, btnAgrupadores);
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            System.out.println(tabs.size());
            Thread.sleep(3000);
            driver.switchTo().window(tabs.get(1));
            click(driver, btnNuevoAgrupador);
            System.out.println(tabs.size());

            driver.switchTo().window(tabs.get(2));
            sendKeys(driver, inputNombreAgrupador, "prueba auto");
            click(driver, dropdownTipoAgrupador);
            click(driver, optionTipoAgrupadorTema);
            clickJS(driver, btnGrabarNota);
            driver.close();
            sendKeys(driver, inputFiltrarAgrupador, "prueba auto");
            driver.findElement(inputFiltrarAgrupador).sendKeys(Keys.ENTER);
            setHeaderNote("prueba auto");
            visibiltyOf(driver, headerNota);
            System.out.println("##############TEST AGRUPADOR OK##############");
        } catch (Exception e) {
            System.out.println("##############TEST CREAR AGRUPADOR ERROR: " + e);
        }
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
