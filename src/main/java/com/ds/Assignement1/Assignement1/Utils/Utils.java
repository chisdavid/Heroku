package com.ds.Assignement1.Assignement1.Utils;

import com.ds.Assignement1.Assignement1.Dto.RPCResponse;
import com.ds.Assignement1.Assignement1.Model.Device;
import com.ds.Assignement1.Assignement1.Model.SensorData;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.ds.Assignement1.Assignement1.Utils.Constants.APP_EMAIL;
import static com.ds.Assignement1.Assignement1.Utils.Constants.APP_PASSWORD;

@NoArgsConstructor
public class Utils {

    public static ResponseEntity genericResponse(Object obj) {
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }

    public static String generatePassword(int passwordLength) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSabcdefghijklmnopqrstuvwxyzTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < passwordLength) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public static String sendEmail(String email, String message) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
            }
        });
        System.out.println("dasasd");
        String [] words = message.split(" ");
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));


            mimeMessage.setSubject("Credentials ");


            String sb = "<head>" +
                    "<style type=\"text/css\">" +
                    "  .red { color: #f00; }" +
                    "</style>" +
                    "</head>" +
                    "<h1 class=\"red\">" + mimeMessage.getSubject() + "</h1>" +
                    "<img src=\"./Logo.svg\" />"+
                    "<p>" +
                    "Your username is <strong>"+words[0]+"</strong> , " +
                    "and your password is <strong> "+words[1]+" </strong>.</p>";
            mimeMessage.setContent(sb, "text/html; charset=utf-8");
            mimeMessage.saveChanges();
            //send message
            Transport.send(mimeMessage);




            System.out.println("message sent successfully");
            return "";
        } catch (MessagingException e) {
            return e.getMessage();
        }
    }

    public static String getKey(String date) {
        String first = date.split("T")[0];
        String[] second = first.split("-");
        return " (" + second[2] + "/" + second[1] + ")";
    }

    public static Double getBaselineByHour (Device device, LocalDateTime localDateTime, int hour)
    {
        AtomicReference<Double> value = new AtomicReference<>(0D);

        device.getSensor()
                .getDataList()
                .stream()
                .filter(i->i.getDate().getDayOfMonth() == localDateTime.getDayOfMonth())
                .filter(j->j.getDate().getHour() == hour)
                .map(j -> j.getValue())
                .forEach(k -> {
                    value.set(value.get() + k);
                });
        return value.get();
    }

    public static Double getSensorDataAverageByDate(Device device, LocalDateTime localDateTime) {
        AtomicReference<Double> value = new AtomicReference<>(0D);

        device.getSensor()
                .getDataList()
                .stream()
                .filter(time -> time.getDate().getDayOfMonth() == localDateTime.getDayOfMonth())
                .map(j -> j.getValue())
                .forEach(k -> {
                    value.set(value.get() + k);
                });

        return value.get();
    }

    public static ArrayList<RPCResponse> getSensorDataByDateHourly(Device device, LocalDateTime localDateTime) {
        ArrayList<RPCResponse> responses = new ArrayList<>();
        List<SensorData> sensorDataList = device.getSensor()
                .getDataList()
                .stream()
                .filter(
                        time -> time.getDate().getDayOfMonth() == localDateTime.getDayOfMonth()
                ).collect(Collectors.toList());

        if (sensorDataList.size() == 0) {
            return getFreeListValues();
        } else {
            for (int hour = 0; hour < 24; hour++) {
                int finalHour = hour;
                AtomicReference<Double> value = new AtomicReference<>(0D);
                sensorDataList.forEach(i -> {
                    LocalDateTime dateTime = i.getDate();
                    if (dateTime.getHour() == finalHour) {
                        value.updateAndGet(v -> v + i.getValue());
                    }
                });
                responses.add(new RPCResponse(hour + ":00", value.get()));
            }
        }
        return responses;
    }

    public static ArrayList<RPCResponse> getFreeListValues() {
        ArrayList<RPCResponse> responses = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            responses.add(new RPCResponse(i + ":00", 0D));
        }
        return responses;
    }


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
