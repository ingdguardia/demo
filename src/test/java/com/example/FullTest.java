package com.example;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FullTest {
    @Parameters({ "url", "testType" })
    @Test
    public void testApp(String url, String testType) throws InterruptedException {
        System.out.println("Tipo de test: " + testType);
        System.out.println("TEST 1");
        System.out.println("URL EXTRAIDA DE JENKINS: " + url);
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-position=1,1");
        options.addArguments("--window-size=800x600");
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);
        Assert.assertEquals(url, driver.getCurrentUrl());
        driver.findElement(By.id("usuario")).sendKeys("dguardia");
        driver.findElement(By.id("password")).sendKeys("dguardia");
        driver.findElement(By.xpath("//a[text()='Ingresar']")).click();
        Assert.assertEquals(url + "administrator/notas", driver.getCurrentUrl());
        System.out.println("TEST 1 PASSED");
        driver.quit();
    }

    @Parameters({ "url2" })
    @Test
    public void testApp2(String url) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-position=500,500");
        options.addArguments("--window-size=800x600");
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);
        System.out.println("TEST 2");
        Thread.sleep(2000);
        driver.quit();
    }

    @Parameters({ "url3" })
    @Test
    public void testApp3(String url) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=800x600");
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);
        System.out.println("TEST 3");
        Thread.sleep(3000);
        driver.quit();
    }
}
