package com.example.projecti_trello_app_backend.MainTest;

import com.example.projecti_trello_app_backend.entities.user.User;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.sql.Timestamp;
import java.time.Duration;

public class MainTest {

    public static void main(String[] args){
        try{
            WebDriverManager.edgedriver().setup();
            WebDriver driver = new EdgeDriver();
            driver.get("https://www.google.com");
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
