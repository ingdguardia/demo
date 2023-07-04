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
    By btnGrabarNota = By.id("save-button");
    By headerNota = By.xpath("//h2[text()='" + tituloNota + "']");

    public void click(WebDriver driver, By element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            driver.findElement(element).click();
        } catch (Exception e) {
            System.out.println("CANT CLICK ELEMENT: " + e);
        }

    }

    public void sendKeys(WebDriver driver, By element, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        driver.findElement(element).sendKeys(text);

    }

    public void checkTitlePage(WebDriver driver, String title) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.titleContains(title));
        System.out.println("TITLE OK");

    }

    public void visibiltyOf(WebDriver driver, By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public void setHeaderNote(String titulo) {
        tituloNota = titulo;
    }
}
