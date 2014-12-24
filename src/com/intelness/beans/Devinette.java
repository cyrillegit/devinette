package com.intelness.beans;

public class Devinette {

    int    id;
    String devinette;
    String answer;
    String first_hint;
    String second_hint;
    String third_hint;
    String description_answer;

    public Devinette() {

    }

    public Devinette( int _id, String _devinette, String _answer ) {
        this.id = _id;
        this.devinette = _devinette;
        this.answer = _answer;
    }

    public Devinette( String _devinette, String _answer ) {
        this.devinette = _devinette;
        this.answer = _answer;
    }

    public Devinette( int id, String devinette, String answer, String first_hint, String second_hint,
            String third_hint, String description_answer ) {
        super();
        this.id = id;
        this.devinette = devinette;
        this.answer = answer;
        this.first_hint = first_hint;
        this.second_hint = second_hint;
        this.third_hint = third_hint;
        this.description_answer = description_answer;
    }

    public int getId() {
        return id;
    }

    public void setId( int _id ) {
        this.id = _id;
    }

    public String getDevinette() {
        return devinette;
    }

    public void setDevinette( String _devinette ) {
        this.devinette = _devinette;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer( String _answer ) {
        this.answer = _answer;
    }

    public String getFirstHint() {
        return first_hint;
    }

    public void setFirstHint( String first_hint ) {
        this.first_hint = first_hint;
    }

    public String getSecondHint() {
        return second_hint;
    }

    public void setSecondHint( String second_hint ) {
        this.second_hint = second_hint;
    }

    public String getThirdHint() {
        return third_hint;
    }

    public void setThirdHint( String third_hint ) {
        this.third_hint = third_hint;
    }

    public String getDescriptionAnswer() {
        return description_answer;
    }

    public void setDescriptionAnswer( String description_answer ) {
        this.description_answer = description_answer;
    }

}
