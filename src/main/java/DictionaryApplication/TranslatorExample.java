package DictionaryApplication;

import java.io.IOException;
import java.util.Scanner;

import com.darkprograms.speech.translator.GoogleTranslate;

public class TranslatorExample {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter the number of words: ");
            int numOfWord = sc.nextInt();
            while (numOfWord-- > 0){
                System.out.print("Enter English word: ");
<<<<<<< Updated upstream
                String text = sc.next();
                System.out.println(GoogleTranslate.translate("vi", text));
=======
                String text = sc.nextLine();
                System.out.println("Meaning: " + GoogleTranslate.translate("vi", text));
>>>>>>> Stashed changes
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
