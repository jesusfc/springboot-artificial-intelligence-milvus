package com.jesusfc.springboot_artificial_intelligence_milvus.controllers;

import com.jesusfc.springboot_artificial_intelligence_milvus.model.Answer;
import com.jesusfc.springboot_artificial_intelligence_milvus.model.CapitalRQ;
import com.jesusfc.springboot_artificial_intelligence_milvus.model.Question;
import com.jesusfc.springboot_artificial_intelligence_milvus.services.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author Jesús Fdez. Caraballo
 * jfcaraballo@gmail.com
 * Created on sept - 2024
 */
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/askQuestion")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }

    @PostMapping("/askCapitalOf")
    public Answer askCapitalOf(@RequestBody CapitalRQ country) {
        return openAIService.getCapital(country);
    }

    @PostMapping("/askRagQuestion")
    public Answer askRagQuestion(@RequestBody Question question) {
        return openAIService.getRagAnswer(question);
    }
}
