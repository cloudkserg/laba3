package com.codebind;

import com.codebind.RsaEncryptor;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyPair;

public class App  {
    private JPanel panel1;
    private KeyPair keyPair;
    private JButton encryptButton;
    private JButton generateButton;
    private JButton decryptButton;
    private JButton signButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JButton verifyButton;


    public App() {
        //обработчик кнопки для шифровки текста
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Если пара ключей не сгенерирована выводим ошибку
                if (keyPair == null) {
                    //записываем в текстовое поле ошибку
                    textField1.setText("Not set keyPair");
                    return;
                }
                //получаем из текстового поля текст для расшифровки
                String decryptedText = textField2.getText();
                try {
                    //шифруем текст при помощи нашего класса
                    String encryptedText = RsaEncryptor.encrypt(decryptedText, keyPair.getPublic());
                    //Зашифрованный текст записываем в текстовое поле textField1
                    textField1.setText(encryptedText);
                } catch (Exception e) {
                    //В случае ошибки в textField1 пишем ошибку
                    textField1.setText(e.toString());
                }
            }
        });
        //обработчик кнопки для генерации ключей
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    //генерируем пару ключей
                    keyPair = RsaEncryptor.generateKeyPair();
                } catch (Exception e) {
                    // если есть ошибка - выводим ее
                    textField6.setText(e.toString());
                    return;
                }
                // в текстовые поля прописываем закрытый и открытый ключи
                textField6.setText(keyPair.getPrivate().toString());
                textField7.setText(keyPair.getPublic().toString());
            }
        });
        //обработчик кнопки для дешифрования текста
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Если ключевая пара не задана - пишем в textField4 ошибку
                if (keyPair == null) {
                    textField2.setText("Not set keyPair");
                }
                //получааем заширофванный текст из textField1
                String encryptedText = textField1.getText();
                try {
                    //получаем расшифрованный текст пользуясь функцией decrypt
                    String decryptedText = RsaEncryptor.decrypt(encryptedText, keyPair.getPrivate());
                    //прописываем в текстовое поле textField2 расшифрованный текст
                    textField2.setText(decryptedText);
                } catch (Exception e) {
                    //В случае ошибки в textField2 пишем ошибку
                    textField2.setText(e.toString());
                }
            }
        });

        //обработчик кнопки для подписи
        signButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Если ключевая пара не задана - пишем в textField4 ошибку
                if (keyPair == null) {
                    textField3.setText("Not set keyPair");
                }
                //получаем текст для подписи из текстового поля textField3
                String signedText = textField3.getText();
                try {
                    //получаем подпись используя метод sign и закрытый ключ
                    String sign = RsaEncryptor.sign(signedText, keyPair.getPrivate());
                    //прописываем в текстовое поле textField2 расшифрованный подпись
                    textField5.setText(sign);
                } catch (Exception e) {
                    //В случае ошибки в textField3 пишем ошибку
                    textField3.setText(e.toString());
                }
            }
        });

        //обработчик кнопки для проверки подписи
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Если ключевая пара не задана - пишем в textField4 ошибку
                if (keyPair == null) {
                    textField4.setText("Not set keyPair");
                }
                //получаем текст для проверки подписи из textField3
                String signedText = textField3.getText();
                //Получаем подпись из textField5
                String sign = textField5.getText();
                try {
                    //получаем результат используя функцию verify и публичный ключ
                    boolean result = RsaEncryptor.verify(signedText, sign, keyPair.getPublic());
                    if (result) {
                        textField4.setText("Success");
                    } else {
                        //В случае неправильной подписи в textField4 пишем Failure
                        textField4.setText("Failure");
                    }
                } catch (Exception e) {
                    //В случае ошибки в textField4 пишем ошибку
                    textField4.setText(e.toString());
                }
            }
        });

        //Здесь настраиваем окно для запуска
        JFrame frame = new JFrame();
        frame.setContentPane(panel1);
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(600, 600));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    //Здесь запускаем программу
    public static void main(String[] args) {
        new App();
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new FormLayout("fill:150px:noGrow,left:4dlu:noGrow,fill:d:grow", "center:d:grow,top:4dlu:noGrow,center:d:grow,top:4dlu:noGrow,center:d:grow,top:4dlu:noGrow,center:d:grow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:d:grow,top:4dlu:noGrow,center:d:grow,top:4dlu:noGrow,center:d:grow,top:4dlu:noGrow,center:d:grow,top:4dlu:noGrow,center:max(d;4px):noGrow"));
        textField1 = new JTextField();
        textField1.setText("");
        CellConstraints cc = new CellConstraints();
        panel1.add(textField1, cc.xy(3, 9, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FormLayout("fill:d:grow", "center:d:grow"));
        panel1.add(panel2, cc.xy(1, 9));
        panel2.setBorder(BorderFactory.createTitledBorder("Encrypted text"));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FormLayout("fill:d:grow", "center:d:grow"));
        panel1.add(panel3, cc.xy(1, 7));
        panel3.setBorder(BorderFactory.createTitledBorder("Decrypted text"));
        textField2 = new JTextField();
        panel1.add(textField2, cc.xy(3, 7, CellConstraints.FILL, CellConstraints.DEFAULT));
        signButton = new JButton();
        signButton.setText("Sign");
        panel1.add(signButton, cc.xy(1, 19));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new FormLayout("fill:d:grow", "center:d:grow"));
        panel1.add(panel4, cc.xy(1, 13));
        panel4.setBorder(BorderFactory.createTitledBorder("Signed text"));
        textField3 = new JTextField();
        panel1.add(textField3, cc.xy(3, 13, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new FormLayout("fill:d:grow", "center:d:grow"));
        panel1.add(panel5, cc.xy(1, 17));
        panel5.setBorder(BorderFactory.createTitledBorder("Verified text"));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new FormLayout("fill:d:grow", "center:d:grow"));
        panel1.add(panel6, cc.xy(1, 15));
        panel6.setBorder(BorderFactory.createTitledBorder("Sign"));
        textField5 = new JTextField();
        panel1.add(textField5, cc.xy(3, 15, CellConstraints.FILL, CellConstraints.DEFAULT));
        encryptButton = new JButton();
        encryptButton.setText("Encrypt");
        panel1.add(encryptButton, cc.xy(1, 11));
        decryptButton = new JButton();
        decryptButton.setText("Decrypt");
        panel1.add(decryptButton, cc.xy(3, 11));
        textField4 = new JTextField();
        panel1.add(textField4, cc.xy(3, 17, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new FormLayout("fill:d:grow", "center:d:grow"));
        panel1.add(panel7, cc.xy(1, 1));
        panel7.setBorder(BorderFactory.createTitledBorder("Private Key"));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new FormLayout("fill:d:grow", "center:d:grow"));
        panel1.add(panel8, cc.xy(1, 3));
        panel8.setBorder(BorderFactory.createTitledBorder("Public Key"));
        generateButton = new JButton();
        generateButton.setText("Generate");
        panel1.add(generateButton, cc.xy(1, 5));
        textField6 = new JTextField();
        panel1.add(textField6, cc.xy(3, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
        textField7 = new JTextField();
        panel1.add(textField7, cc.xy(3, 3, CellConstraints.FILL, CellConstraints.DEFAULT));
        verifyButton = new JButton();
        verifyButton.setText("Verify");
        panel1.add(verifyButton, cc.xy(3, 19));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
