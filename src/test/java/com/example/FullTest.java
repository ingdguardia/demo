package com.example;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FullTest {
    String grupos;
    String urlBase;
    WebDriver driver2;

    @Parameters({ "grupo", "testType" })
    @BeforeTest
    public void initializeVariables(String grupo, String testType) {
        System.out.println("BEFORE TEST");
        System.out.println("URL BASE1: " + urlBase);
        System.out.println("GRUPO: " + grupo);
        System.out.println("GRUPOS: " + grupos);
        urlBase = urlGrupo(grupo);
        System.out.println("URL BASE2: " + urlBase);
        System.out.println("GRUPO2: " + grupo);
        System.out.println("GRUPOS2: " + grupos);
        System.out.println("Tipo de test: " + testType);

        driver2 = initializeChrome(driver2);
        driver2.get(urlBase);

    }

    // @Parameters({ "grupo", "testType" })
    @Test
    public void testApp() throws InterruptedException {
        System.out.println("TEST 1");

        driver2.get(urlBase);
        Assert.assertEquals(urlBase, driver2.getCurrentUrl());
        driver2.findElement(By.id("usuario")).sendKeys("staging");
        driver2.findElement(By.id("password")).sendKeys("P4ssSt4g1ng");
        driver2.findElement(By.xpath("//a[text()='Ingresar']")).click();
        Assert.assertEquals(urlBase + "administrator/notas", driver2.getCurrentUrl());
        System.out.println("TEST 1 PASSED");
        Thread.sleep(2000);
        driver2.quit();
    }

    // @Parameters({ "url2" })
    // @Test
    public void testApp2() throws InterruptedException {
        System.out.println("GRUPOS: " + grupos);
        System.out.println("URL BASE: " + urlBase);
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-position=500,500");
        options.addArguments("--window-size=800x600");
        WebDriver driver = new ChromeDriver(options);
        driver.get(urlBase);
        System.out.println("TEST 2");
        Thread.sleep(5000);
        driver.quit();
    }

    // @Parameters({ "url3" })
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

    public String urlGrupo(String check) {
        if (check.equals("america")) {
            grupos = check;
            urlBase = "https://diariolasamericas-diariolasamericas.backend.staging.thinkindot.com/backend/";
        }
        return urlBase;
    }

    public WebDriver initializeChrome(WebDriver driver) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-position=1,1");
        options.addArguments("--window-size=800x600");
        driver = new ChromeDriver(options);
        return driver;
    }
}
