package com.example.latinpractice;

class Exercise {
    private String question;
    private String answer;
    private String extra;
    private String[] alternatives;
    public Exercise(String question, String answer, String[] alternatives, String extra) {
        this.question = question;
        this.extra = extra;
        this.answer = answer;
        this.alternatives = alternatives;

    }

    public String getQuestion() {
        return question;
    }

    public String[] getAlternatives() {
        return alternatives;
    }
    public String getExtra() {
        return extra;
    }

    public String getAnswer() {
        return answer;
    }
}
