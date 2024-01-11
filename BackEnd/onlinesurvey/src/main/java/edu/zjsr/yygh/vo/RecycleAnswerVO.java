package edu.zjsr.yygh.vo;

import edu.zjsr.yygh.entity.Answer;
import edu.zjsr.yygh.entity.SurveyRecycle;

import java.util.List;

public class RecycleAnswerVO {
    private SurveyRecycle surveyRecycle;

    private List<Answer> answers;

    public SurveyRecycle getSurveyRecycle() {
        return surveyRecycle;
    }

    public void setSurveyRecycle(SurveyRecycle surveyRecycle) {
        this.surveyRecycle = surveyRecycle;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
