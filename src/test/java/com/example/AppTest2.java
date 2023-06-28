package com.example;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest2 {
    String urlLogin = "https://telebajocero.backend.thinkindot.com/backend/";
    String urlAdminNotas = "https://telebajocero.backend.thinkindot.com/backend/administrator/notas";

    @Test
    public void testApp2() throws InterruptedException {
        System.out.println("TEST 2");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://telebajocero.backend.thinkindot.com/backend/");
        Assert.assertEquals(urlLogin, driver.getCurrentUrl());
        driver.findElement(By.id("usuario")).sendKeys("dguardia");
        driver.findElement(By.id("password")).sendKeys("dguardia");
        driver.findElement(By.xpath("//a[text()='Ingresar']")).click();
        Assert.assertEquals(urlAdminNotas, driver.getCurrentUrl());
        System.out.println("TEST 2 PASSED");
        driver.quit();
    }

}
