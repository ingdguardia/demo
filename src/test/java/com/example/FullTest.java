package com.example;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
//import org.openqa.selenium.JavascriptExecutor;
import java.util.logging.FileHandler;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FullTest extends Elements {
    String grupos;
    String url;
    String urlTestApi;
    WebDriver driver;
    String texto;
    String urlApi;
    String idImagen;

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
    public void test3() throws InterruptedException, IOException {
        login();
        createTag("TD AUTOMATION Tag", "Tema");
        createCategory("TD AUTOMATION Category");
        createAttach("Imagen", System.getProperty("user.dir") +
                "/pruebas-evaluacion-1.png", "TD AUTOMATION Imagen");
        createNote("TD AUTOMATION Nota", "Volanta auto", "Copete auto", "TD AUTOMATION Tag", "Tema",
                "TD AUTOMATION Category", "TD AUTOMATION Imagen");
        deleteNote("TD AUTOMATION Nota");
        deleteTag("TD AUTOMATION Tag");
        deleteCategory("TD AUTOMATION Category");
        deleteAttach("TD AUTOMATION Imagen");
    }

    @AfterTest
    public void quit() {
        driver.quit();
    }

    public void screenshot(String element) throws IOException {

        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File("./Screenshots/" + element + ".png"));

    }

    public void getUrl(String check) {
        if (check.equals("america")) {
            grupos = check;
            url = "https://diariolasamericas-diariolasamericas.backend.staging.thinkindot.com/backend/";
            urlTestApi = "https://lasamericas-diariolasamericas-upgrade-81.backend.staging.thinkindot.com/2.0/public/articles/";
        } else if (check.equals("telebajocero")) {
            grupos = check;
            url = "https://telebajocero-pallareslussich-upgrade-81.backend.staging.thinkindot.com/backend/";
            urlTestApi = "https://telebajocero-pallareslussich-upgrade-81.backend.staging.thinkindot.com/2.0/public/articles/";
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
            driver.close();
            driver.switchTo().window(tabs.get(0));
            System.out.println("##############CREAR AGRUPADOR OK##############");
        } catch (Exception e) {
            System.out.println("##############CREAR AGRUPADOR ERROR: " + e);
        }
    }

    public void createNote(String titulo, String volanta, String copete, String tag, String tipoTag, String category,
            String archivo) {
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
            sendKeys(driver, inputArchivosNota, archivo);
            checkboxAdjunto = By.xpath("//*[@id='FileList_" + idImagen + "_item']/figure/div/label/input");
            Thread.sleep(2000);
            clickJS(driver, checkboxAdjunto);
            clickJS(driver, btnRelacionarNota);
            Thread.sleep(3000);
            optionAgrupadores = By.xpath("//li//span[text()='" + tipoTag + " » " + tag + "']");
            clickJS(driver, optionAgrupadores);

            // chipCategoriasTag = By.xpath("//div[@class='chip__body']/span[text()='" + tag
            // + "']");
            // visibiltyOf(driver, chipCategoriasTag);
            sendKeys(driver, inputCategoriasNota, category);
            optionCategorias = By.xpath("//span[text()='" + category + "']");
            clickJS(driver, optionCategorias);
            // chipCategoriasTag = By.xpath("//div[@class='chip__body']/span[text()='" +
            // category + "']");
            // visibiltyOf(driver, chipCategoriasTag);

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
            driver.get(url);
            login();
            // tabs = new ArrayList<String>(driver.getWindowHandles());
            // for (int i = 1; i <= tabs.size(); i++) {
            // driver.close();
            // }
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

    public void deleteTag(String tagName) throws IOException {
        System.out.println(tagName);
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
                ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
                tabs = new ArrayList<String>(driver.getWindowHandles());
                driver.switchTo().window(tabs.get(1));
                sendKeys(driver, inputFiltrar, tagName);
                driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            }
            headerObjeto = setHeaderNote(tagName);
            click(driver, headerObjeto);
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(2));
            // driver.switchTo().window("Categorias :: Thinkindot [4.7] :: ");
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
            screenshot(tagName);
        } catch (Exception e) {
            System.out.println("##############NO SE PUDO BORRAR EL TAG " + tagName + " ERROR: " + e);
            screenshot(tagName);
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
            headerObjeto = setHeaderNote(tituloNota);
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

    // TODO metodo checkUrl
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
            driver.close();
            driver.switchTo().window(tabs.get(0));
            System.out.println("##############CREAR CATEGORIA OK##############");
        } catch (Exception e) {
            System.out.println("##############CREAR CATEGORIA ERROR: " + e);
        }
    }

    public void deleteCategory(String categoryName) throws InterruptedException, IOException {
        try {
            System.out.println("##############BORRANDO CATEGORIA##############");
            System.out.println(driver.getCurrentUrl());
            Thread.sleep(5000);
            if (driver.getCurrentUrl().equals(url + "administrator/categorias")) {
                System.out.println(url + "administrator/categorias");
                System.out.println("##############SECCION CATEGORIAS OK##############");
                sendKeys(driver, inputFiltrar, categoryName);
                driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            } else {
                System.out.println("##############CLICK SECCION CATEGORIAS##############");
                click(driver, btnCategorias);
                ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
                tabs = new ArrayList<String>(driver.getWindowHandles());
                driver.switchTo().window(tabs.get(1));
                sendKeys(driver, inputFiltrar, categoryName);
                driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            }
            setHeaderNote(categoryName);
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
            driver.close();
            driver.switchTo().window(tabs.get(0));
            System.out.println("##############CATEGORIA BORRADA##############");
        } catch (Exception e) {
            System.out.println("##############NO SE PUDO BORRAR LA CATEGORIA " + categoryName + " ERROR: " + e);
            screenshot("FAILED " + categoryName);
        }
    }

    public void createAttach(String tipo, String link, String nombreAdjunto) {
        try {
            System.out.println("##############CREAR ADJUNTO##############");
            click(driver, btnArchivos);
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            // sendKeys(driver, inputFiltrar, nombreAdjunto);
            // headerImagen = By.xpath("//div[text()='" + nombreAdjunto + "']");
            // while (headerImagen != null) {
            // clickJS(driver, headerImagen);
            // tabs = new ArrayList<String>(driver.getWindowHandles());
            // driver.switchTo().window(tabs.get(2));
            // Thread.sleep(5000);
            // clickJS(driver, btnInfo);
            // clickJS(driver, btnEliminar);
            // clickJS(driver, btnSi2);
            // driver.switchTo().window(tabs.get(1));
            // sendKeys(driver, inputFiltrar, nombreAdjunto);
            // }
            // System.out.println("exit while");
            click(driver, btnNuevo);
            tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(2));
            // Thread.sleep(3000);
            // click(driver, btnFile);
            Thread.sleep(3000);
            // click(driver, btnFile);
            System.out.println("HOME: " + System.getProperty("user.dir"));
            driver.findElement(btnFile).sendKeys(link);
            clickJS(driver, optionImgGrande);
            Thread.sleep(2000);
            sendKeys(driver, tituloAdjunto, nombreAdjunto);
            optionTipoAdjunto = By.xpath("(//li//span[text()='" + tipo + "'])[1]");
            clickJS(driver, dropdownTipoAdjunto);
            clickJS(driver, optionTipoAdjunto);
            clickJS(driver, btnGrabar);
            Thread.sleep(3000);
            driver.close();
            driver.switchTo().window(tabs.get(1));
            headerImagen = By.xpath("//div[(text='" + nombreAdjunto + "')]");
            sendKeys(driver, inputFiltrar, nombreAdjunto);
            driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            elementIsClickableW(driver, headerImagen); // TODO FIX
            WebElement wElement = driver.findElement(articleAdjunto);
            idImagen = wElement.getAttribute("cms:value");
            System.out.println("VALOR: " + idImagen);
            driver.close();
            driver.switchTo().window(tabs.get(0));
            System.out.println("##############CREAR ADJUNTO OK##############");
        } catch (Exception e) {
            System.out.println("##############NO SE PUDO CREAR EL ADJUNTO: " + tipo + " ERROR: " + e);

        }

    }

    public void deleteAttach(String attachName) throws InterruptedException {
        System.out.println(idImagen);
        System.out.println("##############BORRANDO ADJUNTO##############");
        try {

            System.out.println(driver.getCurrentUrl());
            if (driver.getCurrentUrl().equals(url + "administrator/adjuntos")) {
                System.out.println(url + "administrator/adjuntos");
                System.out.println("##############SECCION ADJUNTO OK##############");
                sendKeys(driver, inputFiltrar, attachName);
                driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            } else {
                System.out.println("##############CLICK SECCION ARCHIVOS##############");
                click(driver, btnArchivos);
                ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
                tabs = new ArrayList<String>(driver.getWindowHandles());
                System.out.println(tabs.iterator());
                driver.switchTo().window(tabs.get(1));
                sendKeys(driver, inputFiltrar, attachName);
                driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            }
            headerImagen = By.xpath("//div[text()='" + attachName + "']");
            clickJS(driver, headerImagen);
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(2));
            Thread.sleep(5000);
            clickJS(driver, btnInfo);
            clickJS(driver, btnEliminar);
            clickJS(driver, btnSi2);
            driver.switchTo().window(tabs.get(1));
            sendKeys(driver, inputFiltrar, attachName);
            driver.findElement(inputFiltrar).sendKeys(Keys.ENTER);
            visibiltyOf(driver, lblNotFound);
            driver.close();
            driver.switchTo().window(tabs.get(0));
            System.out.println("##############ADJUNTO BORRADO##############");
        } catch (Exception e) {
            System.out.println("##############NO SE PUDO BORRAR EL ADJUNTO " + attachName + " ERROR: " + e);
        }
    }
}
