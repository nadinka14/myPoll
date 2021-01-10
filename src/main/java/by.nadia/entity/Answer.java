package by.nadia.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Answer {

    private long id;

    private long idQuestion;

    private long idPoll;

    @NotNull
    @Positive
    private long number;

    @NotBlank
    @NotEmpty
    private String question;


    private long idGuest;


    private String answer;


    public Answer(long idQuestion, long idPoll, long number, String question, long lastId,String  answer) {
        this.idQuestion = idQuestion;
        this.idPoll = idPoll;
        this.number = number;
        this.question = question;
        this.idGuest = lastId;
        this.answer=answer;
    }
}
