package by.nadia.dto;


import by.nadia.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerDTO {
    private List<Answer> answers=new ArrayList<>();

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }
}
