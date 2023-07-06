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
    By inputCuerpoNota = By.id("cuerpo");
    By btnGrabarNota = By.xpath("//div[@id='save-button']");
    By headerNota;
    By jsonTituloNota = By.xpath("//span[contains(text(), 'Automation')]");
    By jsonCopeteNota = By.xpath("//span[contains(text(), 'Auto-Copete')]");
    By jsonVolanteNota = By.xpath("//span[contains(text(), 'Test Auto')]");
    By jsonIdNota;
    By btnAgrupadores = By.xpath("//span[text()='Agrupadores']");
    By btnNuevoAgrupador = By.xpath("//a[@id='nuevo']");
    By inputNombreAgrupador = By.xpath("//input[@id='nombre']");
    By dropdownTipoAgrupador = By.xpath("//div[@id='content_idAgrupadorTipo']//div//input[@type='text']");
    By optionTipoAgrupadorTema = By.xpath("(//li//span[text()='Tema'])[1]");
    By inputFiltrarAgrupador = By.xpath("//input[@id='text']");

    public void click(WebDriver driver, By element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            driver.findElement(element).click();
            System.out.println("OBJETO CLICKEADO");
        } catch (Exception e) {
            System.out.println("NO SE PUDO CLICKEAR ELEMENTO: " + element.toString() + "ERROR: " + e);
        }

    }

    public void clickJS(WebDriver driver, By element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            WebElement wElement = driver.findElement(element);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click()", wElement);
            System.out.println("CLICK " + wElement.toString());
        } catch (Exception e) {
            System.out.println("NO SE PUDO CLICKEAR ELEMENTO: " + element.toString() + "ERROR: " + e);
        }
    }

    public void sendKeys(WebDriver driver, By element, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            driver.findElement(element).sendKeys(text);
            System.out.println("TEXTO ENVIADO a " + element.toString() + ": " + text);
        } catch (Exception e) {
            System.out.println("NO SE PUDO ENVIAR TEXTO: " + e);
        }

    }

    public void checkTitlePage(WebDriver driver, String title) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.titleContains(title));
            System.out.println("TITLE OK");
        } catch (Exception e) {
            System.out.println("NO SE PUDO VERIFICAR TITULO DE PAGINA: " + e);
        }

    }

    public void visibiltyOf(WebDriver driver, By element) {
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            System.out.println("OBJETO VISIBLE: " + element.toString());

        } catch (Exception e) {
            System.out.println("NO SE PUDO VISUALIZAR EL OBJETO " + element.toString() + ": " + e);
        }
    }

    public By setHeaderNote(String titulo) {
        tituloNota = titulo;
        return headerNota = By.xpath("//h2[text()='" + tituloNota + "']");
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
