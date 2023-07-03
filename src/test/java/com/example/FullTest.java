package com.example;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
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
    String url;
    WebDriver driver;

    @Parameters({ "grupo", "testType" })
    @BeforeTest
    public void initializeVariables(String grupo, String testType) {
        System.out.println("BEFORE TEST");
        System.out.println("URL BASE1: " + url);
        System.out.println("GRUPO: " + grupo);
        System.out.println("GRUPOS: " + grupos);
        getUrl(grupo);
        System.out.println("URL BASE2: " + url);
        System.out.println("GRUPO2: " + grupo);
        System.out.println("GRUPOS2: " + grupos);
        System.out.println("Tipo de test: " + testType);

        driver = initializeChrome(driver);
        driver.get(url);

    }

    // @Parameters({ "grupo", "testType" })
    @Test
    public void testApp() throws InterruptedException {
        System.out.println("TEST 1");
        try {
            driver.get(url);
            Assert.assertEquals(url, driver.getCurrentUrl());
            Thread.sleep(2000);
            driver.findElement(By.id("usuario")).sendKeys("staging");
            driver.findElement(By.id("password")).sendKeys("P4ssSt4g1ng");
            driver.findElement(By.xpath("//a[text()='Ingresar']")).click();
            Assert.assertEquals(url + "administrator/notas", driver.getCurrentUrl());
            System.out.println("TEST 1 PASSED");
            Thread.sleep(2000);
            // driver2.quit();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }

    }

    // @Parameters({ "url2" })
    @Test
    public void testApp2() throws InterruptedException {
        driver.get(url);
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

    @AfterTest
    public void quit() {
        driver.quit();
    }

    public void getUrl(String check) {
        if (check.equals("america")) {
            grupos = check;
            url = "https://diariolasamericas-diariolasamericas.backend.staging.thinkindot.com/backend/";
        }
    }

    public WebDriver initializeChrome(WebDriver explorer) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-position=1,1");
        options.addArguments("--window-size=800x600");
        explorer = new ChromeDriver(options);
        return explorer;
    }
}
