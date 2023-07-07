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

    // @Test
    public void test0() throws InterruptedException {
        System.out.println("##############TEST LOGIN##############");
        login();

    }

    // @Test
    public void test1() throws InterruptedException {
        System.out.println("##############TEST CREAR NOTA##############");
        // createNote("Automation", "Test Auto", "Auto-Copete");
        deleteNote("Automation");
    }

    // @Test
    public void test2() throws InterruptedException {
        System.out.println("##############TEST CREAR TAG##############");
        // createTag("Tag test");
        deleteTag("Tag test");
        createCategory("Categoria test");
        deleteCategory("Categoria test");
    }

    @Test
    public void test3() throws InterruptedException {
        login();
        createTag("Tag test full", "Tema");
        createCategory("Category test full");
        createNote("Automation Full", "Volanta auto", "Copete auto", "Tag test full", "Tema", "Category test full");
        deleteTag("Tag test full");
        deleteCategory("Category test full");
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

    public void createTag(String tagName, String tipoTag) {
        try {
            System.out.println("##############CREAR AGRUPADOR##############");
            click(driver, btnAgrupadores);
            Thread.sleep(2000);
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            clickJS(driver, btnNuevo);
            tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(2));
            sendKeys(driver, inputNombreObjeto, tagName);
            clickJS(driver, dropdownTipoAgrupador);
            optionTipoAgrupadorTema = By.xpath("(//li//span[text()='" + tipoTag + "'])[1]");
            click(driver, optionTipoAgrupadorTema);
            clickJS(driver, btnGrabar);
            Thread.sleep(3000);
            visibiltyOf(driver, alertGuardado);
            driver.close();
            driver.switchTo().window(tabs.get(1));
            sendKeys(driver, inputFiltrar, tagName);
            driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            setHeaderNote(tagName);
            visibiltyOf(driver, headerObjeto);
            System.out.println("##############CREAR AGRUPADOR OK##############");
        } catch (Exception e) {
            System.out.println("##############CREAR AGRUPADOR ERROR: " + e);
        }
    }

    public void createNote(String titulo, String volanta, String copete, String tag, String tipoTag, String category) {
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
            sendKeys(driver, inputAgrupadoresNota, tag);
            optionAgrupadores = By.xpath("//li//span[text()='" + tipoTag + " » " + tag + "']");
            clickJS(driver, optionAgrupadores);
            visibiltyOf(driver, chipCategoriasTag);
            sendKeys(driver, inputCategoriasNota, category);
            optionAgrupadores = By.xpath("//li//span[text()='" + category + "']");
            clickJS(driver, optionCategorias);
            visibiltyOf(driver, chipCategoriasTag);

            clickJS(driver, btnGrabar);

            // urlApi = driver.getCurrentUrl();
            Thread.sleep(2000);
            String idApi = getNoteIdApi(driver.getCurrentUrl());

            driver.close();
            driver.switchTo().window(tabs.get(0));
            headerObjeto = setHeaderNote(titulo);
            System.out.println("HEADER NOTA: " + headerObjeto.toString());
            visibiltyOf(driver, headerObjeto);

            String testApi = urlTestApi + idApi;
            driver.get(testApi);
            System.out.println(testApi);
            Thread.sleep(3000);
            jsonTituloNota = By.xpath("//span[contains(text(), '" + titulo + "')]");
            jsonCopeteNota = By.xpath("//span[contains(text(), '" + copete + "')]");
            jsonVolantaNota = By.xpath("//span[contains(text(), '" + volanta + "')]");
            visibiltyOf(driver, jsonTituloNota);
            visibiltyOf(driver, jsonCopeteNota);
            visibiltyOf(driver, jsonVolantaNota);
            jsonIdNota = setIdNote(idApi);
            visibiltyOf(driver, jsonIdNota);
            driver.navigate().back();
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
        try {

            System.out.println(driver.getCurrentUrl());
            if (driver.getCurrentUrl().equals(url + "administrator/tagsContenido")
                    || driver.getCurrentUrl().equals(url + "administrator/agrupadoresContenido")) {
                System.out.println("##############SECCION TAGS OK##############");
                sendKeys(driver, inputFiltrar, tagName);
                driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            } else {
                System.out.println("##############CLICK SECCION TAGS##############");
                click(driver, btnAgrupadores);
                sendKeys(driver, inputFiltrar, tagName);
                driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            }
            click(driver, headerObjeto);
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(2));
            Thread.sleep(5000);
            clickJS(driver, btnInfo);
            clickJS(driver, btnEliminar);
            clickJS(driver, btnSi);
            driver.switchTo().window(tabs.get(1));
            sendKeys(driver, inputFiltrar, tagName);
            driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            visibiltyOf(driver, lblNotFound);
            driver.close();
            driver.switchTo().window(tabs.get(0));
            System.out.println("##############TAG BORRADO##############");
        } catch (Exception e) {
            System.out.println("##############NO SE PUDO BORRAR EL TAG " + tagName + " ERROR: " + e);
        }
    }

    public void deleteNote(String tituloNota) {
        System.out.println("##############BORRANDO NOTA##############");
        try {

            System.out.println(driver.getCurrentUrl());
            if (driver.getCurrentUrl().equals(url + "administrator/notas")) {
                System.out.println("##############SECCION NOTA OK##############");
                sendKeys(driver, inputFiltrar, tituloNota);
                driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            } else {
                System.out.println("##############SECCION NOTA##############");
                driver.get(url);
                sendKeys(driver, inputFiltrar, tituloNota);
                driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            }
            click(driver, headerObjeto);
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            Thread.sleep(5000);
            clickJS(driver, btnInfo);
            clickJS(driver, btnEliminar);
            clickJS(driver, btnSi2);
            driver.switchTo().window(tabs.get(0));
            // sendKeys(driver, inputFiltrar, tituloNota);
            // driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            visibiltyOf(driver, lblNotFound);
            System.out.println("##############NOTA BORRADA##############");
        } catch (Exception e) {
            System.out.println("##############NO SE PUDO BORRAR LA NOTA " + tituloNota + " ERROR: " + e);
        }
    }

    public void createCategory(String categoryName) {
        try {
            System.out.println("##############CREAR CATEGORIA##############");
            click(driver, btnCategorias);
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            click(driver, btnNuevo);
            tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(2));
            sendKeys(driver, inputNombreObjeto, categoryName);
            clickJS(driver, dropdownPortalCategoria);
            click(driver, optionPortalCategoria);
            clickJS(driver, btnGrabar);
            Thread.sleep(3000);
            visibiltyOf(driver, alertGuardado);
            driver.close();
            driver.switchTo().window(tabs.get(1));
            sendKeys(driver, inputFiltrar, categoryName);
            driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            setHeaderNote(categoryName);
            visibiltyOf(driver, headerObjeto);
            System.out.println("##############CREAR CATEGORIA OK##############");
        } catch (Exception e) {
            System.out.println("##############CREAR CATEGORIA ERROR: " + e);
        }
    }

    public void deleteCategory(String categoryName) throws InterruptedException {

        System.out.println("##############BORRANDO TAG##############");
        try {

            System.out.println(driver.getCurrentUrl());
            if (driver.getCurrentUrl().equals(url + "administrator/categorias")) {
                System.out.println("##############SECCION CATEGORIA OK##############");
                sendKeys(driver, inputFiltrar, categoryName);
                driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            } else {
                System.out.println("##############CLICK SECCION CATEGORIA##############");
                click(driver, btnCategorias);
                sendKeys(driver, inputFiltrar, categoryName);
                driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            }
            click(driver, headerObjeto);
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(2));
            Thread.sleep(5000);
            clickJS(driver, btnInfo);
            clickJS(driver, btnEliminar);
            clickJS(driver, btnSi);
            driver.switchTo().window(tabs.get(1));
            sendKeys(driver, inputFiltrar, categoryName);
            driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            visibiltyOf(driver, lblNotFound);
            System.out.println("##############TAG BORRADO##############");
        } catch (Exception e) {
            System.out.println("##############NO SE PUDO BORRAR EL TAG " + categoryName + " ERROR: " + e);
        }
    }
}
