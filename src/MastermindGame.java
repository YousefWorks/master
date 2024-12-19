import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MastermindGame {
    private static final String[] COLORS = {"Red", "Green", "Blue", "Yellow", "Purple", "Orange"}; // قائمة الألوان المتاحة
    private static final int CODE_LENGTH = 4; // طول الكود السري
    private static final int MAX_ATTEMPTS = 10; // الحد الأقصى لعدد المحاولات

    private String[] secretCode; // الكود السري الذي يتم توليده
    private int attempts; // عدد المحاولات المستخدمة

    private JFrame frame; // الإطار الرئيسي للواجهة
    private JLabel feedbackLabel; // لعرض نتائج التخمين
    private JButton submitButton; // زر إرسال التخمين
    private JComboBox<String>[] colorSelectors; // مربعات اختيار الألوان

    public MastermindGame() { // المُنشئ لتهيئة اللعبة
        secretCode = generateSecretCode(); // توليد الكود السري
        attempts = 0; // تعيين عدد المحاولات إلى صفر
        createAndShowGUI(); // إنشاء الواجهة وعرضها
    }

    private String[] generateSecretCode() { // دالة لتوليد الكود السري
        Random random = new Random(); // كائن لتوليد أرقام عشوائية
        String[] code = new String[CODE_LENGTH]; // مصفوفة لتخزين الكود السري
        for (int i = 0; i < CODE_LENGTH; i++) { // حلقة لتعيين كل لون في الكود
            code[i] = COLORS[random.nextInt(COLORS.length)]; // اختيار لون عشوائي من القائمة
        }
        return code; // إرجاع الكود السري
    }

    private void createAndShowGUI() { // دالة لإنشاء واجهة المستخدم
        frame = new JFrame("Mastermind Game"); // إنشاء إطار اللعبة
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // تعيين الإغلاق عند غلق الإطار
        frame.setSize(900, 700); // تعيين حجم الإطار
        frame.setLayout(new BorderLayout(10, 10)); // تعيين تخطيط الإطار
        frame.getContentPane().setBackground(new Color(40, 42, 54)); // تعيين لون الخلفية

        // Title
        JLabel titleLabel = new JLabel("Mastermind", JLabel.CENTER); // إنشاء عنوان اللعبة
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // تعيين الخط والحجم
        titleLabel.setForeground(Color.WHITE); // تعيين لون النص
        frame.add(titleLabel, BorderLayout.NORTH); // إضافة العنوان إلى الأعلى

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout()); // لوحة رئيسية بتخطيط GridBag
        mainPanel.setBackground(new Color(40, 42, 54)); // تعيين لون الخلفية
        GridBagConstraints gbc = new GridBagConstraints(); // قيود التخطيط
        gbc.insets = new Insets(10, 10, 10, 10); // الهوامش بين المكونات

        // Instructions
        JLabel instructions = new JLabel("Choose 4 colors to guess. You have 10 attempts!"); // تعليمات اللعبة
        instructions.setFont(new Font("Arial", Font.PLAIN, 18)); // تعيين الخط والحجم
        instructions.setForeground(Color.WHITE); // تعيين لون النص
        gbc.gridx = 0; // تعيين موقع العنصر على المحور X
        gbc.gridy = 0; // تعيين موقع العنصر على المحور Y
        gbc.gridwidth = 2; // عرض العنصر على عمودين
        mainPanel.add(instructions, gbc); // إضافة التعليمات إلى اللوحة الرئيسية

        // Color Selectors
        JPanel colorPanel = new JPanel(new GridLayout(1, CODE_LENGTH, 10, 10)); // لوحة لاختيار الألوان
        colorPanel.setBackground(new Color(40, 42, 54)); // تعيين لون الخلفية
        colorSelectors = new JComboBox[CODE_LENGTH]; // مصفوفة لمربعات الاختيار
        for (int i = 0; i < CODE_LENGTH; i++) { // حلقة لإنشاء مربعات الاختيار
            colorSelectors[i] = new JComboBox<>(COLORS); // إنشاء مربع اختيار مع الألوان
            colorSelectors[i].setFont(new Font("Arial", Font.PLAIN, 14)); // تعيين الخط
            colorSelectors[i].setBackground(Color.WHITE); // تعيين لون الخلفية
            colorSelectors[i].setForeground(Color.BLACK); // تعيين لون النص
            colorPanel.add(colorSelectors[i]); // إضافة مربع الاختيار إلى اللوحة
        }
        gbc.gridy = 1; // تغيير موقع العنصر على المحور Y
        mainPanel.add(colorPanel, gbc); // إضافة اللوحة إلى اللوحة الرئيسية

        // Submit Button
        submitButton = new JButton("Submit Guess"); // زر إرسال التخمين
        submitButton.setFont(new Font("Arial", Font.BOLD, 18)); // تعيين الخط والحجم
        submitButton.setBackground(new Color(46, 139, 87)); // تعيين لون الخلفية
        submitButton.setForeground(Color.WHITE); // تعيين لون النص
        gbc.gridy = 2; // تغيير موقع العنصر على المحور Y
        mainPanel.add(submitButton, gbc); // إضافة الزر إلى اللوحة الرئيسية

        // Feedback Label
        feedbackLabel = new JLabel("", JLabel.CENTER); // تسمية لعرض النتائج
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // تعيين الخط والحجم
        feedbackLabel.setForeground(Color.WHITE); // تعيين لون النص
        gbc.gridy = 3; // تغيير موقع العنصر على المحور Y
        mainPanel.add(feedbackLabel, gbc); // إضافة التسمية إلى اللوحة الرئيسية

        frame.add(mainPanel, BorderLayout.CENTER); // إضافة اللوحة الرئيسية إلى الإطار

        // Add action listener to submit button
        submitButton.addActionListener(e -> checkGuess()); // تعيين مستمع الحدث عند الضغط على الزر

        frame.setLocationRelativeTo(null); // تعيين الإطار في منتصف الشاشة
        frame.setVisible(true); // عرض الإطار
    }

    private void checkGuess() { // دالة لفحص التخمين
        String[] guess = new String[CODE_LENGTH]; // مصفوفة لتخزين التخمين
        for (int i = 0; i < CODE_LENGTH; i++) { // حلقة للحصول على القيم من مربعات الاختيار
            guess[i] = (String) colorSelectors[i].getSelectedItem(); // الحصول على العنصر المختار
        }

        attempts++; // زيادة عدد المحاولات
        int correctPosition = 0; // عدد الألوان الصحيحة في المكان الصحيح
        int correctColor = 0; // عدد الألوان الصحيحة في المكان الخطأ

        boolean[] codeUsed = new boolean[CODE_LENGTH]; // مصفوفة لتتبع الألوان المستخدمة في الكود السري
        boolean[] guessUsed = new boolean[CODE_LENGTH]; // مصفوفة لتتبع الألوان المستخدمة في التخمين

        // Check correct positions
        for (int i = 0; i < CODE_LENGTH; i++) { // حلقة لفحص الأماكن الصحيحة
            if (guess[i].equals(secretCode[i])) { // إذا كان اللون في المكان الصحيح
                correctPosition++; // زيادة عدد الأماكن الصحيحة
                codeUsed[i] = true; // وضع علامة على اللون كمستخدم
                guessUsed[i] = true; // وضع علامة على اللون كمستخدم
            }
        }

        // Check correct colors
        for (int i = 0; i < CODE_LENGTH; i++) { // حلقة لفحص الألوان الصحيحة في الأماكن الخطأ
            for (int j = 0; j < CODE_LENGTH; j++) {
                if (!codeUsed[j] && !guessUsed[i] && guess[i].equals(secretCode[j])) { // إذا كان اللون صحيحًا لكنه في المكان الخطأ
                    correctColor++; // زيادة عدد الألوان الصحيحة في الأماكن الخطأ
                    codeUsed[j] = true; // وضع علامة على اللون كمستخدم في الكود
                    guessUsed[i] = true; // وضع علامة على اللون كمستخدم في التخمين
                    break; // الخروج من الحلقة الداخلية
                }
            }
        }

        // Display guess result
        if (correctPosition == CODE_LENGTH) { // إذا كان التخمين صحيحًا بالكامل
            endGame(true); // إنهاء اللعبة مع الفوز
        } else if (attempts >= MAX_ATTEMPTS) { // إذا انتهت المحاولات
            endGame(false); // إنهاء اللعبة مع الخسارة
        } else {
            feedbackLabel.setText(String.format("Correct colors in right position: %d, Correct colors in wrong position: %d",
                    correctPosition, correctColor)); // عرض النتائج
        }
    }

    private void endGame(boolean won) { // دالة لإنهاء اللعبة
        submitButton.setEnabled(false); // تعطيل زر الإرسال

        if (won) { // إذا كان اللاعب فاز
            feedbackLabel.setText("Congratulations! You guessed the secret code!"); // عرض رسالة الفوز
            feedbackLabel.setForeground(Color.GREEN); // تغيير لون النص إلى الأخضر
        } else { // إذا خسر اللاعب
            feedbackLabel.setText("Game Over! The secret code was: " + String.join(", ", secretCode)); // عرض رسالة الخسارة
            feedbackLabel.setForeground(Color.RED); // تغيير لون النص إلى الأحمر
        }
    }

    public static void main(String[] args) { // الدالة الرئيسية لتشغيل اللعبة
        SwingUtilities.invokeLater(MastermindGame::new); // تشغيل اللعبة على خيط واجهة المستخدم
    }
}
