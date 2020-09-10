import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.*;

public class Test1 extends JPanel {
    /*static ArrayList<String> paintList = new ArrayList<>();*/
    static String[] TDizi = {};
    static int sayac = 0;


    public Dimension getPreferredSize() {
        return new Dimension(800, 800);
    }
    static JTextField V = new JTextField("V(degisken ya da non-terminal olmayan simgeler kumesi):", 20);
    static JTextField T = new JTextField("T(terminal simgeler kumesi Orn. a,b Seklinde giriniz):", 20);
    static JTextField S = new JTextField("S(baslangic sembolu):", 20);
    static JTextField variableSymbol = new JTextField("Uretim kurallarinizin sembolu. (Orn. A)", 20);
    static JTextField variable = new JTextField("Uretim kurallariniz(epsln ve lmbda'yi '?' olarak giriniz )", 20);
    static JLabel vNumber = new JLabel("gramere ait kural sayisi: ");
    static JLabel dil = new JLabel();
    static JPanel kurallarJPanel = new JPanel();

    static JButton buttonStart = new JButton("dili bul");
    static JButton buttonMore = new JButton("ekle");

    Test1() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        add(V);
        add(T);
        add(S);
        V.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                V.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        T.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                T.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        S.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                S.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        variableSymbol.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                variableSymbol.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        variable.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                variable.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        add(kurallarJPanel);
        add(variableSymbol);
        add(variable);
        add(BorderLayout.SOUTH,buttonMore);
        add(BorderLayout.SOUTH,buttonStart);
        add(vNumber);
        add(dil);
    }

    public static void main(String[] args) {
        HashMap<String, ArrayList<String>> uKurallari = new HashMap<String, ArrayList<String>>(); //alÄ±nan P(Ã¼retim kurallarÄ±)'na rahatÃ§a eriÅŸebilmek iÃ§in HashMap tanÄ±mlanÄ±r(Ã–rn. key:S, value:ASA|A|Îµ).
        SwingUtilities.invokeLater(new Runnable() {
            public JFrame frame;

            public void run() {
                frame = new JFrame("test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationByPlatform(true);
                frame.add(new Test1());
                frame.pack();
                frame.setVisible(true);
            }
        });
        buttonStart.addActionListener(e -> {
            TDizi = T.getText().split(",");
            uKurallari.put("S", new ArrayList<>(Arrays.asList(S.getText().split(" "))));
            dil.setText(stringBul(uKurallari, TDizi));
        });
        buttonMore.addActionListener(e -> {
            uKurallari.put(variableSymbol.getText(), new ArrayList<>(Arrays.asList(variable.getText().split(" "))));
            vNumber.setText("gramere ait kural sayisi " + (sayac + 1));
            sayac++;
            variableSymbol.setText("yeni sembolu giriniz");
            variable.setText("yeni veriyi giriniz(eps=lmb=?)");
        });
    }

    public static int indexBul(String sembolS) { //verilen stringden rastgele bir bÃ¼yÃ¼k harf dÃ¶ndÃ¼ren metod
        char tempChar;
        Random random = new Random();
        tempChar = sembolS.charAt(random.nextInt(sembolS.length()));

        while (String.valueOf(tempChar).toLowerCase() == String.valueOf(tempChar)) {
            tempChar = sembolS.charAt(random.nextInt(sembolS.length()));
            if (sembolS.toLowerCase() == sembolS) break;
        }
        return sembolS.indexOf(tempChar);
    }

    public static String stringBul(HashMap<String, ArrayList<String>> uKurallari, String[] T) {
        Random random = new Random();
        String sembolS = "", replace = " ", kucuk = " ";
        boolean durum = true;
        int tekrar = 0, limit = 1000, index = 0;
        for (String iter : uKurallari.get("S") //baÅŸlangÄ±Ã§ sembolÃ¼(S)'nÃ¼n iÃ§indeki en bÃ¼yÃ¼k boyutlu value'su alÄ±nÄ±p sembolS deÄŸiÅŸkenine atanÄ±r.
        ) {
            int boyut = iter.length();
            if (boyut > sembolS.length())
                sembolS = iter;
        }
        String temp = sembolS;
        while (true) {
            tekrar++;
            if (tekrar > limit) {
                return "Girilen gramere gore dil bulunamiyor";
            }
//            for (Map.Entry<String, ArrayList<String>> iter : uKurallari.entrySet()) {
//                index = indexBul(sembolS);
//                if (String.valueOf(sembolS.charAt(index)).equals(iter.getKey())) {
//                    if (tekrar <= limit) {
//                        for (String value : iter.getValue()
//                        ) {
//                            String temp0 = value.toLowerCase();
//                            if (temp0 != value) {
//                                replace = value;
//                            }
//                        }
//                    } else {
//                        replace = iter.getValue().get(random.nextInt(iter.getValue().size()));
//                    }
//                    if(replace == " ") continue;
//                    sembolS = sembolS.replace(String.valueOf(sembolS.charAt(index)), replace);
//                }
//            }
            //Ã¼st tarafta yorum satÄ±rÄ±na alÄ±nan foreach dÃ¶ngÃ¼sÃ¼ ile daha fazla string elde edip Ã§alÄ±ÅŸma mantÄ±ÄŸÄ± deÄŸiÅŸtirilebilir)
            for (Map.Entry<String, ArrayList<String>> iter : uKurallari.entrySet()) { //foreach dÃ¶ngÃ¼sÃ¼ ile Ã¼retim kurallarÄ±nÄ±n bulunduÄŸu hashmapte gezerek baÅŸlangÄ±Ã§ stringi(S) iÃ§inden random bir index ile(indexBul()), bu mapteki random bir valueyu deÄŸiÅŸtiriyoruz(while dÃ¶ngÃ¼sÃ¼nÃ¼n amacÄ± terminal olmayanlarÄ± terminale Ã§evirmek).
                index = indexBul(sembolS);
                if (String.valueOf(sembolS.charAt(index)).equals(iter.getKey())) {
                    for (String s : iter.getValue()
                    ) {
                        if (s.toLowerCase() == s) {
                            kucuk = s;
                            break;
                        }
                    }
                    if (kucuk.equals(" ")) kucuk = iter.getValue().get(random.nextInt(iter.getValue().size()));
                    sembolS = sembolS.replace(String.valueOf(sembolS.charAt(index)), kucuk);
                    //if (tekrar <= 8) paintList.add(sembolS);
                }

            }
            sembolS = sembolS.replaceAll("[ε-λ-?]", ""); //elde ettiÄŸimiz yeni stringden tÃ¼m 'Îµ' ve 'Î»' karakterlerini siliyoruz.
            int sayac = 0;
            for (int i = 0; i < sembolS.length(); i++) { //dÃ¶ngÃ¼ ile sembolS'in iÃ§indeki 'Îµ' ve 'Î»' karakterlerinin sayÄ±sÄ±nÄ± alÄ±yoruz.
                if (String.valueOf(sembolS.charAt(i)) == "ε" || String.valueOf(sembolS.charAt(i)) == "λ" || String.valueOf(sembolS.charAt(i)) == "?")
                    sayac++;
            }
            if (sayac == sembolS.length()) //bir Ã¼stteki dÃ¶ngÃ¼den aldÄ±ÄŸÄ±mÄ±z sayÄ± ile sembolS'in tÃ¼m karakterlerinin 'Îµ' ya da 'Î»' karakterlerinden oluÅŸup oluÅŸmadÄ±ÄŸÄ±nÄ± kontrol ediyoruz, oluÅŸuyor ise 69. satÄ±rda temp deÄŸiÅŸkenine yaptÄ±ÄŸÄ±mÄ±z atamayÄ± kullanarak sembolSi eski deÄŸerine getiriyoruz.
                sembolS = temp;

            for (int i = 0; i < sembolS.length(); i++) { //dÃ¶ngÃ¼ ile sembolSin tÃ¼m karakterlerinin terminal olup olmadÄ±ÄŸÄ±nÄ± kontrol ediyoruz(T[] dizisi).
                for (int j = 0; j < T.length; j++) {
                    if (String.valueOf(sembolS.charAt(i)).equals(T[j])) {
                        durum = true;
                        break;
                    }
                    durum = false;
                }
                if (!durum) break;
            }
            if (durum) break;
        }
        System.out.println("String: " + sembolS);
        return dilBul(sembolS, uKurallari, T);
    }

    public static String dilBul(String sembolS, HashMap<String, ArrayList<String>> uKurallari, String[] T) {
        String kontrol = sembolS, language = "L = {";
        Random rnd = new Random();
        int mod = 0, random = 0, min = 999, temp = 0, sayac = 0, deger = 0, boyut = 0, addValue = 0, lowerCase = 0;
        for (Map.Entry<String, ArrayList<String>> iter : uKurallari.entrySet()) {
            for (String value : iter.getValue()
            ) {
                if (!value.contains("ε") && !value.contains("λ") && !value.contains("?") && !iter.getKey().equals("S"))
                    boyut += value.length();
            }
        }
        if (kontrol.chars().allMatch(a -> a == kontrol.charAt(0))) { //eÄŸer gelen stringin bÃ¼tÃ¼n karakterleri birbirine eÅŸit ise.
            for (int i = 0; i < sembolS.length(); i++) {
                for (Map.Entry<String, ArrayList<String>> iter : uKurallari.entrySet()) {
                    for (String value : iter.getValue()
                    ) {
                        if (value.chars().allMatch(a -> a == value.charAt(0)) && value.length() <= sembolS.length() && value.charAt(0) == sembolS.charAt(0)) { //Ã¼retim kurallarÄ±mÄ±z iÃ§inde dolaÅŸarak gereken kurallara uygun deÄŸeri bulup mod olarak belirliyoruz.
                            mod = value.length();
                        }
                    }
                }
            }
            language += "w € ∑* n|(w)" + sembolS.charAt(0) + " mod " + mod + "=0}"; //terminallerin hepsi birbirine eÅŸitse bu ÅŸekilde bir language belirliyoruz.
            return language;
        } else if (boyut == sembolS.length()) {
            HashMap<Character, Integer> chrSayilari = new HashMap<Character, Integer>();
            char[] stringArray = sembolS.toCharArray();
            for (char c : stringArray) {
                if (chrSayilari.containsKey(c)) {
                    chrSayilari.put(c, chrSayilari.get(c) + 1);
                } else {
                    chrSayilari.put(c, 1);
                }
            }
            for (Map.Entry<Character, Integer> iter : chrSayilari.entrySet()) {

                language += iter.getKey() + "^" + iter.getValue() + " ";
            }
            language += "}";
            return language;
        } else { //eÄŸer terminallerden n,m,k... kadar oluÅŸturulabiliyorsa
            List<String> tempList = new ArrayList<>();
            char[] alfabe = "mnopqrstuvwxyz".toCharArray(); //Ã¼sler iÃ§in
            ArrayList<Integer> yasakli = new ArrayList<>();
            for (int i = 0; i < sembolS.length(); i++) { //geÃ§ici listeye stringin karakterlerini tek tek atÄ±yoruz
                tempList.add(String.valueOf(sembolS.charAt(i)));
            }
            Set<String> terminals = new HashSet<String>(tempList); //oluÅŸturduÄŸumuz geÃ§ici listeden HashSet Ã¼retiyoruz(amaÃ§ tekrar eden karakterleri ortadan kaldÄ±rmak)
            for (String iter : terminals //terminalleri ve Ã¼stleri ekliyoruz(Ã–rn. L = {a^m b^n | .....
            ) {
                random = rnd.nextInt(alfabe.length);
                while (yasakli.contains(random))
                    random = rnd.nextInt(alfabe.length);
                language += iter + "^" + alfabe[random] + " ";
                yasakli.add(random);
            }
            language += " | ";
            char[] stringArray = sembolS.toCharArray();
            HashMap<Character, Integer> charMap = new HashMap<>();
            for (char c : stringArray
            ) {
                if (charMap.containsKey(c)) {
                    charMap.put(c, charMap.get(c) + 1);
                } else {
                    charMap.put(c, 1);
                }
            }
            for (String terminal : terminals
            ) {

                language += alfabe[yasakli.get(sayac)] + ">=" + charMap.get(terminal.charAt(0)) + " ";
                sayac++;
            }
            language += "}";
            return language;
        }
    }
}