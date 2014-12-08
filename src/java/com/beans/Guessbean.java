/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.application.FacesMessage;

@Named
@SessionScoped
public class Guessbean implements Serializable {

    //stores the random int
    private int number;
    private Integer userNumber;
    private int minimum;
    private int remainingGuesses;

    @Inject
    @MaxNumber
    private int maxNumber;

    @Inject
    @Random
    Instance<Integer> randomInt;

    private int maximum;

    @PostConstruct
    public void reset() {
        this.minimum = 0;
        this.userNumber = 0;
        this.remainingGuesses = 10;
        this.maximum = maxNumber;
        this.number = randomInt.get();

    }

    public Guessbean() {
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getRemainingGuesses() {
        return remainingGuesses;
    }

    public void setRemainingGuesses(int remainingGuesses) {
        this.remainingGuesses = remainingGuesses;
    }

    public void validateNumberRange(FacesContext context,
            UIComponent toValidate,
            Object value){
        
        if(remainingGuesses <= 0) {
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage("No guesses left");
           context.addMessage(toValidate.getClientId(context), message);
           return;
        }
        
        int input = (Integer) value;
        if(input < minimum || input > maximum){
            ((UIInput) toValidate).setValid(false);
            
            FacesMessage message = new FacesMessage("Invalid guess");
            context.addMessage(toValidate.getClientId(context), message);
        }
    }
    
        public String check() {
        if (number < userNumber) {
            maximum = userNumber - 1;
        }

        if (number > userNumber) {
            minimum = userNumber + 1;
        }

        if (number == userNumber) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Correct!"));
        }
        remainingGuesses--;
        return null;       
    }
    
}
