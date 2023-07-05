package com.example;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Elements {
    String tituloNota;
    int timeOut = 30;
    // ELEMENTOS CMS
    By txtLoginUser = By.id("usuario");
    By txtLoginPass = By.id("password");
    By btnLogin = By.xpath("//a[text()='Ingresar']");
    By btnNuevo = By.id("nuevo");
    By txtVolantaNota = By.id("volanta");
    By txtTituloNota = By.id("titulo");
    By txtCopeteNota = By.id("copete");
    By txtCuerpoNota = By.id("cuerpo");
    By btnGrabarNota = By.xpath("//div[@id='save-button']");
    By headerNota;

    public void click(WebDriver driver, By element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            driver.findElement(element).click();
            System.out.println("OBJETO CLICKEADO");
        } catch (Exception e) {
            System.out.println("NO SE PUDO CLICKEAR ELEMENTO: " + e);
        }

    }

    public void sendKeys(WebDriver driver, By element, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            driver.findElement(element).sendKeys(text);
            System.out.println("TEXTO ENVIADO: " + text);
        } catch (Exception e) {
            System.out.println("NO SE PUDO ENVIAR TEXTO: " + e);
        }

    }

    public void checkTitlePage(WebDriver driver, String title) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.titleContains(title));
        System.out.println("TITLE OK");

    }

    public void visibiltyOf(WebDriver driver, By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        System.out.println("Se ve el objeto: " + element.toString());
    }

    public By setHeaderNote(String titulo) {
        tituloNota = titulo;
        return headerNota = By.xpath("//h2[text()='" + tituloNota + "']");
    }

    public String getNoteIdApi(String url) {
        String id = "";
        String ruta1 = url;
        // Separo la ruta en partes delimitadas por el caracter /
        String[] parts = ruta1.split("/");
        // Obtengo lo que quiero mostrar en el textview
        String ultima = parts[parts.length];
        System.out.println("ultima: " + ultima);
        return id;
    }
}
