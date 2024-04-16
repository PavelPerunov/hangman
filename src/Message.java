public class Message {
    private static final String rulesHeader = "ПРАВИЛА ИГРЫ:";
    private static final String rules = """  
            Случайным образом из уже имеющегося словаря загадывается слово. Игрок должен отгадать это слово.
            Слово будет состоять только из букв русского алфавита. Игроку позволено совершить 6 ошибок, после совершения 7
            игрок будет считаться проигранным.Если же отгадает слово - то победителем.
            Удачной и Приятной Игры!""";

    public static void getRules() {
        System.err.println(rulesHeader);
        System.out.println(rules);
    }

}
