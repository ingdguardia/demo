package com.example;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class Elements {
    String tituloNota;
    String idNota;
    int timeOut = 30;
    // ELEMENTOS CMS
    By inputLoginUser = By.id("usuario");
    By inputLoginPass = By.id("password");
    By btnLogin = By.xpath("//a[text()='Ingresar']");
    By btnNuevo = By.id("nuevo");
    By inputVolantaNota = By.id("volanta");
    By inputTituloNota = By.id("titulo");
    By inputCopeteNota = By.id("copete");
    By inputCuerpoNota = By.xpath("//*[@id='mce_0']/p");
    By btnGrabar = By.xpath("//div[@id='save-button']");
    By headerObjeto;
    By jsonTituloNota = By.xpath("//span[contains(text(), 'Automation')]");
    By jsonCopeteNota = By.xpath("//span[contains(text(), 'Auto-Copete')]");
    By jsonVolantaNota = By.xpath("//span[contains(text(), 'Test Auto')]");
    By jsonIdNota;
    By btnAgrupadores = By.xpath("//span[text()='Agrupadores']");
    By inputNombreObjeto = By.xpath("//input[@id='nombre']");
    By dropdownTipoAgrupador = By.xpath("//div[@id='content_idAgrupadorTipo']//div//input[@type='text']");
    By dropdownPortalCategoria = By.xpath("//div[@id='content_idCMSPortal']//div//input[@type='text']");
    By optionTipoAgrupadorTema = By.xpath("(//li//span[text()='Tema'])[1]");
    By optionPortalCategoria = By.xpath("(//li//span[text()='Todos'])[1]");
    By inputFiltrar = By.xpath("//input[@id='text']");
    By btnInfo = By.xpath("//div//span[@class='icon info-publish']");
    By btnEliminar = By.xpath("//a[text()='Eliminar']");
    By btnSi = By.xpath("//a[text()='Sí']");
    By btnSi2 = By.xpath("//a[text()='Si']");
    By lblNotFound = By.xpath("//p[text()='No se han encontrado resultados']");
    By alertGuardado = By.xpath("//*[@id='toast-container']/div[text()='El contenido ha sido guardado.']");
    By btnCategorias = By.xpath("//span[text()='Categorias']");
    By inputCategoriasNota = By.xpath("//input[@id='arrCategorias_input']");
    By optionCategorias = By.xpath("//span[text()='test']");
    By chipCategoriasTag = By.xpath("//div[@class='chip__body']/span[text()='test']");
    By inputAgrupadoresNota = By.xpath("//input[@id='arrAgrupadores_input']");
    By optionAgrupadores = By.xpath("//li//span[text()='Tema » Test']");
    By btnArchivos = By.xpath("//span[text()='Archivos']");
    By btnFile = By.id("uploadFile");
    By dropdownTipoAdjunto = By.xpath("//div[@id='content_idAdjuntoTipo']//div//input[@type='text']");
    By optionTipoAdjunto = By.xpath("(//li//span[text()='Imagen'])[1]");
    By optionImgGrande = By.xpath("(//a[contains(text(), 'Grande')])[1]");
    By tituloAdjunto = By.id("descripcion");
    By headerImagen = By.xpath("//div[contains(text(), 'TD Automation Imagen')]");
    By inputArchivosNota = By.id("FileList_input");
    By optionArchivosNota = By.xpath("//span[contains(text(), 'Test Auto')]");
    By articleAdjunto = By.xpath("//article[starts-with(@id,'Result')]");
    By checkboxAdjunto = By.xpath("//*[@id='FileList_7162368_item']/figure/div");
    By btnRelacionarNota = By.xpath("//*[@id='FileList_result_items']/div/div[2]/div[1]/a");

    public void click(WebDriver driver, By element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            driver.findElement(element).click();
            System.out.println("OBJETO " + element.toString() + " CLICKEADO");
        } catch (Exception e) {
            System.out.println("NO SE PUDO CLICKEAR ELEMENTO: " + element.toString() + "ERROR: " + e);
        }

    }

    public void clickJS(WebDriver driver, By element) {
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(element));
            WebElement wElement = driver.findElement(element);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click()", wElement);
            System.out.println("OBJETO " + element.toString() + " CLICKEADO CON JS");
        } catch (Exception e) {
            System.out.println("NO SE PUDO CLICKEAR ELEMENTO CON JS: " + element.toString() + "ERROR: " + e);
        }
    }

    public void sendKeys(WebDriver driver, By element, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            driver.findElement(element).clear();
            driver.findElement(element).sendKeys(text);
            System.out.println("TEXTO ENVIADO a " + element.toString() + ": " + text);
        } catch (Exception e) {
            System.out.println("NO SE PUDO ENVIAR TEXTO: " + e);
        }

    }

    public void checkTitlePage(WebDriver driver, String title) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.titleContains(title));
            System.out.println("TITLE OK");
        } catch (Exception e) {
            System.out.println("NO SE PUDO VERIFICAR TITULO DE PAGINA: " + e);
        }

    }

    public void visibiltyOf(WebDriver driver, By element) {
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            System.out.println("OBJETO VISIBLE: " + element.toString());

        } catch (Exception e) {
            System.out.println("NO SE PUDO VISUALIZAR EL OBJETO " + element.toString() + ": " + e);
        }
    }

    public By setHeaderNote(String titulo) {
        tituloNota = titulo;
        return headerObjeto = By.xpath("//h2[text()='" + tituloNota + "']");
    }

    public By setIdNote(String id) {
        idNota = id;
        return jsonIdNota = By.xpath("(//span[contains(text(), '" + idNota + "')])[2]");
    }

    public String getNoteIdApi(String url) {
        String ruta1 = url;
        // Separo la ruta en partes delimitadas por el caracter /
        String[] parts = ruta1.split("/");
        // Obtengo lo que quiero mostrar en el textview
        String id = parts[parts.length - 1];
        return id;
    }
}
