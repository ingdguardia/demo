package com.example;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FullTest {
    @Parameters({ "url", "testType" })
    @Test
    public void testApp(String url, String testType) throws InterruptedException {
        System.out.println("Tipo de test: " + testType);
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);
        System.out.println("TEST 1 PROBANDO");
        Thread.sleep(2000);
        driver.quit();
    }

    // @Test
    public void testApp2() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://telebajocero.backend.thinkindot.com/backend/");
        System.out.println("TEST 2");
        Thread.sleep(2000);
        driver.quit();
    }
}
