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
public class Question {

    private long id;

    private long idPoll;
    @NotNull
    @Positive
    private long number;
    @NotBlank
    @NotEmpty
    private String question;

}
