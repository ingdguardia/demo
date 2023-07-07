package com.example;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
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
    public void test0() throws InterruptedException {
        System.out.println("##############TEST LOGIN##############");
        login();

    }

    @Test
    public void test1() throws InterruptedException {
        System.out.println("##############TEST CREAR NOTA##############");
        createNote("Automation", "Test Auto", "Auto-Copete");
    }

    @Test
    public void test2() throws InterruptedException {
        driver.navigate().back();
        System.out.println("##############TEST CREAR TAG##############");
        createTag("prueba auto");
        deleteTag("prueba auto");
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

    public void createTag(String tagName) {
        try {
            System.out.println("##############CREAR AGRUPADOR##############");
            click(driver, btnAgrupadores);
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            System.out.println(tabs.size());
            driver.switchTo().window(tabs.get(1));
            click(driver, btnNuevo);
            tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(2));
            sendKeys(driver, inputNombreAgrupador, tagName);
            clickJS(driver, dropdownTipoAgrupador);
            click(driver, optionTipoAgrupadorTema);
            clickJS(driver, btnGrabarNota);
            Thread.sleep(3000);
            visibiltyOf(driver, alertGuardado);
            driver.close();
            driver.switchTo().window(tabs.get(1));
            sendKeys(driver, inputFiltrarAgrupador, tagName);
            driver.findElement(inputFiltrarAgrupador).sendKeys(Keys.ENTER);
            setHeaderNote(tagName);
            visibiltyOf(driver, headerNota);
            System.out.println("##############CREAR AGRUPADOR OK##############");
        } catch (Exception e) {
            System.out.println("##############CREAR AGRUPADOR ERROR: " + e);
        }
    }

    public void createNote(String titulo, String volanta, String copete) {
        System.out.println("##############CREAR NOTA##############");
        try {
            click(driver, btnNuevo);
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            checkTitlePage(driver, "Sin Titulo");
            Thread.sleep(5000);
            driver.switchTo().frame("cuerpoiframe");
            sendKeys(driver, inputCuerpoNota, "TEST CUERPO");
            driver.switchTo().parentFrame();
            sendKeys(driver, inputVolantaNota, volanta);
            sendKeys(driver, inputTituloNota, titulo);
            sendKeys(driver, inputCopeteNota, copete);

            clickJS(driver, btnGrabarNota);

            // urlApi = driver.getCurrentUrl();
            Thread.sleep(2000);
            String idApi = getNoteIdApi(driver.getCurrentUrl());

            driver.close();
            driver.switchTo().window(tabs.get(0));
            headerNota = setHeaderNote(titulo);
            System.out.println("HEADER NOTA: " + headerNota.toString());
            visibiltyOf(driver, headerNota);

            String testApi = urlTestApi + idApi;
            driver.get(testApi);
            System.out.println(testApi);
            Thread.sleep(3000);
            jsonTituloNota = By.xpath("//span[contains(text(), '" + titulo + "')]");
            jsonCopeteNota = By.xpath("//span[contains(text(), '" + copete + "')]");
            jsonVolanteNota = By.xpath("//span[contains(text(), '" + volanta + "')]");
            visibiltyOf(driver, jsonTituloNota);
            visibiltyOf(driver, jsonCopeteNota);
            visibiltyOf(driver, jsonVolanteNota);
            jsonIdNota = setIdNote(idApi);
            visibiltyOf(driver, jsonIdNota);

            System.out.println("##############CREAR NOTA OK##############");

        } catch (Exception e) {
            System.out.println("##############CREAR NOTA ERROR: " + e);
        }
    }

    public void login() {
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

    public void deleteTag(String tagName) throws InterruptedException {
        System.out.println("##############BORRANDO TAG##############");
        System.out.println(driver.getCurrentUrl());
        if (driver.getCurrentUrl().equals(url + "administrator/tagsContenido")
                || driver.getCurrentUrl().equals(url + "administrator/agrupadoresContenido")) {
            System.out.println("##############SECCION TAGS OK##############");
            sendKeys(driver, inputFiltrarAgrupador, tagName);
            driver.findElement(inputFiltrarAgrupador).sendKeys(Keys.ENTER);
        } else {
            System.out.println("##############CLICK SECCION TAGS##############");
            click(driver, btnAgrupadores);
            sendKeys(driver, inputFiltrarAgrupador, tagName);
            driver.findElement(inputFiltrarAgrupador).sendKeys(Keys.ENTER);
        }
        click(driver, headerNota);
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(2));
        Thread.sleep(5000);
        clickJS(driver, btnInfo);
        clickJS(driver, btnEliminar);
        clickJS(driver, btnSi);
        driver.switchTo().window(tabs.get(1));
        visibiltyOf(driver, lblNotFound);
        System.out.println("##############TAG BORRADO##############");
    }
}
