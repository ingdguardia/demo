package com.example;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    String urlLogin = "https://telebajocero.backend.thinkindot.com/backend/";
    String urlAdminNotas = "https://telebajocero.backend.thinkindot.com/backend/administrator/notas";

    @Parameters({ "url" })
    @Test
    public void testApp2(String url) throws InterruptedException {
        System.out.println("TEST 1");
        System.out.println("URL EXTRAIDA DE JENKINS: " + url);
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);
        Assert.assertEquals(urlLogin, driver.getCurrentUrl());
        driver.findElement(By.id("usuario")).sendKeys("dguardia");
        driver.findElement(By.id("password")).sendKeys("dguardia");
        driver.findElement(By.xpath("//a[text()='Ingresar']")).click();
        Assert.assertEquals(url + "administrator/notas", driver.getCurrentUrl());
        System.out.println("TEST 1 PASSED");
        driver.quit();
    }

    @Test
    public void testApp() throws InterruptedException {
        System.out.println("TEST 2");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://telebajocero.backend.thinkindot.com/backend/");
        Thread.sleep(2000);
        System.out.println("TEST 2 PASSED");
        driver.quit();
    }

    @Test
    public void testApp2() throws InterruptedException {
        System.out.println("TEST 3");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://telebajocero.backend.thinkindot.com/backend/");
        Thread.sleep(2000);
        System.out.println("TEST 3 PASSED");
        driver.quit();
    }

}
