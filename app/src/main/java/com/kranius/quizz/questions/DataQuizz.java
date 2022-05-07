package com.kranius.quizz.questions;

import java.util.Arrays;

public final class DataQuizz {

    private static final Question q1 = new Question("Quel est le premier président de la 4eme République Française ?", new String[] {"Vicent AURIOL", "René COTY", "Albert LEBRUN", "Paul DOUMER"}, 1);
    private static final Question q2 = new Question("Combien de pays il y a-t-il dans l'Union Européenne ?", new String[] {"15", "24", "32", "27"}, 4);
    private static final Question q3 = new Question("Quelle est la langue la moins parlée au monde ?", new String[] {"L'Artchi", "Le Silbo", "Rotokas", "Le Piraha"}, 2);
    private static final Question q4 = new Question("Quel est le pays le plus peuplé au monde ?", new String[] {"USA", "Chine", "Inde", "Indonésie"}, 2);
    private static final Question q5 = new Question("Quel est l'animal le plus rapide ?", new String[] {"Tortue", "Souris", "Faucon", "Lapin"}, 3);
    private static final Question q6 = new Question("Quel est la plus grande planete du Système solaire ? ?", new String[] {"La Terre", "Neptune", "Mars", "Jupiter"}, 4);

    public static QuestionBank generateQuestions() {
        return new QuestionBank(Arrays.asList(q1,q2,q3,q4,q5,q6));
}
}
