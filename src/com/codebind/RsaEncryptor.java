package com.codebind;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.*;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

//Класс для работы с библиотекой и шфирования/дешифровки
public class RsaEncryptor {

    //функция для генерации  пары ключей
    public static KeyPair generateKeyPair() throws Exception {
        //Получаем объект генератора по алгоритму RSA
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        //задаем ключи с длиной 2 байта
        generator.initialize(2048, new SecureRandom());
        //генерируем пару
        return generator.generateKeyPair();
    }

    //функция для шифровки текста - принимает текст и публичный ключ
    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        //Создаем объект для работы с шфированием по RSA и инициируем его с публичным ключом
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        //дешифруем текст
        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));
        //возвращаем его в виде строки
        return Base64.getEncoder().encodeToString(cipherText);
    }

    //функция для дешифровки текста
    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        //преобразуем текст для дешфировки в набор байтом
        byte[] bytes = Base64.getDecoder().decode(cipherText);
        //Создаем объект для работы с шфированием по RSA и инициируем его с закрытым ключом
        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        //производим дешфировку текста
        return new String(decriptCipher.doFinal(bytes), UTF_8);
    }

    //функция для подписи текста
    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        //Создаем сигнатуру в формате SHA256+RSSA
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        //инциируем ее закрытым ключом
        privateSignature.initSign(privateKey);
        //передаем в нее текст для подписи
        privateSignature.update(plainText.getBytes(UTF_8));
        //получаем подпись в виде массива байтов
        byte[] signature = privateSignature.sign();
        // переводим подпись в строку
        return Base64.getEncoder().encodeToString(signature);
    }

    //функция для проверки подписи
    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        //Создаем сигнатуру в формате SHA256+RSSA
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        //инциируем ее открытым ключом
        publicSignature.initVerify(publicKey);
        //передаем в нее текст для подписи
        publicSignature.update(plainText.getBytes(UTF_8));

        //расшифровываем подпись
        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        //если она совпала - возвращаем True, иначе False
        return publicSignature.verify(signatureBytes);
    }

}
